package net.jolene.thumbandthicket.mixin.entity.model;

import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jolene.thumbandthicket.util.CoatTwoUtil;
import net.minecraft.client.render.entity.feature.SheepWoolFeatureRenderer;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.Identifier;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(SheepWoolFeatureRenderer.class)
public class SheepWoolFeatureRendererMixin {

    @Redirect(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/SheepEntity;FFFFFF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/feature/SheepWoolFeatureRenderer;SKIN:Lnet/minecraft/util/Identifier;", opcode = Opcodes.GETSTATIC))
    private Identifier thumbandthicket$changeSheepWool(@Local(argsOnly = true) SheepEntity sheepEntity) {
        CoatTwoUtil secondLayerSheep = (CoatTwoUtil) sheepEntity;
        if (secondLayerSheep.thumbandthicket$hasCoatTwo()) return Identifier.ofVanilla("textures/entity/sheep/sheep_coat_two.png");
        return Identifier.ofVanilla("textures/entity/sheep/sheep_coat_one.png");
    }
}
