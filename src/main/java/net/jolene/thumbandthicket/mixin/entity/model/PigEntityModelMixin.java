package net.jolene.thumbandthicket.mixin.entity.model;

import net.jolene.thumbandthicket.util.GrazingAnimator;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.entity.passive.PigEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PigEntityModel.class)
public class PigEntityModelMixin<T extends PigEntity> extends QuadrupedEntityModel<T> {

    @Unique
    private float headPitchModifier;

    protected PigEntityModelMixin(ModelPart root, boolean headScaled, float childHeadYOffset, float childHeadZOffset, float invertedChildHeadScale, float invertedChildBodyScale, int childBodyYOffset) {
        super(root, headScaled, childHeadYOffset, childHeadZOffset, invertedChildHeadScale, invertedChildBodyScale, childBodyYOffset);
    }

    /**
     * @author gayasslily
     * @reason change pig model
     */
    @Overwrite
    public static TexturedModelData getTexturedModelData(Dilation dilation) {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 50).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 6.0F, new Dilation(0.0F))
                        .uv(28, 50).cuboid("head_outer",-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 6.0F, new Dilation(0.5F))
                        .uv(52, 38).cuboid("snout",-2.0F, 0.0F, -5.0F, 4.0F, 3.0F, 1.0F, new Dilation(0.0F)),
                        ModelTransform.pivot(0.0F, 15.0F, -10.0F));

        modelPartData.getChild("head").addChild("left_ear", ModelPartBuilder.create().uv(52, 42).cuboid("left_ear",0.0F, 0.0F, 0.0F, 0.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, -2.0F, -3.5F));
        modelPartData.getChild("head").addChild("right_ear", ModelPartBuilder.create().uv(56, 49).cuboid("right_ear",0.0F, 0.0F, 0.0F, 0.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, -2.0F, -3.5F));

        modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, 0.0F, -8.0F, 10.0F, 9.0F, 16.0F, new Dilation(0.0F)).uv(0, 25).cuboid("body_outer",-5.0F, -1.0F, -8.0F, 10.0F, 9.0F, 16.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 10.0F, 0.0F));
        modelPartData.addChild("left_hind_leg",ModelPartBuilder.create().uv(52, 10).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)),ModelTransform.pivot(3F,18F,7F));
        modelPartData.addChild("right_front_leg",ModelPartBuilder.create().uv(52, 29).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F)),ModelTransform.pivot(-3F,19F,-6F));
        modelPartData.addChild("left_front_leg",ModelPartBuilder.create().uv(52, 20).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F)),ModelTransform.pivot(3F,19F,-6F));
        modelPartData.addChild("right_hind_leg", ModelPartBuilder.create().uv(52, 0).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-3F,18F, 7F));

        return TexturedModelData.of(modelData, 128, 128);
    }

    public void animateModel(T pigEntity, float f, float g, float h) {
        super.animateModel(pigEntity, f, g, h);
        GrazingAnimator grazingPig = (GrazingAnimator) pigEntity;
        this.head.pivotY = 15.0F + grazingPig.thumbandthicket$getNeckAngle(h) * 9.0F;
        this.headPitchModifier = grazingPig.thumbandthicket$getHeadAngle(h);
    }

    public void setAngles(T pigEntity, float f, float g, float h, float i, float j) {
        super.setAngles(pigEntity, f, g, h, i, j);
        this.head.pitch = this.headPitchModifier;
    }
}
