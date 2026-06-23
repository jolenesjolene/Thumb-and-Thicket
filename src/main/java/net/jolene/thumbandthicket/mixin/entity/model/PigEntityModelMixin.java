package net.jolene.thumbandthicket.mixin.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jolene.thumbandthicket.mixin.entity.PigEntityMixin;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Environment(value = EnvType.CLIENT)
@Mixin(PigEntityModel.class)
public class PigEntityModelMixin<T extends PigEntityMixin> extends QuadrupedEntityModel<T> {
    @Unique
    private float headPitchModifier;

    protected PigEntityModelMixin(ModelPart root, boolean headScaled, float childHeadYOffset, float childHeadZOffset, float invertedChildHeadScale, float invertedChildBodyScale, int childBodyYOffset) {
        super(root, headScaled, childHeadYOffset, childHeadZOffset, invertedChildHeadScale, invertedChildBodyScale, childBodyYOffset);
    }

    public void animateModel(T farmAnimalEntity, float f, float g, float h) {
        super.animateModel(farmAnimalEntity, f, g, h);
        this.head.pivotY = 6.0F + farmAnimalEntity.getNeckAngle(h) * 9.0F;
        this.headPitchModifier = farmAnimalEntity.getHeadAngle(h);
    }

    public void setAngles(T farmAnimalEntity, float f, float g, float h, float i, float j) {
        super.setAngles(farmAnimalEntity, f, g, h, i, j);
        this.head.pitch = this.headPitchModifier;
    }
}
