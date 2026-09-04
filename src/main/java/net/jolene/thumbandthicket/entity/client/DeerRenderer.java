package net.jolene.thumbandthicket.entity.client;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.entity.custom.DeerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DeerRenderer extends MobEntityRenderer<DeerEntity, DeerModel<DeerEntity>> {
    public DeerRenderer(EntityRendererFactory.Context context) {
        super(context, new DeerModel<>(context.getPart(DeerModel.DEER)), 0.25f);
    }

    @Override
    public Identifier getTexture(DeerEntity entity) {
        return Identifier.of(ThumbAndThicket.MOD_ID, "textures/entity/deer/fallow.png");
    }

    @Override
    public void render(DeerEntity entity, float entityYaw, float tickDelta,
                       MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {

        matrices.push();

        if (entity.isBaby()) {
            matrices.scale(0.5F, 0.5F, 0.5F);
        }

        super.render(entity, entityYaw, tickDelta, matrices, vertexConsumers, light);

        matrices.pop();
    }
}