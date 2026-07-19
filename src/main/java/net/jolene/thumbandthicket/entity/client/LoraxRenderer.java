package net.jolene.thumbandthicket.entity.client;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.entity.custom.BrownBearEntity;
import net.jolene.thumbandthicket.entity.custom.LoraxEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class LoraxRenderer extends MobEntityRenderer<LoraxEntity, LoraxModel<LoraxEntity>> {
    public LoraxRenderer(EntityRendererFactory.Context context) {
        super(context, new LoraxModel<>(context.getPart(LoraxModel.LORAX)), 0.25f);
    }

    @Override
    public Identifier getTexture(LoraxEntity entity) {
        return Identifier.of(ThumbAndThicket.MOD_ID, "textures/entity/lorax.png");
    }

    @Override
    public void render(LoraxEntity entity, float entityYaw, float tickDelta,
                       MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {

        matrices.push();

        if (entity.isBaby()) {
            matrices.scale(0.5F, 0.5F, 0.5F);
        }

        super.render(entity, entityYaw, tickDelta, matrices, vertexConsumers, light);

        matrices.pop();
    }
}