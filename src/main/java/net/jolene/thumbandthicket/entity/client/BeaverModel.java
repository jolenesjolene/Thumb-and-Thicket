package net.jolene.thumbandthicket.entity.client;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.entity.custom.BeaverEntity;
import net.jolene.thumbandthicket.entity.custom.BrownBearEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class BeaverModel<T extends BeaverEntity> extends SinglePartEntityModel<T> {

    public static final EntityModelLayer BEAVER =
            new EntityModelLayer(Identifier.of(ThumbAndThicket.MOD_ID, "beaver"), "main");

    private final ModelPart root;
    private final ModelPart beaver;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart tail;
    private final ModelPart leftfrontleg;
    private final ModelPart rightfrontleg;
    private final ModelPart rightbackleg;
    private final ModelPart leftbackleg;
    private final ModelPart base;

    public BeaverModel(ModelPart root) {
        this.root = root.getChild("root");
        this.beaver = this.root.getChild("beaver");
        this.body = this.beaver.getChild("body");
        this.head = this.body.getChild("head");
        this.tail = this.body.getChild("tail");
        this.leftfrontleg = this.body.getChild("leftfrontleg");
        this.rightfrontleg = this.body.getChild("rightfrontleg");
        this.rightbackleg = this.body.getChild("rightbackleg");
        this.leftbackleg = this.body.getChild("leftbackleg");
        this.base = this.body.getChild("base");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData root = modelPartData.addChild(
                "root",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-13.0F, -8.0F, -3.0F, 2.0F, 2.0F, 2.0F,
                                new Dilation(0.0F)),
                ModelTransform.pivot(12.0F, 24.0F, 0.0F)
        );

        ModelPartData beaver = root.addChild(
                "beaver",
                ModelPartBuilder.create(),
                ModelTransform.pivot(-12.0F, -5.5F, 0.0F)
        );

        ModelPartData body = beaver.addChild(
                "body",
                ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 1.0F, 0.0F)
        );

        ModelPartData head = body.addChild(
                "head",
                ModelPartBuilder.create()
                        .uv(0, 20)
                        .cuboid(-4.0F, -2.5F, -6.0F, 8.0F, 5.0F, 6.0F,
                                new Dilation(0.0F))
                        .uv(28, 20)
                        .cuboid(-4.0F, -2.5F, -6.0F, 8.0F, 5.0F, 6.0F,
                                new Dilation(0.3F))
                        .uv(38, 10)
                        .cuboid(-1.0F, 2.5F, -6.0F, 2.0F, 1.0F, 0.0F,
                                new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, -2.0F, -5.0F)
        );

        ModelPartData tail = body.addChild(
                "tail",
                ModelPartBuilder.create()
                        .uv(0, 31)
                        .cuboid(-4.0F, -1.0F, 0.0F, 8.0F, 2.0F, 6.0F,
                                new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 1.5F, 5.0F)
        );

        ModelPartData leftfrontleg = body.addChild(
                "leftfrontleg",
                ModelPartBuilder.create()
                        .uv(38, 5)
                        .cuboid(-1.5F, -0.5F, -2.0F, 3.0F, 3.0F, 2.0F,
                                new Dilation(0.0F)),
                ModelTransform.pivot(3.0F, 2.0F, -5.0F)
        );

        ModelPartData rightfrontleg = body.addChild(
                "rightfrontleg",
                ModelPartBuilder.create()
                        .uv(38, 0)
                        .cuboid(-1.5F, -0.5F, -2.0F, 3.0F, 3.0F, 2.0F,
                                new Dilation(0.0F)),
                ModelTransform.pivot(-3.0F, 2.0F, -5.0F)
        );

        ModelPartData rightbackleg = body.addChild(
                "rightbackleg",
                ModelPartBuilder.create(),
                ModelTransform.pivot(-4.5F, 4.5F, 1.0F)
        );

        rightbackleg.addChild(
                "cube_r1",
                ModelPartBuilder.create()
                        .uv(28, 31)
                        .cuboid(-2.0F, 0.0F, -4.0F, 4.0F, 0.0F, 6.0F,
                                new Dilation(0.0F)),
                ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.2182F, 0.0F)
        );

        ModelPartData leftbackleg = body.addChild(
                "leftbackleg",
                ModelPartBuilder.create(),
                ModelTransform.pivot(4.5F, 4.5F, 1.0F)
        );

        leftbackleg.addChild(
                "cube_r2",
                ModelPartBuilder.create()
                        .uv(28, 37)
                        .cuboid(-2.0F, 0.0F, -4.0F, 4.0F, 0.0F, 6.0F,
                                new Dilation(0.0F)),
                ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, -0.2182F, 0.0F)
        );

        ModelPartData base = body.addChild(
                "base",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        .cuboid(-4.5F, -5.5F, -5.0F, 9.0F, 10.0F, 10.0F,
                                new Dilation(-0.01F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F)
        );

        return TexturedModelData.of(modelData, 64, 64);
    }


    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {

        this.getPart().traverse().forEach(ModelPart::resetTransform);

        this.setHeadAngles(netHeadYaw, headPitch);
        if (entity.isTouchingWater()) {
            this.body.pitch = entity.getPitch() * 0.017453292F;
        } else {
            this.body.pitch = 0;
        }


        if (entity.isTouchingWater()) {

            this.updateAnimation(
                    entity.swimAnimationState,
                    BeaverAnimations.swim,
                    ageInTicks,
                    1.0F
            );

        } else {

            this.animateMovement(
                    BeaverAnimations.walk,
                    limbSwing,
                    limbSwingAmount,
                    2.75F,
                    2.5F
            );

            this.updateAnimation(
                    entity.idleAnimationState,
                    BeaverAnimations.idle,
                    ageInTicks,
                    1.0F
            );
        }
    }


    private void setHeadAngles(float headYaw, float headPitch) {

        headYaw = MathHelper.clamp(headYaw, -35.0F, 35.0F);
        headPitch = MathHelper.clamp(headPitch, -45.0F, 25.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }



    @Override
    public ModelPart getPart() {
        return root;
    }
}