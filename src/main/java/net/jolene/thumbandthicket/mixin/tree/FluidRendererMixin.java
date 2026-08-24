package net.jolene.thumbandthicket.mixin.tree;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.util.Rooty;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;

import static net.jolene.thumbandthicket.util.ModProperties.ROOTY;

@Mixin(FluidRenderer.class)
public class FluidRendererMixin {

    @WrapMethod(method = "isSideCovered(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/Direction;FLnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z")
    private static boolean thumbandthicket$isLogCovered(BlockView world, Direction direction, float height, BlockPos pos, BlockState state, Operation<Boolean> original) {
        return state.contains(ROOTY) && state.get(ROOTY) != Rooty.NONE || original.call(world, direction, height, pos, state);
    }
}
