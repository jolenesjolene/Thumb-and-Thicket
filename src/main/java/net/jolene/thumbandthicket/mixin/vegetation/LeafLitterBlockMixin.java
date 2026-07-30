package net.jolene.thumbandthicket.mixin.vegetation;

import com.blackgear.vanillabackport.common.level.blocks.LeafLitterBlock;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LeafLitterBlock.class)
public class LeafLitterBlockMixin {

    @WrapMethod(method = "canPlaceAt")
    private boolean thumbandthicket$canPlaceAt(BlockState state, WorldView world, BlockPos pos, Operation<Boolean> original) {
        BlockPos below = pos.down();
        FluidState fluidState = world.getFluidState(below);
        FluidState fluidState2 = world.getFluidState(pos);
        return (fluidState.getFluid() == Fluids.WATER && fluidState2.getFluid() == Fluids.EMPTY) || (world.getBlockState(below).isSideSolidFullSquare(world, below, Direction.UP) && fluidState.isEmpty() && fluidState2.isEmpty());
    }
}
