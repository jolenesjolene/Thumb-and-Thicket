package net.jolene.thumbandthicket.entity.client;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.entity.custom.MooseEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class MooseModel<T extends MooseEntity> extends SinglePartEntityModel<T> {
    public static final EntityModelLayer MOOSE =
            new EntityModelLayer(Identifier.of(ThumbAndThicket.MOD_ID, "moose"), "main");

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart antlers;
    private final ModelPart tail;
    private final ModelPart front_left_leg;
    private final ModelPart front_right_leg;
    private final ModelPart back_right_leg;
    private final ModelPart back_left_leg;
    public MooseModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.head = this.body.getChild("head");
        this.antlers = this.head.getChild("antlers");
        this.tail = this.body.getChild("tail");
        this.front_left_leg = this.root.getChild("front_left_leg");
        this.front_right_leg = this.root.getChild("front_right_leg");
        this.back_right_leg = this.root.getChild("back_right_leg");
        this.back_left_leg = this.root.getChild("back_left_leg");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-6.5F, -16.0F, -14.0F, 13.0F, 16.0F, 28.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, -20.0F, 0.0F));

        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0, 44).cuboid(-3.5F, -8.0F, -24.0F, 7.0F, 8.0F, 18.0F, new Dilation(0.0F))
                .uv(82, 18).cuboid(0.0F, 0.0F, -19.0F, 0.0F, 8.0F, 7.0F, new Dilation(0.0F))
                .uv(50, 44).cuboid(-3.5F, -4.0F, -12.0F, 7.0F, 12.0F, 14.0F, new Dilation(-0.01F))
                .uv(92, 48).cuboid(3.5F, -11.0F, -10.0F, 0.0F, 3.0F, 4.0F, new Dilation(0.0F))
                .uv(92, 41).cuboid(-3.5F, -11.0F, -10.0F, 0.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -16.0F, -6.0F));

        ModelPartData antlers = head.addChild("antlers", ModelPartBuilder.create().uv(0, 70).cuboid(-13.5F, 0.0F, -3.0F, 10.0F, 0.0F, 10.0F, new Dilation(0.01F))
                .uv(40, 70).cuboid(3.5F, 0.0F, -3.0F, 10.0F, 0.0F, 10.0F, new Dilation(0.01F))
                .uv(80, 70).cuboid(-13.5F, -8.0F, -3.0F, 10.0F, 8.0F, 0.0F, new Dilation(0.01F))
                .uv(82, 33).cuboid(3.5F, -8.0F, -3.0F, 10.0F, 8.0F, 0.0F, new Dilation(0.01F))
                .uv(84, 86).cuboid(-13.5F, -5.0F, 7.0F, 6.0F, 5.0F, 0.0F, new Dilation(0.01F))
                .uv(84, 91).cuboid(7.5F, -5.0F, 7.0F, 6.0F, 5.0F, 0.0F, new Dilation(0.01F))
                .uv(64, 80).cuboid(-13.5F, -8.0F, -3.0F, 0.0F, 8.0F, 10.0F, new Dilation(0.01F))
                .uv(82, 0).cuboid(13.5F, -8.0F, -3.0F, 0.0F, 8.0F, 10.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, -6.0F, -9.0F));

        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(84, 78).cuboid(-2.0F, 0.0F, 0.0F, 5.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, -14.0F, 14.1F, 0.2182F, 0.0F, 0.0F));

        ModelPartData front_left_leg = root.addChild("front_left_leg", ModelPartBuilder.create().uv(16, 80).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 20.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(4.5F, -20.0F, -11.0F));

        ModelPartData front_right_leg = root.addChild("front_right_leg", ModelPartBuilder.create().uv(32, 80).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 20.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.5F, -20.0F, -11.0F));

        ModelPartData back_right_leg = root.addChild("back_right_leg", ModelPartBuilder.create().uv(48, 80).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 20.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.5F, -20.0F, 11.0F));

        ModelPartData back_left_leg = root.addChild("back_left_leg", ModelPartBuilder.create().uv(0, 80).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 20.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(4.5F, -20.0F, 11.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }
    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {

        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(netHeadYaw, headPitch);

        this.animateMovement(MooseAnimations.walk, limbSwing, limbSwingAmount, 3.5f, 3.5f);
        this.updateAnimation(entity.idleAnimationState, MooseAnimations.idle, ageInTicks, 1f);
        this.updateAnimation(
                entity.attackAnimationState,
                MooseAnimations.attack,
                ageInTicks,
                1.0F
        );
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -22.5F, 22.5F);
        headPitch = MathHelper.clamp(headPitch, -22.5F, 67.5F);

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