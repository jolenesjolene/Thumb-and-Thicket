package net.jolene.thumbandthicket.mixin.entity.model.variant.cow;

import com.blackgear.vanillabackport.client.level.entities.model.cow.CowVariantModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jolene.thumbandthicket.util.ModelUtil;
import net.minecraft.client.model.ModelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Environment(EnvType.CLIENT)
@Mixin(CowVariantModel.class)
public class CowVariantModelMixin {

    /**
     * @author gayasslily
     * @reason change cow model
     */
    @Overwrite
    static ModelData createBaseCowModel() {
        return ModelUtil.createCowModel();
    }
}
