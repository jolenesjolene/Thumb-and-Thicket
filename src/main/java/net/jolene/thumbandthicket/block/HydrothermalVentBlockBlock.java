package net.jolene.thumbandthicket.block;

import net.jolene.thumbandthicket.util.ModProperties;
import net.jolene.thumbandthicket.util.VentPart;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class HydrothermalVentBlockBlock extends Block {
    public HydrothermalVentBlockBlock(Settings settings) {
        super(settings);
        super.setDefaultState(this.stateManager.getDefaultState().with(ModProperties.ACTIVE, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ModProperties.ACTIVE);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        state = this.getDefaultState();
        BlockState state1 = world.getBlockState(pos.down());
        if (state1.isOf(Blocks.MAGMA_BLOCK)) state = state.with(ModProperties.ACTIVE, true);
        return state;
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = this.getDefaultState();
        BlockState state1 = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
        if (state1.isOf(Blocks.MAGMA_BLOCK)) state = state.with(ModProperties.ACTIVE, true);
        return state;
    }
}
