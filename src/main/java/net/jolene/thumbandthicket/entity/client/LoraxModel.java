package net.jolene.thumbandthicket.entity.client;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.entity.custom.BrownBearEntity;
import net.jolene.thumbandthicket.entity.custom.LoraxEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class LoraxModel<T extends LoraxEntity> extends SinglePartEntityModel<T> {
    public static final EntityModelLayer LORAX =
            new EntityModelLayer(Identifier.of(ThumbAndThicket.MOD_ID, "lorax"), "main");

    private final ModelPart root;
    private final ModelPart lorax;
    private final ModelPart body;
    private final ModelPart lefteyebrow;
    private final ModelPart righteyebrow;
    private final ModelPart mushtachio;
    private final ModelPart leftarm;
    private final ModelPart rightarm;
    private final ModelPart rightleg;
    private final ModelPart leftleg;
    public LoraxModel(ModelPart root) {
        this.root = root.getChild("root");
        this.lorax = this.root.getChild("lorax");
        this.body = this.lorax.getChild("body");
        this.lefteyebrow = this.body.getChild("lefteyebrow");
        this.righteyebrow = this.body.getChild("righteyebrow");
        this.mushtachio = this.body.getChild("mushtachio");
        this.leftarm = this.body.getChild("leftarm");
        this.rightarm = this.body.getChild("rightarm");
        this.rightleg = this.lorax.getChild("rightleg");
        this.leftleg = this.lorax.getChild("leftleg");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData lorax = root.addChild("lorax", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -11.0F, 0.0F));

        ModelPartData body = lorax.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -14.0F, -3.0F, 10.0F, 14.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 7.0F, 0.0F));

        ModelPartData lefteyebrow = body.addChild("lefteyebrow", ModelPartBuilder.create().uv(32, 0).cuboid(0.0F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -13.0F, -3.2F));

        ModelPartData righteyebrow = body.addChild("righteyebrow", ModelPartBuilder.create().uv(32, 2).cuboid(-5.0F, -2.0F, 0.0F, 5.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, -13.0F, -3.2F));

        ModelPartData mushtachio = body.addChild("mushtachio", ModelPartBuilder.create().uv(0, 20).cuboid(-8.0F, -3.0F, 0.0F, 16.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, -3.2F));

        ModelPartData leftarm = body.addChild("leftarm", ModelPartBuilder.create().uv(0, 32).cuboid(0.0F, 0.0F, -1.0F, 0.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(5.2F, -6.0F, 0.0F));

        ModelPartData rightarm = body.addChild("rightarm", ModelPartBuilder.create().uv(28, 26).cuboid(0.0F, 0.0F, -1.0F, 0.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.2F, -6.0F, 0.0F));

        ModelPartData rightleg = lorax.addChild("rightleg", ModelPartBuilder.create().uv(0, 26).cuboid(-1.5F, 2.0F, -2.5F, 3.0F, 2.0F, 4.0F, new Dilation(0.0F))
                .uv(32, 4).cuboid(-0.5F, 0.0F, 0.5F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, 7.0F, 0.5F));

        ModelPartData leftleg = lorax.addChild("leftleg", ModelPartBuilder.create().uv(14, 26).cuboid(-1.5F, 2.0F, -2.5F, 3.0F, 2.0F, 4.0F, new Dilation(0.0F))
                .uv(4, 32).cuboid(-0.5F, 0.0F, 0.5F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, 7.0F, 0.5F));
        return TexturedModelData.of(modelData, 64, 64);
    }
    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {

        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(netHeadYaw, headPitch);

        this.animateMovement(LoraxAnimations.walk, limbSwing, limbSwingAmount, 2.75f, 2.5f);
        this.updateAnimation(entity.idleAnimationState, LoraxAnimations.idle, ageInTicks, 1f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -35.0F, 35.0F);
        headPitch = MathHelper.clamp(headPitch, -45.0F, 25.0F);

        this.body.yaw = headYaw * 0.017453292F;
        this.body.pitch = headPitch * 0.017453292F;
    }

    public void render(MatrixStack matrices, VertexConsumer vertexConsumer,
                       int light, int overlay, float red, float green,
                       float blue, float alpha) {
        root.render(matrices, vertexConsumer, light, overlay);
    }


    @Override
    public ModelPart getPart() {
        return root;
    }
}