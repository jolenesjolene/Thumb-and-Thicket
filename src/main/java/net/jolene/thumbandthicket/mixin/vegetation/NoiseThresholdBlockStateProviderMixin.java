package net.jolene.thumbandthicket.mixin.vegetation;

import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.stateprovider.NoiseThresholdBlockStateProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseThresholdBlockStateProvider.class)
public class NoiseThresholdBlockStateProviderMixin {

    @Inject(method = "get", at = @At("RETURN"), cancellable = true)
    private void thumbandthicket$randomizeFlowerBlockstates(Random random, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        BlockState state = cir.getReturnValue();
        if (state.getBlock() instanceof FlowerBlock) cir.setReturnValue(state.with(ModProperties.FLOWERS, random.nextBetween(1,3)));
    }
}
