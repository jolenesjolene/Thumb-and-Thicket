package net.jolene.thumbandthicket.mixin.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.CowEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(CowEntityModel.class)
public class CowEntityModelMixin {
    /**
     * @author gayasslily
     * @reason change cow model
     */
    @Overwrite
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 33).cuboid(-4.0F, -23.0F, -5.0F, 8.0F, 10.0F, 6.0F, new Dilation(0.0F))
                .uv(40, 52).cuboid("right_horn",4.0F, -24.0F, -3.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(44, 52).cuboid("left_horn",-5.0F, -24.0F, -3.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
                .uv(40, 49).cuboid("right_ear",4.0F, -22.0F, -2.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F))
                .uv(46, 49).cuboid("left_ear",-7.0F, -22.0F, -2.0F, 3.0F, 3.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 21.0F, -8.0F));
        modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -6.0F, -9.0F, 12.0F, 13.0F, 20.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 5.0F, 2.0F));
        modelPartData.getChild("body").addChild("tail", ModelPartBuilder.create().uv(32, 49).cuboid(-2.0F, -17.0F, -8.0F, 4.0F, 10.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 12.0F, 19.0F, 0.0F, 0.0F, 0.0F));
        modelPartData.addChild("right_hind_leg",ModelPartBuilder.create().uv(16, 49).cuboid(-2.0F, 0.0F, 2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)),ModelTransform.pivot(-4,12,7));
        modelPartData.addChild("left_hind_leg",ModelPartBuilder.create().uv(0, 49).cuboid(-2.0F, 0.0F, 2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)),ModelTransform.pivot(4,12,7));
        modelPartData.addChild("right_front_leg",ModelPartBuilder.create().uv(44, 33).cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)),ModelTransform.pivot(-4,12,-6));
        modelPartData.addChild("left_front_leg", ModelPartBuilder.create().uv(28, 33).cuboid(-2.0F, 0.0F, -1.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(4,12, -6));


        return TexturedModelData.of(modelData, 128, 128);
    }

}
