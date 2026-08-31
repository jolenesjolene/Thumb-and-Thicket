package net.jolene.thumbandthicket.entity.client;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.entity.custom.MooseEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class MooseRenderer extends MobEntityRenderer<MooseEntity, MooseModel<MooseEntity>> {

    public MooseRenderer(EntityRendererFactory.Context context) {
        super(
                context,
                new MooseModel<>(
                        context.getPart(MooseModel.MOOSE)
                ),
                0.25F
        );
    }


    @Override
    public Identifier getTexture(MooseEntity entity) {

        return Identifier.of(
                ThumbAndThicket.MOD_ID,
                "textures/entity/moose/moose.png"
        );
    }


    @Override
    public void render(
            MooseEntity livingEntity,
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