package net.jolene.thumbandthicket.mixin.vegetation;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.stateprovider.NoiseBlockStateProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static net.jolene.thumbandthicket.ThumbAndThicket.RANDOM;

@Mixin(NoiseBlockStateProvider.class)
public class NoiseBlockStateProviderMixin {

    @Inject(method = "get", at = @At("HEAD"))
    private void thumbandthicket$randomizeFlowerBlockstates(Random random, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        RANDOM = random;
    }

    @WrapMethod(method = "getStateAtValue")
    protected BlockState thumbandthicket$randomizeFlowerBlockstates(List<BlockState> states, double value, Operation<BlockState> original) {
        double d = MathHelper.clamp((1.0 + value) / 2.0, 0.0, 0.9999);
        BlockState state = states.get((int)(d * (double)states.size()));
        if (state.getBlock() instanceof FlowerBlock && RANDOM != null) return state.with(ModProperties.FLOWERS, RANDOM.nextBetween(1,3)).with(Properties.FACING, Direction.Type.HORIZONTAL.random(RANDOM));
        return original.call(states, value);
    }
}
