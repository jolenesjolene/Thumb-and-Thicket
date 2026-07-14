package net.jolene.thumbandthicket.entity.client;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.entity.custom.BeaverEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class BeaverRenderer extends MobEntityRenderer<BeaverEntity, BeaverModel<BeaverEntity>> {

    public BeaverRenderer(EntityRendererFactory.Context context) {
        super(
                context,
                new BeaverModel<>(
                        context.getPart(BeaverModel.BEAVER)
                ),
                0.25F
        );
    }


    @Override
    public Identifier getTexture(BeaverEntity entity) {

        return Identifier.of(
                ThumbAndThicket.MOD_ID,
                "textures/entity/beaver/beaver.png"
        );
    }


    @Override
    public void render(
            BeaverEntity livingEntity,
            float f,
            float g,
            MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider,
            int i
    ) {

        if (livingEntity.isBaby()) {
            matrixStack.scale(0.5F, 0.5F, 0.5F);
        }

        super.render(
                livingEntity,
                f,
                g,
                matrixStack,
                vertexConsumerProvider,
                i
        );
    }
}