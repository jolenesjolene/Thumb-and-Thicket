package net.jolene.thumbandthicket.mixin.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jolene.thumbandthicket.util.ModelUtil;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Environment(EnvType.CLIENT)
@Mixin(SheepEntityModel.class)
public class SheepEntityModelMixin {

    /**
     * @author gayasslily
     * @reason change sheep model
     */
    @Overwrite
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = ModelUtil.createSheepModel();

        return TexturedModelData.of(modelData, 64, 64);
    }
}
