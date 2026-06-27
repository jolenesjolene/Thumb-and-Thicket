package net.jolene.thumbandthicket.util;

import net.minecraft.client.model.*;

public class ModelUtil {

    public static ModelData createCowModel() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("head", ModelPartBuilder.create().uv(64, 0).cuboid(-4.0F, -5.0F, -7.0F, 8.0F, 10.0F, 6.0F, new Dilation(0.0F))
                        .uv(64, 16).cuboid(-4.0F, -5.0F, -7.0F, 8.0F, 10.0F, 6.0F, new Dilation(0.5F))
                        .uv(24, 66).cuboid("right_ear",4.0F, -4.0F, -4.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F))
                        .uv(30, 66).cuboid("left_ear",-7.0F, -4.0F, -4.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 3.0F, -6.0F));
        modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -6.0F, -10.0F, 12.0F, 13.0F, 20.0F, new Dilation(0.0F)).uv(0, 33).cuboid(-6.0F, -6.0F, -10.0F, 12.0F, 13.0F, 20.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 5.0F, 3.0F));
        modelPartData.getChild("body").addChild("tail", ModelPartBuilder.create().uv(16, 66).cuboid(-2.0F, -17.0F, -9.0F, 4.0F, 10.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 12.0F, 19.0F, 0.0F, 0.0F, 0.0F));
        modelPartData.addChild("right_hind_leg",ModelPartBuilder.create().uv(0, 66).cuboid(-2.0F, 0.0F, 2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)),ModelTransform.pivot(-4,12,7));
        modelPartData.addChild("left_hind_leg",ModelPartBuilder.create().uv(64, 64).cuboid(-2.0F, 0.0F, 2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)),ModelTransform.pivot(4,12,7));
        modelPartData.addChild("right_front_leg",ModelPartBuilder.create().uv(64, 48).cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)),ModelTransform.pivot(-4,12,-6));
        modelPartData.addChild("left_front_leg", ModelPartBuilder.create().uv(64, 32).cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(4,12, -6));
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
}
