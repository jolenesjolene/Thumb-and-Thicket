package net.jolene.thumbandthicket.mixin;

import net.jolene.thumbandthicket.util.FoamShapeUtil;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FluidRenderer.class)
public class FluidRendererMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void thumbandthicket$renderFoam(BlockRenderView world, BlockPos pos, VertexConsumer vertexConsumer, net.minecraft.block.BlockState blockState, FluidState fluidState, CallbackInfo ci) {
        if (fluidState.isOf(Fluids.WATER) && fluidState.isStill()) {
            FoamShapeUtil spriteSet = FoamShapeUtil.getSpriteSet(world, pos);
            if (spriteSet != null && spriteSet.sprites.length > 0) {
                renderFoam(vertexConsumer, spriteSet.sprites[0], pos.getX(), pos.getY() + 1f, pos.getZ());
            }
        }
    }

    @Unique
    private static void renderFoam(VertexConsumer buffer, Sprite sprite, float x, float y, float z) {
        Matrix4f matrix = new Matrix4f();

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
