package net.jolene.thumbandthicket.mixin.snowy;

import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowyBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnowyBlock.class)
public class SnowyBlockMixin {

    @Inject(method = "isSnow", at = @At("HEAD"), cancellable = true)
    private static void thumbandthicket$snowyIfSnowyPlant(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.contains(ModProperties.LAYERS)) cir.setReturnValue(state.get(ModProperties.LAYERS) > 0);
    }
}
