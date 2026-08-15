package net.jolene.thumbandthicket.mixin.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jolene.thumbandthicket.util.ModelUtil;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.client.render.entity.model.SheepWoolEntityModel;
import net.minecraft.entity.passive.SheepEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Environment(EnvType.CLIENT)
@Mixin(SheepWoolEntityModel.class)
public class SheepWoolEntityModelMixin<T extends SheepEntity> extends QuadrupedEntityModel<T> {

    @Shadow
    private float headPitchModifier;

    protected SheepWoolEntityModelMixin(ModelPart root, boolean headScaled, float childHeadYOffset, float childHeadZOffset, float invertedChildHeadScale, float invertedChildBodyScale, int childBodyYOffset) {
        super(root, headScaled, childHeadYOffset, childHeadZOffset, invertedChildHeadScale, invertedChildBodyScale, childBodyYOffset);
    }

    /**
     * @author gayasslily
     * @reason change sheep model
     */
    @Overwrite
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = ModelUtil.createSheepWoolModel();

        return TexturedModelData.of(modelData, 128, 128);
    }

    /**
     * @author gayasslily
     * @reason change sheep model
     */
    @Overwrite
    public void animateModel(T sheepEntity, float f, float g, float h) {
        super.animateModel(sheepEntity, f, g, h);
        this.head.pivotY = 8.0F + sheepEntity.getNeckAngle(h) * 9.0F;
        this.headPitchModifier = sheepEntity.getHeadAngle(h);
    }
}
