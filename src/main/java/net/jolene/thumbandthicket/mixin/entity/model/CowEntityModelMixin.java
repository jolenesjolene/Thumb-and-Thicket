package net.jolene.thumbandthicket.mixin.entity.model;

import net.jolene.thumbandthicket.util.GrazingAnimator;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.entity.passive.CowEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(CowEntityModel.class)
public class CowEntityModelMixin<T extends CowEntity> extends QuadrupedEntityModel<T> {

    @Unique
    private float headPitchModifier;

    protected CowEntityModelMixin(ModelPart root, boolean headScaled, float childHeadYOffset, float childHeadZOffset, float invertedChildHeadScale, float invertedChildBodyScale, int childBodyYOffset) {
        super(root, headScaled, childHeadYOffset, childHeadZOffset, invertedChildHeadScale, invertedChildBodyScale, childBodyYOffset);
    }

    /**
     * @author gayasslily
     * @reason change cow model
     */
    @Overwrite
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("head", ModelPartBuilder.create().uv(64, 0).cuboid(-4.0F, -5.0F, -7.0F, 8.0F, 10.0F, 6.0F, new Dilation(0.0F))
                        .uv(64, 16).cuboid(-4.0F, -5.0F, -7.0F, 8.0F, 10.0F, 6.0F, new Dilation(0.5F))
                        .uv(36, 66).cuboid("right_horn",4.0F, -6.0F, -5.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                        .uv(40, 66).cuboid("left_horn",-5.0F, -6.0F, -5.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                        .uv(24, 66).cuboid("right_ear",4.0F, -4.0F, -4.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F))
                        .uv(30, 66).cuboid("left_ear",-7.0F, -4.0F, -4.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 3.0F, -6.0F));
        modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -6.0F, -10.0F, 12.0F, 13.0F, 20.0F, new Dilation(0.0F)).uv(0, 33).cuboid(-6.0F, -6.0F, -10.0F, 12.0F, 13.0F, 20.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 5.0F, 3.0F));
        modelPartData.getChild("body").addChild("tail", ModelPartBuilder.create().uv(16, 66).cuboid(-2.0F, -17.0F, -9.0F, 4.0F, 10.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 12.0F, 19.0F, 0.0F, 0.0F, 0.0F));
        modelPartData.addChild("right_hind_leg",ModelPartBuilder.create().uv(0, 66).cuboid(-2.0F, 0.0F, 2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)),ModelTransform.pivot(-4,12,7));
        modelPartData.addChild("left_hind_leg",ModelPartBuilder.create().uv(64, 64).cuboid(-2.0F, 0.0F, 2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)),ModelTransform.pivot(4,12,7));
        modelPartData.addChild("right_front_leg",ModelPartBuilder.create().uv(64, 48).cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)),ModelTransform.pivot(-4,12,-6));
        modelPartData.addChild("left_front_leg", ModelPartBuilder.create().uv(64, 32).cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(4,12, -6));

        return TexturedModelData.of(modelData, 96, 96);
    }

    public void animateModel(T cowEntity, float f, float g, float h) {
        super.animateModel(cowEntity, f, g, h);
        GrazingAnimator grazingCow = (GrazingAnimator) cowEntity;
        this.head.pivotY = 3.0F + grazingCow.thumbandthicket$getNeckAngle(h) * 9.0F;
        this.headPitchModifier = grazingCow.thumbandthicket$getHeadAngle(h);
    }

    public void setAngles(T cowEntity, float f, float g, float h, float i, float j) {
        super.setAngles(cowEntity, f, g, h, i, j);
        this.head.pitch = this.headPitchModifier;
    }
}
