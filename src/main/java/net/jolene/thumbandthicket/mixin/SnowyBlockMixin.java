package net.jolene.thumbandthicket.mixin;

import net.jolene.thumbandthicket.block.ShortSnowyPlantBlock;
import net.jolene.thumbandthicket.block.TallSnowyPlantBlock;
import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowyBlock;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
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
