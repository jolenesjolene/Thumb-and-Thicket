package net.jolene.thumbandthicket.mixin.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jolene.thumbandthicket.util.ModelUtil;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.SheepWoolEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Environment(EnvType.CLIENT)
@Mixin(SheepWoolEntityModel.class)
public class SheepWoolEntityModelMixin {

    /**
     * @author gayasslily
     * @reason change sheep model
     */
    @Overwrite
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = ModelUtil.createSheepWoolModel();

        return TexturedModelData.of(modelData, 64, 64);
    }
}
