package net.jolene.thumbandthicket.mixin.entity.model;

import net.jolene.thumbandthicket.util.GrazingAnimator;
import net.jolene.thumbandthicket.util.ModelUtil;
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
        ModelData modelData = ModelUtil.createCowModel();
        ModelPartData root = modelData.getRoot();
        root.getChild("head").addChild("right_horn", ModelPartBuilder.create().uv(36, 66).cuboid(4.0F, -6.0F, -5.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.NONE);
        root.getChild("head").addChild("left_horn", ModelPartBuilder.create().uv(40, 66).cuboid("left_horn",-5.0F, -6.0F, -5.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.NONE);

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
