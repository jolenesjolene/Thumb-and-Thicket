package net.jolene.thumbandthicket.entity.client.Remodels;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class SilverFishRemodel extends EntityModel<Entity> {
    private final ModelPart antennae;
    private final ModelPart main;
    public SilverFishRemodel(ModelPart root) {
        this.antennae = root.getChild("antennae");
        this.main = root.getChild("bb_main");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData antennae = modelPartData.addChild("antennae", ModelPartBuilder.create(), ModelTransform.pivot(-1.5F, 20.5F, -5.0F));

        ModelPartData antennae_left_r1 = antennae.addChild("antennae_left_r1", ModelPartBuilder.create().uv(0, 24).cuboid(0.0F, -4.0F, -3.0F, 0.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

        ModelPartData antennae_right_r1 = antennae.addChild("antennae_right_r1", ModelPartBuilder.create().uv(18, 22).cuboid(-1.0F, -4.0F, -3.0F, 0.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 7).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 4.0F, 6.0F, new Dilation(0.0F))
                .uv(18, 17).cuboid(-2.5F, -3.0F, -5.0F, 5.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 17).cuboid(-2.5F, -3.0F, 3.0F, 5.0F, 3.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-3.5F, -1.5F, 7.0F, 7.0F, 0.0F, 7.0F, new Dilation(0.0F))
                .uv(24, 7).cuboid(3.0F, -0.25F, -2.5F, 2.0F, 0.0F, 1.0F, new Dilation(0.0F))
                .uv(8, 24).cuboid(3.0F, -0.25F, -0.5F, 2.0F, 0.0F, 1.0F, new Dilation(0.0F))
                .uv(24, 8).cuboid(3.0F, -0.25F, 1.5F, 2.0F, 0.0F, 1.0F, new Dilation(0.0F))
                .uv(24, 9).cuboid(-5.0F, -0.25F, 1.5F, 2.0F, 0.0F, 1.0F, new Dilation(0.0F))
                .uv(24, 10).cuboid(-5.0F, -0.25F, -0.5F, 2.0F, 0.0F, 1.0F, new Dilation(0.0F))
                .uv(24, 11).cuboid(-5.0F, -0.25F, -2.5F, 2.0F, 0.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }
    @Override
    public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        main.render(matrices, vertices, light, overlay, color);
        antennae.render(matrices, vertices, light, overlay, color);
    }
}