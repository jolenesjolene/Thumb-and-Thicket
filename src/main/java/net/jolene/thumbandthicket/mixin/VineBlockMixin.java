package net.jolene.thumbandthicket.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.jolene.thumbandthicket.util.ModProperties.*;

@Mixin(VineBlock.class)
public class VineBlockMixin {
    @WrapMethod(method = "randomTick")
    private void thumbandthicket$cancelWhenSnipped(BlockState state, ServerWorld world, BlockPos pos, Random random, Operation<Void> original) {
        if (!state.get(SNIPPED)) original.call(state, world, pos, random);
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void thumbandthicket$addSnipped(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(SNIPPED);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void appendLogPropertiesValue(AbstractBlock.Settings settings, CallbackInfo ci) {
        Block vineBlock = VineBlock.class.cast(this);
        BlockState defaultBlockState = vineBlock.getDefaultState();
        ((BlockAccessor) vineBlock).invokeSetDefaultState(defaultBlockState.with(SNIPPED, false));
    }
}
