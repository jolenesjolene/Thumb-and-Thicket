package net.jolene.thumbandthicket.util.foam;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class FoamRenderer {

    public static void init() {
        WorldRenderEvents.AFTER_TRANSLUCENT.register(context -> {
            MinecraftClient client = MinecraftClient.getInstance();

            if (client.world == null || client.player == null) return;

            Vec3d camPos = context.camera().getPos();
            MatrixStack matrices = context.matrixStack();

            matrices.push();
            matrices.translate(-camPos.x, -camPos.y, -camPos.z);
            VertexConsumerProvider.Immediate consumers = client.getBufferBuilders().getEntityVertexConsumers();
            VertexConsumer buffer = consumers.getBuffer(RenderLayer.getTranslucent());

            for (FoamChunk chunk : FoamManager.CHUNKS.values()) {
                for (FoamQuad quad : chunk.quads) {
                    renderFoam(matrices, buffer, quad.sprite(), quad.x(), quad.y(), quad.z()
                    );
                }
            }
            consumers.draw();
            matrices.pop();
        });
    }

    public static void renderFoam(MatrixStack matrices, VertexConsumer buffer, Sprite sprite, float x, float y, float z) {
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