package net.jolene.thumbandthicket.util;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.joml.Matrix4f;

public class WaterOverlayRenderer {
    private static final int RADIUS = 32;

    public static void renderFoam() {
        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> {
            MinecraftClient client = MinecraftClient.getInstance();
            World world = client.world;

            if (world != null && client.player != null) {
                Vec3d camPos = context.camera().getPos();
                MatrixStack matrices = context.matrixStack();
                matrices.push();
                matrices.translate(-camPos.x, -camPos.y, -camPos.z);

                VertexConsumerProvider.Immediate consumers = client.getBufferBuilders().getEntityVertexConsumers();
                VertexConsumer buffer = consumers.getBuffer(RenderLayer.getTranslucent());
                BlockPos center = client.player.getBlockPos();
                BlockPos.Mutable mutable = new BlockPos.Mutable();

                for (int x = -RADIUS; x <= RADIUS; x++) {
                    for (int y = -16; y <= 16; y++) {
                        for (int z = -RADIUS; z <= RADIUS; z++) {
                            mutable.set(center.getX() + x, center.getY() + y, center.getZ() + z);
                            BlockState state = world.getBlockState(mutable);

                            if (state.getFluidState().getFluid() != Fluids.WATER) continue;
                            if (!state.getFluidState().isStill()) continue;
                            if (!world.getBlockState(mutable.up()).isAir()) continue;

                            FoamShapeUtil set = FoamShapeUtil.getSpriteSet(world, mutable);
                            renderFoam(matrices, buffer, set.sprites[0], mutable.getX(), mutable.getY() + 0.901f, mutable.getZ());
                        }
                    }
                }
                consumers.draw();
                matrices.pop();
            }
        });
    }

    private static void renderFoam(MatrixStack matrices, VertexConsumer buffer, Sprite sprite, float x, float y, float z) {
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        float minU = sprite.getMinU();
        float maxU = sprite.getMaxU();
        float minV = sprite.getMinV();
        float maxV = sprite.getMaxV();

        buffer.vertex(matrix, x, y, z)
                .color(255, 255, 255, 255)
                .texture(minU, minV)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(0, 1, 0);

        buffer.vertex(matrix, x, y, z + 1)
                .color(255, 255, 255, 255)
                .texture(minU, maxV)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(0, 1, 0);

        buffer.vertex(matrix, x + 1, y, z + 1)
                .color(255, 255, 255, 255)
                .texture(maxU, maxV)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(0, 1, 0);

        buffer.vertex(matrix, x + 1, y, z)
                .color(255, 255, 255, 255)
                .texture(maxU, minV)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
                .normal(0, 1, 0);
    }
}