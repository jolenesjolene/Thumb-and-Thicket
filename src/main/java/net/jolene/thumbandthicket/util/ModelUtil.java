package net.jolene.thumbandthicket.util;

import net.minecraft.client.model.*;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.Identifier;

public class ModelUtil {

    public static ModelData createCowModel() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("head", ModelPartBuilder.create().uv(64, 0).cuboid(-4.0F, -5.0F, -7.0F, 8.0F, 10.0F, 6.0F, new Dilation(0.0F))
                        .uv(64, 16).cuboid(-4.0F, -5.0F, -7.0F, 8.0F, 10.0F, 6.0F, new Dilation(0.5F))
                        .uv(24, 66).cuboid("right_ear",4.0F, -4.0F, -3.9F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F))
                        .uv(30, 66).cuboid("left_ear",-7.0F, -4.0F, -3.9F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 3.0F, -6.0F));
        modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -6.0F, -10.0F, 12.0F, 13.0F, 20.0F, new Dilation(0.0F)).uv(0, 33).cuboid(-6.0F, -6.0F, -10.0F, 12.0F, 13.0F, 20.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 5.0F, 3.0F));
        modelPartData.getChild("body").addChild("tail", ModelPartBuilder.create().uv(16, 66).cuboid(-2.0F, 0.0F, 0.1F, 4.0F, 10.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.0F, 10F, 0.0F, 0.0F, 0.0F));
        modelPartData.addChild("right_hind_leg",ModelPartBuilder.create().uv(0, 66).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)),ModelTransform.pivot(-4,12,11));
        modelPartData.addChild("left_hind_leg",ModelPartBuilder.create().uv(64, 64).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)),ModelTransform.pivot(4,12,11));
        modelPartData.addChild("right_front_leg",ModelPartBuilder.create().uv(64, 48).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)),ModelTransform.pivot(-4,12,-5));
        modelPartData.addChild("left_front_leg", ModelPartBuilder.create().uv(64, 32).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(4,12, -5));
        return modelData;
    }

    public static ModelData createPigModel() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 50).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 6.0F, new Dilation(0.0F))
                        .uv(28, 50).cuboid("head_outer",-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 6.0F, new Dilation(0.5F))
                        .uv(52, 38).cuboid("snout",-2.0F, 0.0F, -5.0F, 4.0F, 3.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 15.0F, -10.0F));

        modelPartData.getChild("head").addChild("left_ear", ModelPartBuilder.create().uv(52, 42).cuboid("left_ear",0.0F, 0.0F, 0.0F, 0.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, -2.0F, -2.5F));
        modelPartData.getChild("head").addChild("right_ear", ModelPartBuilder.create().uv(56, 49).cuboid("right_ear",0.0F, 0.0F, 0.0F, 0.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, -2.0F, -2.5F));

        modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, 0.0F, -8.0F, 10.0F, 9.0F, 16.0F, new Dilation(0.0F)).uv(0, 25).cuboid("body_outer",-5.0F, -1.0F, -8.0F, 10.0F, 9.0F, 16.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 10.0F, 0.0F));
        modelPartData.addChild("left_hind_leg",ModelPartBuilder.create().uv(52, 10).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)),ModelTransform.pivot(3F,18F,7F));
        modelPartData.addChild("right_front_leg",ModelPartBuilder.create().uv(52, 29).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F)),ModelTransform.pivot(-3F,19F,-6F));
        modelPartData.addChild("left_front_leg",ModelPartBuilder.create().uv(52, 20).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F)),ModelTransform.pivot(3F,19F,-6F));
        modelPartData.addChild("right_hind_leg", ModelPartBuilder.create().uv(52, 0).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-3F,18F, 7F));
        return modelData;
    }

    public static ModelData createChickenModel() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(
                "body",
                ModelPartBuilder.create()
                        .uv(0, 14)
                        .cuboid(
                                -3.5F, -5.0F, -3.0F,
                                7.0F, 6.0F, 8.0F,
                                new Dilation(0.0F)
                        )
                        .uv(0, 0)
                        .cuboid(
                                -4.0F, -5.0F, -3.0F,
                                8.0F, 6.0F, 8.0F,
                                new Dilation(0.5F)
                        ),
                ModelTransform.pivot(0.0F, 19.0F, -1.0F)
        );
        modelPartData.addChild(
                "tail",
                ModelPartBuilder.create()
                        .uv(14, 28)
                        .cuboid(
                                0.0F, -3.0F, -4.0F,
                                0.0F, 8.0F, 8.0F,
                                new Dilation(0.0F)
                        ),
                ModelTransform.pivot(0.0F, 15.0F, 4.0F)
        );

        modelPartData.addChild(
                "head",
                ModelPartBuilder.create()
                        .uv(30, 14)
                        .cuboid(
                                -2.0F, -4.0F, -3.0F,
                                4.0F, 5.0F, 3.0F,
                                new Dilation(0.0F)
                        )
                        .uv(30, 32)
                        .cuboid(
                                -2.0F, -4.0F, -3.0F,
                                4.0F, 5.0F, 3.0F,
                                new Dilation(0.5F)
                        ),
                ModelTransform.pivot(0.0F, 14.0F, -2.0F)
        );

        modelPartData.addChild(
                "beak",
                ModelPartBuilder.create()
                        .uv(0, 28)
                        .cuboid(
                                0.0F, -8.0F, -5.0F,
                                0.0F, 12.0F, 7.0F,
                                new Dilation(0.0F)
                        ),
                ModelTransform.pivot(0.0F, 14.0F, -2.0F)
        );
        modelPartData.addChild(
                "red_thing",
                ModelPartBuilder.create()
                        .uv(32, 10)
                        .cuboid(
                                -2.0F, -2.0F, -5.0F,
                                4.0F, 2.0F, 2.0F,
                                new Dilation(0.0F)
                        ),
                ModelTransform.pivot(0.0F, 14.0F, -2.0F)
        );

        modelPartData.addChild(
                "right_leg",
                ModelPartBuilder.create()
                        .uv(30, 40)
                        .cuboid(
                                -1.5F, 0.0F, -3.0F,
                                3.0F, 5.0F, 3.0F,
                                new Dilation(0.0F)
                        ),
                ModelTransform.of(
                        -1.5F, 19.0F, 1.0F,
                        0.0F, 0.2182F, 0.0F
                )
        );

        modelPartData.addChild(
                "left_leg",
                ModelPartBuilder.create()
                        .uv(42, 22)
                        .cuboid(
                                -1.5F, 0.0F, -3.0F,
                                3.0F, 5.0F, 3.0F,
                                new Dilation(0.0F)
                        ),
                ModelTransform.of(
                        1.5F, 19.0F, 1.0F,
                        0.0F, -0.2182F, 0.0F
                )
        );

        modelPartData.addChild(
                "right_wing",
                ModelPartBuilder.create()
                        .uv(30, 22)
                        .cuboid(
                                0.0F, 0.0F, -3.0F,
                                0.0F, 4.0F, 6.0F,
                                new Dilation(0.0F)
                        ),
                ModelTransform.pivot(-3.6F, 15.0F, 0.0F)
        );

        modelPartData.addChild(
                "left_wing",
                ModelPartBuilder.create()
                        .uv(32, 0)
                        .cuboid(
                                0.0F, 0.0F, -3.0F,
                                0.0F, 4.0F, 6.0F,
                                new Dilation(0.0F)
                        ),
                ModelTransform.pivot(3.6F, 15.0F, 0.0F)
        );

        return modelData;
    }

    public static ModelData createSheepModel() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -10.0F, -10.0F, 10.0F, 17.0F, 9.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 5.0F, 2.0F, 1.5708F, 0.0F, 0.0F));
        body.addChild("tail", ModelPartBuilder.create().uv(0, 40).cuboid(-1.5F, -0.15F, -4.0F, 3.0F, 0.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 7.25F, -3.0F));

        modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 26).cuboid(-3.0F, -4.0F, -5.0F, 6.0F, 8.0F, 6.0F, new Dilation(0.0F))
                .uv(14, 40).cuboid(3.0F, -3.0F, -1.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F))
                .uv(18, 40).cuboid(-5.0F, -3.0F, -1.0F, 2.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 8.0F, -9.0F));

        modelPartData.addChild("right_hind_leg", ModelPartBuilder.create().mirrored().uv(24, 26).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 15.0F, 7.0F));
        modelPartData.addChild("left_hind_leg", ModelPartBuilder.create().uv(24, 26).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, 15.0F, 7.0F));
        modelPartData.addChild("right_front_leg", ModelPartBuilder.create().mirrored().uv(38, 13).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 15.0F, -5.0F));
        modelPartData.addChild("left_front_leg", ModelPartBuilder.create().uv(38, 13).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, 15.0F, -5.0F));

        return modelData;
    }

    public static ModelData createSheepWoolModel() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 33).cuboid(-6.0F, -10.0F, -10.0F, 12.0F, 18.0F, 10.0F, new Dilation(0.5F))
                .uv(0, 0).cuboid(-7.0F, -11.0F, -11.0F, 14.0F, 20.0F, 13.0F, new Dilation(0.5F)), ModelTransform.of(0.0F, 5.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

        modelPartData.addChild("head", ModelPartBuilder.create().uv(44, 33).cuboid(-3.0F, -4.0F, -5.0F, 6.0F, 8.0F, 6.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 8.0F, -9.0F));

        modelPartData.addChild("right_hind_leg", ModelPartBuilder.create().uv(44, 47).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.25F)), ModelTransform.pivot(-3.0F, 15.0F, 7.0F));
        modelPartData.addChild("left_hind_leg", ModelPartBuilder.create().uv(54, 0).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.25F)), ModelTransform.pivot(3.0F, 15.0F, 7.0F));
        modelPartData.addChild("right_front_leg", ModelPartBuilder.create().uv(54, 7).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.25F)), ModelTransform.pivot(-3.0F, 15.0F, -5.0F));
        modelPartData.addChild("left_front_leg", ModelPartBuilder.create().uv(54, 14).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.25F)), ModelTransform.pivot(3.0F, 15.0F, -5.0F));

        return modelData;
    }
}
