package net.jolene.thumbandthicket.mixin.entity.model.variant.chicken;

import com.blackgear.vanillabackport.client.level.entities.model.chicken.ColdChickenModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jolene.thumbandthicket.util.ModelUtil;
import net.minecraft.client.model.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Environment(EnvType.CLIENT)
@Mixin(ColdChickenModel.class)
public class ColdChickenModelMixin {
    /**
     * @author gayasslily
     * @reason change chicken model
     */
    @Overwrite
    public static TexturedModelData createBodyLayer() {
        ModelData modelData = ModelUtil.createChickenModel();

        return TexturedModelData.of(modelData, 64, 64);
    }
}
