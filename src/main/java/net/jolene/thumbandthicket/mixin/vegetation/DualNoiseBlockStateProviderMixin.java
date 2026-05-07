package net.jolene.thumbandthicket.mixin.vegetation;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.stateprovider.DualNoiseBlockStateProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import static net.jolene.thumbandthicket.ThumbAndThicket.RANDOM;


@Mixin(DualNoiseBlockStateProvider.class)
public class DualNoiseBlockStateProviderMixin {

    @Inject(method = "get", at = @At("HEAD"))
    private void thumbandthicket$randomizeFlowerBlockstates(Random random, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        RANDOM = random;
    }
}
