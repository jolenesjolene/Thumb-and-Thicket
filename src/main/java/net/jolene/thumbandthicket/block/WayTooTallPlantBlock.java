package net.jolene.thumbandthicket.block;

import com.mojang.serialization.MapCodec;
import net.jolene.thumbandthicket.util.ModProperties;
import net.jolene.thumbandthicket.util.TripleTallBlock;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class WayTooTallPlantBlock extends PlantBlock {

    protected WayTooTallPlantBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(PART, TripleTallBlock.BOTTOM));
    }
    public static final MapCodec<WayTooTallPlantBlock> CODEC = WayTooTallPlantBlock.createCodec(WayTooTallPlantBlock::new);

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    public static EnumProperty<TripleTallBlock> PART = ModProperties.TALL_PLANT_PART_VERTICAL;

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        TripleTallBlock verticalPlantPart = state.get(PART);
        if (!(direction.getAxis() != Direction.Axis.Y || verticalPlantPart == TripleTallBlock.BOTTOM != (direction == Direction.UP) || neighborState.isOf(this) && neighborState.get(PART) != verticalPlantPart)) {
            return Blocks.AIR.getDefaultState();
        }
        if (!(direction.getAxis() != Direction.Axis.Y || verticalPlantPart == TripleTallBlock.MIDDLE != (direction == Direction.UP) || neighborState.isOf(this) && neighborState.get(PART) != verticalPlantPart)) {
            return Blocks.AIR.getDefaultState();
        }
        if (verticalPlantPart == TripleTallBlock.BOTTOM && direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        if (blockPos.getY() < world.getTopY() - 2 && world.getBlockState(blockPos.up()).canReplace(ctx) && world.getBlockState(blockPos.up(2)).canReplace(ctx)) {
            return super.getPlacementState(ctx);
        }
        return null;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockPos blockPos = pos.up();
        world.setBlockState(blockPos, WayTooTallPlantBlock.withWaterloggedState(world, blockPos, this.getDefaultState().with(ModProperties.TALL_PLANT_PART_VERTICAL, TripleTallBlock.MIDDLE)), Block.NOTIFY_ALL);
        world.setBlockState(blockPos.up(), WayTooTallPlantBlock.withWaterloggedState(world, blockPos.up(), this.getDefaultState().with(ModProperties.TALL_PLANT_PART_VERTICAL, TripleTallBlock.TOP)), Block.NOTIFY_ALL);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(PART) == TripleTallBlock.TOP) {
            BlockState blockState = world.getBlockState(pos.down());
            return blockState.isOf(this) && blockState.get(PART) == TripleTallBlock.MIDDLE;
        }
        if (state.get(PART) == TripleTallBlock.MIDDLE) {
            BlockState blockState = world.getBlockState(pos.down());
            return blockState.isOf(this) && blockState.get(PART) == TripleTallBlock.BOTTOM;
        }
        return super.canPlaceAt(state, world, pos);
    }

    public static void placeAt(WorldAccess world, BlockState state, BlockPos pos, int flags) {
        BlockPos blockPos = pos.up();
        world.setBlockState(pos, TallPlantBlock.withWaterloggedState(world, pos, state.with(PART, TripleTallBlock.BOTTOM)), flags);
        world.setBlockState(blockPos, TallPlantBlock.withWaterloggedState(world, blockPos, state.with(PART, TripleTallBlock.MIDDLE)), flags);
        world.setBlockState(blockPos.up(), TallPlantBlock.withWaterloggedState(world, blockPos.up(), state.with(PART, TripleTallBlock.TOP)), flags);
    }

    public static BlockState withWaterloggedState(WorldView world, BlockPos pos, BlockState state) {
        if (state.contains(Properties.WATERLOGGED)) {
            return state.with(Properties.WATERLOGGED, world.isWater(pos));
        }
        return state;
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            if (player.isCreative()) {
                WayTooTallPlantBlock.onBreakInCreative(world, pos, state, player);
            } else {
                WayTooTallPlantBlock.dropStacks(state, world, pos, null, player, player.getMainHandStack());
            }
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, Blocks.AIR.getDefaultState(), blockEntity, tool);
    }

    protected static void onBreakInCreative(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockPos blockPos;
        BlockState blockState = world.getBlockState(pos.down());
        BlockState blockState1 = world.getBlockState(pos.down(2));
        TripleTallBlock partVertical = state.get(PART);
        if (partVertical == TripleTallBlock.TOP && (blockState.isOf(state.getBlock()) && blockState.get(PART) == TripleTallBlock.MIDDLE) && (blockState1.isOf(state.getBlock()) && blockState1.get(PART) == TripleTallBlock.BOTTOM)) {
            BlockState blockState2 = blockState.getFluidState().isOf(Fluids.WATER) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
            world.setBlockState(pos, blockState2, Block.NOTIFY_ALL | Block.SKIP_DROPS);
            world.syncWorldEvent(player, WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(blockState));
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PART);
    }

    @Override
    protected long getRenderingSeed(BlockState state, BlockPos pos) {
        return MathHelper.hashCode(pos.getX(), pos.down(state.get(PART) == TripleTallBlock.BOTTOM ? 0 : 1).getY(), pos.getZ());
    }
}
