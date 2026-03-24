package net.jolene.thumbandthicket.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import static net.jolene.thumbandthicket.util.ModProperties.AMOUNT;
import static net.minecraft.state.property.Properties.BLOCK_FACE;
import static net.minecraft.state.property.Properties.FACING;

@Mixin(MushroomPlantBlock.class)
public abstract class MushroomPlantBlockMixin extends Block {
    
    @Unique private static final VoxelShape FLOOR_2 = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    @Unique private static final VoxelShape FLOOR_3 = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    @Unique private static final VoxelShape FLOOR_4 = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    
    @Unique private static final VoxelShape WALL_1_NORTH = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    @Unique private static final VoxelShape WALL_2_NORTH = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    @Unique private static final VoxelShape WALL_3_NORTH = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    @Unique private static final VoxelShape WALL_4_NORTH = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    
    @Unique private static final VoxelShape WALL_1_EAST = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    @Unique private static final VoxelShape WALL_2_EAST = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    @Unique private static final VoxelShape WALL_3_EAST = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    @Unique private static final VoxelShape WALL_4_EAST = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    
    @Unique private static final VoxelShape WALL_1_SOUTH = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    @Unique private static final VoxelShape WALL_2_SOUTH = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    @Unique private static final VoxelShape WALL_3_SOUTH = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    @Unique private static final VoxelShape WALL_4_SOUTH = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    
    @Unique private static final VoxelShape WALL_1_WEST = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    @Unique private static final VoxelShape WALL_2_WEST = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    @Unique private static final VoxelShape WALL_3_WEST = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    @Unique private static final VoxelShape WALL_4_WEST = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    
    @Unique private static final VoxelShape CEILING_1 = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    @Unique private static final VoxelShape CEILING_2 = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    @Unique private static final VoxelShape CEILING_3 = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);
    @Unique private static final VoxelShape CEILING_4 = Block.createCuboidShape(6.0, 14.0, 5.0, 10.0, 16.0, 11.0);

    public MushroomPlantBlockMixin(Settings settings) {
        super(settings);
    }

    @WrapMethod(method = "getOutlineShape")
    private VoxelShape thumbandthicket$getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, Operation<VoxelShape> original) {
        Direction direction = state.get(FACING);
        int amount = state.get(AMOUNT);
        switch (state.get(BLOCK_FACE)) {
            case FLOOR: {
                switch (amount) {
                    case 1 -> original.call(state, world, pos, context);
                    case 2 -> {return FLOOR_2;}
                    case 3 -> {return FLOOR_3;}
                    case 4 -> {return FLOOR_4;}
                    default -> throw new IllegalStateException();
                }
            }
            case CEILING: {
                switch (amount) {
                    case 1 -> {return CEILING_1;}
                    case 2 -> {return CEILING_2;}
                    case 3 -> {return CEILING_3;}
                    case 4 -> {return CEILING_4;}
                    default -> throw new IllegalStateException();
                }
            }
            case WALL: {
                return switch (direction) {
                    case Direction.EAST -> {
                        switch (amount) {
                            case 1 -> {yield WALL_1_EAST;}
                            case 2 -> {yield WALL_2_EAST;}
                            case 3 -> {yield WALL_3_EAST;}
                            case 4 -> {yield WALL_4_EAST;}
                            default -> throw new IllegalStateException();
                        }
                    }
                    case Direction.WEST -> {
                        switch (amount) {
                            case 1 -> {yield WALL_1_WEST;}
                            case 2 -> {yield WALL_2_WEST;}
                            case 3 -> {yield WALL_3_WEST;}
                            case 4 -> {yield WALL_4_WEST;}
                            default -> throw new IllegalStateException();
                        }
                    }
                    case Direction.SOUTH -> {
                        switch (amount) {
                            case 1 -> {yield WALL_1_SOUTH;}
                            case 2 -> {yield WALL_2_SOUTH;}
                            case 3 -> {yield WALL_3_SOUTH;}
                            case 4 -> {yield WALL_4_SOUTH;}
                            default -> throw new IllegalStateException();
                        }
                    }
                    case Direction.NORTH, UP, DOWN -> {
                        switch (amount) {
                            case 1 -> {yield WALL_1_NORTH;}
                            case 2 -> {yield WALL_2_NORTH;}
                            case 3 -> {yield WALL_3_NORTH;}
                            case 4 -> {yield WALL_4_NORTH;}
                            default -> throw new IllegalStateException();
                        }
                    }
                };
            }
        }
        return null;
    }

    @WrapMethod(method = "canPlaceAt")
    private boolean thumbandthicket$canPlaceAt(BlockState state, WorldView world, BlockPos pos, Operation<Boolean> original) {

        Direction direction = thumbandthicket$getDirection(state);
        BlockPos blockPos = pos.offset(direction);
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction.getOpposite()) && (world.getBaseLightLevel(pos, 0) < 13 || world.getBlockState(blockPos).isIn(BlockTags.MUSHROOM_GROW_BLOCK));
    }

    @Unique
    private static Direction thumbandthicket$getDirection(BlockState state) {
        return switch (state.get(BLOCK_FACE)) {
            case CEILING -> Direction.UP;
            case FLOOR -> Direction.DOWN;
            default -> state.get(FACING);
        };
    }

    @WrapMethod(method = "grow")
    private void thumbandthicket$growIfFloor(ServerWorld world, Random random, BlockPos pos, BlockState state, Operation<Void> original) {
        if (state.get(BLOCK_FACE) == BlockFace.FLOOR) original.call(world, random, pos, state);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (!context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && state.get(AMOUNT) < 4) {
            return true;
        }
        return super.canReplace(state, context);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Block block = this;
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            return blockState.with(AMOUNT, Math.min(4, blockState.get(AMOUNT) + 1));
        }
        for (Direction direction : ctx.getPlacementDirections()) {
            BlockState blockState1 = direction.getAxis() == Direction.Axis.Y ? block.getDefaultState().with(BLOCK_FACE, direction == Direction.UP ? BlockFace.FLOOR : BlockFace.CEILING).with(FACING, ctx.getHorizontalPlayerFacing()) : block.getDefaultState().with(BLOCK_FACE, BlockFace.WALL).with(FACING, direction.getOpposite());
            if (!blockState1.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) continue;
            return blockState1;
        }
        return null;
    }
}
