package net.jolene.thumbandthicket.entity.client;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.entity.custom.BrownBearEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class BrownBearModel<T extends BrownBearEntity> extends SinglePartEntityModel<T> {
    public static final EntityModelLayer BROWN_BEAR =
            new EntityModelLayer(Identifier.of(ThumbAndThicket.MOD_ID, "brown_bear"), "main");

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart nose;
    private final ModelPart right_ear;
    private final ModelPart left_ear;
    private final ModelPart back_left_leg;
    private final ModelPart back_right_leg;
    private final ModelPart front_right_leg;
    private final ModelPart front_left_leg;

    public BrownBearModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.head = this.body.getChild("head");
        this.nose = this.head.getChild("nose");
        this.right_ear = this.head.getChild("right_ear");
        this.left_ear = this.head.getChild("left_ear");
        this.back_left_leg = this.root.getChild("back_left_leg");
        this.back_right_leg = this.root.getChild("back_right_leg");
        this.front_right_leg = this.root.getChild("front_right_leg");
        this.front_left_leg = this.root.getChild("front_left_leg");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData root = modelPartData.addChild("root",
                ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 25.0F, 0.0F));

        ModelPartData body = root.addChild("body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-8.0F, -9.5F, -14.0F, 16.0F, 16.0F, 14.0F, new Dilation(-0.001F))
                        .uv(0, 30).cuboid(-7.5F, -7.5F, 0.0F, 15.0F, 12.0F, 12.0F),
                ModelTransform.pivot(0.0F, -17.5F, 6.0F));

        ModelPartData head = body.addChild("head",
                ModelPartBuilder.create().uv(0, 54).cuboid(-6.5F, -5.5F, -8.0F, 13.0F, 11.0F, 8.0F),
                ModelTransform.pivot(0.0F, 0.0F, -14.0F));

        ModelPartData nose = head.addChild("nose",
                ModelPartBuilder.create().uv(60, 20).cuboid(-3.5F, -6.0F, -4.0F, 7.0F, 6.0F, 4.0F),
                ModelTransform.pivot(0.0F, 5.5F, -8.0F));

        ModelPartData right_ear = head.addChild("right_ear",
                ModelPartBuilder.create().uv(0, 73).cuboid(-3.5F, -3.0F, -0.5F, 5.0F, 4.0F, 1.0F),
                ModelTransform.pivot(-5.0F, -5.5F, -3.5F));

        ModelPartData left_ear = head.addChild("left_ear",
                ModelPartBuilder.create().uv(12, 73).cuboid(-1.5F, -3.0F, -0.5F, 5.0F, 4.0F, 1.0F),
                ModelTransform.pivot(5.0F, -5.5F, -3.5F));

        root.addChild("back_left_leg",
                ModelPartBuilder.create().uv(42, 56).cuboid(-3.0F, -3.0F, -4.0F, 6.0F, 18.0F, 8.0F),
                ModelTransform.pivot(7.0F, -16.0F, 12.0F));

        root.addChild("back_right_leg",
                ModelPartBuilder.create().uv(54, 30).cuboid(-3.0F, -3.0F, -4.0F, 6.0F, 18.0F, 8.0F),
                ModelTransform.pivot(-7.0F, -16.0F, 12.0F));

        root.addChild("front_right_leg",
                ModelPartBuilder.create().uv(60, 0).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 14.0F, 6.0F),
                ModelTransform.pivot(-6.0F, -11.0F, -5.0F));

        root.addChild("front_left_leg",
                ModelPartBuilder.create().uv(70, 56).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 14.0F, 6.0F),
                ModelTransform.pivot(6.0F, -11.0F, -5.0F));

        return TexturedModelData.of(modelData, 128, 128);
    }
    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {

        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(netHeadYaw, headPitch);

        this.animateMovement(BrownBearAnimations.walk, limbSwing, limbSwingAmount, 2.75f, 2.5f);
        this.updateAnimation(entity.idleAnimationState, BrownBearAnimations.idle, ageInTicks, 1f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -35.0F, 35.0F);
        headPitch = MathHelper.clamp(headPitch, -45.0F, 25.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

    public void render(MatrixStack matrices, VertexConsumer vertexConsumer,
                       int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertexConsumer, light, overlay);
    }

    @Override
    public ModelPart getPart() {
        return root;
    }
}