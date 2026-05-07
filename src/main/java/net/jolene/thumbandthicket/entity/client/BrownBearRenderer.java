package net.jolene.thumbandthicket.entity.client;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.entity.custom.BrownBearEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BrownBearRenderer extends MobEntityRenderer<BrownBearEntity, BrownBearModel<BrownBearEntity>> {
    public BrownBearRenderer(EntityRendererFactory.Context context) {
        super(context, new BrownBearModel<>(context.getPart(BrownBearModel.BROWN_BEAR)), 0.75f);
    }

    @Override
    public Identifier getTexture(BrownBearEntity entity) {
        if (entity.isAngry()) {
            return Identifier.of(ThumbAndThicket.MOD_ID, "textures/entity/brown_bear_angry.png");
        }

        return Identifier.of(ThumbAndThicket.MOD_ID, "textures/entity/brown_bear.png");
    }

    @Override
    public void render(BrownBearEntity livingEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {
        if(livingEntity.isBaby()) {
            matrixStack.scale(0.5f, 0.5f, 0.5f);
        } else {
            matrixStack.scale(1f, 1f, 1f);
        }

        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
