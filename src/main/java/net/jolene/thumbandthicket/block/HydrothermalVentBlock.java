package net.jolene.thumbandthicket.block;

import net.jolene.thumbandthicket.util.ModProperties;
import net.jolene.thumbandthicket.util.VentPart;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import static net.minecraft.state.property.Properties.WATERLOGGED;

public class HydrothermalVentBlock extends Block implements Waterloggable {

    public HydrothermalVentBlock(Settings settings) {
        super(settings);
        super.setDefaultState(this.stateManager.getDefaultState().with(ModProperties.VENT_PART, VentPart.TOP).with(ModProperties.ACTIVE, false).with(Properties.WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ModProperties.VENT_PART);
        builder.add(ModProperties.ACTIVE);
        builder.add(Properties.FACING);
        builder.add(WATERLOGGED);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BlockState state1 = world.getBlockState(pos.up());
        BlockState state2 = world.getBlockState(pos.down());
        if (state.get(Properties.FACING) != Direction.DOWN && state.get(Properties.FACING) != Direction.UP) state2 = world.getBlockState(pos.offset(state.get(Properties.FACING)));
        boolean active = false;
        if (state2.contains(ModProperties.ACTIVE)) active = state2.get(ModProperties.ACTIVE);
        if (state1.isOf(this)) state = state.with(ModProperties.VENT_PART, VentPart.BASE);

        return state.with(ModProperties.ACTIVE, active);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        net.minecraft.util.math.Direction direction = ctx.getSide();

        BlockState state = this.getDefaultState();
        BlockState state1 = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
        if (direction != Direction.UP && direction != Direction.DOWN) state1 = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(direction.getOpposite()));
        boolean active = false;
        if (state1.contains(ModProperties.ACTIVE)) active = state1.get(ModProperties.ACTIVE);
        return state.with(ModProperties.ACTIVE, active).with(Properties.FACING, ctx.getSide().getOpposite()).with(WATERLOGGED, fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return state.isSideSolidFullSquare(world, pos, state.get(Properties.FACING).getOpposite());
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }
}
