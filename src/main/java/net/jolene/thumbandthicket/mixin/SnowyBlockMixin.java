package net.jolene.thumbandthicket.mixin;

import net.jolene.thumbandthicket.block.ShortSnowyPlantBlock;
import net.jolene.thumbandthicket.block.TallSnowyPlantBlock;
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

    @Inject(method = "getStateForNeighborUpdate", at = @At("HEAD"), cancellable = true)
    private void thumbandthicket$snowyIfSnowyPlant(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
        if ((world.getBlockState(pos.up()).getBlock() instanceof ShortSnowyPlantBlock) || (world.getBlockState(pos.up()).getBlock() instanceof TallSnowyPlantBlock)) cir.setReturnValue(state.with(SnowyBlock.SNOWY, true));
    }

    @Inject(method = "getPlacementState", at = @At("HEAD"), cancellable = true)
    private void thumbandthicket$snowyIfSnowyPlant(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        Block snowyBlock = SnowyBlock.class.cast(this);
        BlockState defaultBlockState = snowyBlock.getDefaultState();
        if ((world.getBlockState(pos.up()).getBlock() instanceof ShortSnowyPlantBlock) || (world.getBlockState(pos.up()).getBlock() instanceof TallSnowyPlantBlock)) {
            cir.setReturnValue(defaultBlockState.with(SnowyBlock.SNOWY, true));
        }
    }
}
