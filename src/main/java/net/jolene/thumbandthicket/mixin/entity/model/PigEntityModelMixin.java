package net.jolene.thumbandthicket.mixin.entity.model;

import net.jolene.thumbandthicket.util.GrazingAnimator;
import net.jolene.thumbandthicket.util.ModelUtil;
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
        ModelData modelData = ModelUtil.createPigModel();

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
