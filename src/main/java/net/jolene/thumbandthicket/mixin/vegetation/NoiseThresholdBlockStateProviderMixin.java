package net.jolene.thumbandthicket.mixin.vegetation;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.stateprovider.NoiseThresholdBlockStateProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jolene.thumbandthicket.ThumbAndThicket.RANDOM;

@Mixin(NoiseThresholdBlockStateProvider.class)
public class NoiseThresholdBlockStateProviderMixin {

    @Inject(method = "get", at = @At("RETURN"), cancellable = true)
    private void thumbandthicket$randomizeFlowerBlockstates(Random random, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        BlockState state = cir.getReturnValue();
        if (state.getBlock() instanceof FlowerBlock && random != null) cir.setReturnValue(state.with(ModProperties.FLOWERS, random.nextBetween(1,3)).with(Properties.FACING, Direction.Type.HORIZONTAL.random(random)));
    }
}
