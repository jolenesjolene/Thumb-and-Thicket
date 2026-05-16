package net.jolene.thumbandthicket.client;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.jolene.thumbandthicket.util.FoamRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FoamRenderHandler {

    public static void render(WorldRenderContext context) {

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        World world = client.world;

        Camera camera = context.camera();
        BlockPos cameraPos = camera.getBlockPos();
        MatrixStack matrices = context.matrixStack();
        VertexConsumerProvider consumers = context.consumers();

        // RenderLayer (simple, stable, translucent-friendly)
        RenderLayer FOAM_LAYER = RenderLayer.getTranslucent();

        VertexConsumer vc = consumers.getBuffer(FOAM_LAYER);

        int radius = 8;

        for (BlockPos pos : BlockPos.iterateOutwards(cameraPos, radius, 4, radius)) {

            FluidState fluid = world.getFluidState(pos);

            if (!fluid.isIn(FluidTags.WATER)) continue;

            // only surface water
            if (!world.getFluidState(pos.up()).isEmpty()) continue;

            FoamRenderer set = FoamRenderer.getSpriteSet(world, pos);
            if (set == null) continue;

            Sprite sprite = set.sprites[0];
            if (sprite == null) continue;

            float x = (float) (pos.getX() - camera.getPos().x);
            float y = (float) (pos.getY() - camera.getPos().y + 0.02f);
            float z = (float) (pos.getZ() - camera.getPos().z);

            int light = WorldRenderer.getLightmapCoordinates(world, pos);

            float minU = sprite.getMinU();
            float maxU = sprite.getMaxU();
            float minV = sprite.getMinV();
            float maxV = sprite.getMaxV();

            // quad (top face)
            vc.vertex(x, y, z)
                    .texture(minU, minV)
                    .color(255, 255, 255, 255)
                    .light(light)
                    .normal(0, 1, 0);

            vc.vertex(x, y, z + 1)
                    .texture(minU, maxV)
                    .color(255, 255, 255, 255)
                    .light(light)
                    .normal(0, 1, 0);

            vc.vertex(x + 1, y, z + 1)
                    .texture(maxU, maxV)
                    .color(255, 255, 255, 255)
                    .light(light)
                    .normal(0, 1, 0);

            vc.vertex(x + 1, y, z)
                    .texture(maxU, minV)
                    .color(255, 255, 255, 255)
                    .light(light)
                    .normal(0, 1, 0);
        }
    }
}