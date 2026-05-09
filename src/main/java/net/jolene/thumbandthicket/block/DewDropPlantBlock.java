package net.jolene.thumbandthicket.block;

import net.jolene.thumbandthicket.item.ModItems;
import net.jolene.thumbandthicket.util.ModProperties;
import net.jolene.thumbandthicket.util.TripleTallBlock;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class DewDropPlantBlock extends WayTooTallPlantBlock {

    public static final int MIN_LEVEL = 0;
    public static final int MAX_LEVEL = 3;
    public static int CURRENT_LEVEL = 0;
    public static final IntProperty LEVEL = ModProperties.LEVEL_3;
    private static final VoxelShape RAYCAST_SHAPE = AbstractCauldronBlock.createCuboidShape(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);
    protected static final VoxelShape OUTLINE_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.union(AbstractCauldronBlock.createCuboidShape(0.0, 0.0, 4.0, 16.0, 3.0, 12.0), AbstractCauldronBlock.createCuboidShape(4.0, 0.0, 0.0, 12.0, 3.0, 16.0), AbstractCauldronBlock.createCuboidShape(2.0, 0.0, 2.0, 14.0, 3.0, 14.0), RAYCAST_SHAPE), BooleanBiFunction.ONLY_FIRST);


    protected DewDropPlantBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, MIN_LEVEL).with(Properties.FACING, Direction.NORTH));
    }

    private boolean isFull(BlockState state) {
        return state.get(LEVEL) == 3;
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return Objects.requireNonNull(super.getPlacementState(ctx)).with(Properties.FACING, ctx.getHorizontalPlayerFacing());
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(PART) == TripleTallBlock.MIDDLE && stack.isOf(Items.GLASS_BOTTLE)) {
            if (state.get(LEVEL) > MIN_LEVEL) {
                decrementFluidLevel(state, world, pos);
                stack.decrementUnlessCreative(1,player);
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(ModItems.DEW_BOTTLE)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
                return ItemActionResult.SUCCESS;
            }
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(LEVEL).add(Properties.FACING);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(PART) == TripleTallBlock.MIDDLE) {
            return OUTLINE_SHAPE;
        }
        return super.getCollisionShape(state, world, pos, context);
    }

    public static void decrementFluidLevel(BlockState state, World world, BlockPos pos) {
        int i = state.get(LEVEL) - 1;
        Direction direction = state.get(Properties.FACING);
        BlockState blockState = i == 0 ? ModBlocks.DEW_DROP.getDefaultState().with(PART, TripleTallBlock.MIDDLE).with(Properties.FACING, direction) : state.with(LEVEL, i);
        world.setBlockState(pos, blockState);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (state.get(PART) == TripleTallBlock.MIDDLE && isEntityTouchingFluid(state, pos, entity)) {

        }
        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (state.get(PART) == TripleTallBlock.TOP && world.getBlockState(pos.down()).isOf(this) && world.getBlockState(pos.down(2)).isOf(this)) {
            if (CURRENT_LEVEL > 0) {
                world.setBlockState(pos.down(), ModBlocks.DEW_CAULDRON.getDefaultState().with(Properties.LEVEL_3, CURRENT_LEVEL));
                return;
            }
            world.setBlockState(pos.down(), Blocks.CAULDRON.getDefaultState());
            return;
        }
        if (state.get(PART) == TripleTallBlock.MIDDLE) {
            if (CURRENT_LEVEL > 0) {
                world.setBlockState(pos, ModBlocks.DEW_CAULDRON.getDefaultState().with(Properties.LEVEL_3, CURRENT_LEVEL));
                return;
            }
            world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
            return;
        }
        if (state.get(PART) == TripleTallBlock.BOTTOM && world.getBlockState(pos.up()).isOf(this) && world.getBlockState(pos.up(2)).isOf(this)) {
            if (CURRENT_LEVEL > 0) {
                world.setBlockState(pos.up(), ModBlocks.DEW_CAULDRON.getDefaultState().with(Properties.LEVEL_3, CURRENT_LEVEL));
                return;
            }
            world.setBlockState(pos.up(), Blocks.CAULDRON.getDefaultState());
            return;
        }
        super.afterBreak(world, player, pos, state, blockEntity, tool);
    }

    protected double getFluidHeight(BlockState state) {
        return (6.0 + (double) state.get(LEVEL) * 3.0) / 16.0;
    }

    protected boolean isEntityTouchingFluid(BlockState state, BlockPos pos, Entity entity) {
        return entity.getY() < (double)pos.getY() + this.getFluidHeight(state) && entity.getBoundingBox().maxY > (double)pos.getY() + 0.25;
    }

    @Override
    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        if (state.get(PART) == TripleTallBlock.MIDDLE) return state.get(LEVEL);
        return 0;
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (state.get(PART) == TripleTallBlock.TOP && world.getBlockState(pos.down()).isOf(this)) CURRENT_LEVEL = world.getBlockState(pos.down()).get(LEVEL);
        if (state.get(PART) == TripleTallBlock.MIDDLE) CURRENT_LEVEL = state.get(LEVEL);
        if (state.get(PART) == TripleTallBlock.BOTTOM && world.getBlockState(pos.up()).isOf(this)) CURRENT_LEVEL = world.getBlockState(pos.up()).get(LEVEL);
        return super.onBreak(world, pos, state, player);
    }
}
