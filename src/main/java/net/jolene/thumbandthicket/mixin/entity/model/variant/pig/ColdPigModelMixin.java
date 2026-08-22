package net.jolene.thumbandthicket.mixin.entity.model.variant.pig;

import com.blackgear.vanillabackport.client.level.entities.model.pig.ColdPigModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jolene.thumbandthicket.util.ModelUtil;
import net.minecraft.client.model.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(ColdPigModel.class)
public class ColdPigModelMixin {
    /**
     * @author gayasslily
     * @reason change cow model
     */
    @Overwrite
    public static TexturedModelData createBodyLayer() {
        ModelData modelData = ModelUtil.createPigModel();
        ModelPartData root = modelData.getRoot();

        return TexturedModelData.of(modelData, 128, 128);
    }
}
