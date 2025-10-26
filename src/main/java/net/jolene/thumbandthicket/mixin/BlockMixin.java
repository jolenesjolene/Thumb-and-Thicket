package net.jolene.thumbandthicket.mixin;

import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.util.Side;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.Direction;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jolene.thumbandthicket.util.ModProperties.*;
import static net.minecraft.block.PillarBlock.AXIS;

@Mixin(Block.class)
public class BlockMixin {

    @Shadow private BlockState defaultState;

    @Inject(method = "getPlacementState", at = @At("HEAD"), cancellable = true)
    private void modifyPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        Block block = (Block)(Object)this;
        BlockState state = block.getDefaultState();
        if (state.contains(ROOTY) && state.contains(SIDE)) {
            if (state.get(AXIS).isVertical()) {
                Block blockDown = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(Direction.Axis.Y,-1)).getBlock();
                Block blockUp = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(Direction.Axis.Y,+1)).getBlock();
                if (blockDown.equals(ModBlocks.ROOT_BLOCK)) cir.setReturnValue(defaultState.with(SIDE, Side.TOP));
                if (blockUp.equals(ModBlocks.ROOT_BLOCK)) cir.setReturnValue(defaultState.with(SIDE, Side.BOTTOM));
            }
        }
    }
}
