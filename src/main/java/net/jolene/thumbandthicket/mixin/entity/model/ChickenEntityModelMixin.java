package net.jolene.thumbandthicket.mixin.entity.model;

import net.jolene.thumbandthicket.util.ModelUtil;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ChickenEntityModel.class)
public class ChickenEntityModelMixin {

    /**
     * @author gayasslily
     * @reason change chicken model
     */
    @Overwrite
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = ModelUtil.createChickenModel();
        ModelPartData root = modelData.getRoot();

        return TexturedModelData.of(modelData, 64, 64);
    }
}
