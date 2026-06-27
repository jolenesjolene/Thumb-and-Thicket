package net.jolene.thumbandthicket.mixin.entity.model.variant.cow;

import com.blackgear.vanillabackport.client.level.entities.model.cow.ColdCowModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jolene.thumbandthicket.util.ModelUtil;
import net.minecraft.client.model.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Environment(EnvType.CLIENT)
@Mixin(ColdCowModel.class)
public class ColdCowModelMixin {
    /**
     * @author gayasslily
     * @reason change cow model
     */
    @Overwrite
    public static TexturedModelData createBodyLayer() {
        ModelData modelData = ModelUtil.createCowModel();
        ModelPartData root = modelData.getRoot();
        root.addChild("right_horn", ModelPartBuilder.create().uv(36, 66).cuboid(4.0F, -6.0F, -5.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.NONE);
        root.addChild("left_horn", ModelPartBuilder.create().uv(40, 66).cuboid("left_horn",-5.0F, -6.0F, -5.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.NONE);

        return TexturedModelData.of(modelData, 96, 96);
    }
}
