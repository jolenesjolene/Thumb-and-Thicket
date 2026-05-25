package net.jolene.thumbandthicket.block;

import net.jolene.thumbandthicket.util.ModProperties;
import net.jolene.thumbandthicket.util.VentPart;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class HydrothermalVentBlock extends PillarBlock {
    public static Direction SIDE = Direction.DOWN;

    public HydrothermalVentBlock(Settings settings) {
        super(settings);
        super.setDefaultState(this.stateManager.getDefaultState().with(ModProperties.VENT_PART, VentPart.TOP).with(ModProperties.ACTIVE, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ModProperties.VENT_PART);
        builder.add(ModProperties.ACTIVE);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BlockState state1 = world.getBlockState(pos.up());
        BlockState state2 = world.getBlockState(pos.down());
        Direction.Axis axis = state.get(AXIS);
        if (axis == Direction.Axis.X) state1 = world.getBlockState(pos.offset(SIDE.getOpposite()));
        if (axis == Direction.Axis.Z) state1 = world.getBlockState(pos.offset(SIDE.getOpposite()));
        boolean active = false;
        if (state2.contains(ModProperties.ACTIVE)) active = state2.get(ModProperties.ACTIVE);
        if (state1.isOf(this)) state = state.with(ModProperties.VENT_PART, VentPart.BASE);

        return state.with(ModProperties.ACTIVE, active);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        SIDE = ctx.getSide();

        BlockState state = this.getDefaultState();
        BlockState state1 = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
        if (SIDE.getAxis() == Direction.Axis.X) state1 = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(SIDE.getOpposite()));
        if (SIDE.getAxis() == Direction.Axis.Z) state1 = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(SIDE.getOpposite()));
        boolean active = false;
        if (state1.contains(ModProperties.ACTIVE)) active = state1.get(ModProperties.ACTIVE);
        return state.with(AXIS, ctx.getSide().getAxis()).with(ModProperties.ACTIVE, active);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return state.isSideSolidFullSquare(world, pos, SIDE);
    }
}
