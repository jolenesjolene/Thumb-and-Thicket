package net.jolene.thumbandthicket.mixin;

import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.util.Rooty;
import net.jolene.thumbandthicket.util.Slice;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jolene.thumbandthicket.util.ModProperties.*;
import static net.minecraft.block.PillarBlock.AXIS;

@Mixin(value = PillarBlock.class, priority = 990)
public class PillarBlockMixin extends Block {

    public PillarBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void appendLogProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci){
        AbstractBlock.Settings settings = (PillarBlock.class.cast(this)).getSettings();
        SettingsAccessor accessor = (SettingsAccessor) settings;
        if (accessor.getInstrument() == NoteBlockInstrument.BASS && accessor.getSoundGroup() == BlockSoundGroup.WOOD) builder.add(SLICE).add(ROOTY);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void appendLogPropertiesValue(AbstractBlock.Settings settings, CallbackInfo ci) {
        Block pillarBlock = PillarBlock.class.cast(this);
        BlockState defaultBlockState = pillarBlock.getDefaultState();
        if (defaultBlockState.contains(ROOTY) && defaultBlockState.contains(SLICE)) {
            ((BlockAccessor)pillarBlock).invokeSetDefaultState(defaultBlockState.with(ROOTY, Rooty.NONE).with(SLICE, Slice.ZERO).with(ROOTY, Rooty.NONE));
        }
    }

    @Inject(method = "getPlacementState", at = @At("RETURN"), cancellable = true)
    private void modifyPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        BlockState state = cir.getReturnValue();
        if (state.contains(ROOTY) && state.contains(ROOTY) && state.contains(SLICE)) {
            World world = ctx.getWorld();
            BlockPos pos = ctx.getBlockPos();

            state = determineSide(state, world, pos);
            if (state.get(ROOTY) != Rooty.NONE) {
                state = calculateSlice(state, world, pos);
            }
            state = inheritSlice(state, world, pos);

            cir.setReturnValue(state);
        }
    }

    @Unique
    private BlockState determineSide(BlockState state, World world, BlockPos pos) {
        Direction.Axis axis = state.get(AXIS);

        if (!state.contains(ROOTY)) return state;

        if (axis.isVertical()) {
            Block below = world.getBlockState(pos.down()).getBlock();
            Block above = world.getBlockState(pos.up()).getBlock();
            if (below == ModBlocks.ROOT_BLOCK) return state.with(ROOTY, Rooty.BOTTOM);
            if (above == ModBlocks.ROOT_BLOCK) return state.with(ROOTY, Rooty.TOP);
        }

        return state;
    }

    @Unique
    private BlockState calculateSlice(BlockState state, World world, BlockPos pos) {
        Direction.Axis axis = state.get(AXIS);
        Block block = state.getBlock();

        Direction[] dirs;
        if (axis == Direction.Axis.Y) {
            dirs = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

            boolean[] connected = new boolean[4];
            for (int i = 0; i < dirs.length; i++) {
                BlockState neighbor = world.getBlockState(pos.offset(dirs[i]));
                connected[i] = neighbor.getBlock() == block &&
                        neighbor.contains(ROOTY) &&
                        neighbor.get(ROOTY) != Rooty.NONE;
            }

            if (connected[0] && connected[1]) return state.with(SLICE, Slice.TWO);
            if (connected[1] && connected[2]) return state.with(SLICE, Slice.THREE);
            if (connected[2] && connected[3]) return state.with(SLICE, Slice.FOUR);
            if (connected[3] && connected[0]) return state.with(SLICE, Slice.ONE);
        }

        return state;
    }

    @Unique
    private BlockState inheritSlice(BlockState state, World world, BlockPos pos) {

        Direction.Axis axis = state.get(AXIS);
        Block block = state.getBlock();

        Direction dir1, dir2;
        if (axis == Direction.Axis.Y) {
            dir1 = Direction.UP; dir2 = Direction.DOWN;
            BlockPos neighborPos1 = pos.offset(dir1);
            BlockPos neighborPos2 = pos.offset(dir2);

            for (BlockPos neighborPos : new BlockPos[]{neighborPos1, neighborPos2}) {
                BlockState neighbor = world.getBlockState(neighborPos);
                if (neighbor.getBlock() == block && neighbor.contains(SLICE) && neighbor.get(AXIS) == axis) {
                    return state.with(SLICE, neighbor.get(SLICE));
                }
            }
        }
        return state;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient) {
            BlockState newState = state;
            Direction.Axis axis = newState.get(AXIS);

            if (newState.contains(SLICE) && axis == Direction.Axis.Y) {
                newState = calculateSlice(newState, world, pos);

                Direction[] neighbors;
                    neighbors = new Direction[]{Direction.UP, Direction.DOWN};
                    for (Direction dir : neighbors) {
                        BlockPos neighborPos = pos.offset(dir);
                        BlockState neighborState = world.getBlockState(neighborPos);

                        if (neighborState.getBlock() == this && neighborState.contains(ROOTY) && neighborState.get(ROOTY) != Rooty.NONE && neighborState.get(AXIS) == axis) {
                            BlockState updatedNeighbor = calculateSlice(neighborState, world, neighborPos);
                            if (!neighborState.equals(updatedNeighbor)) {
                                world.setBlockState(neighborPos, updatedNeighbor, Block.NOTIFY_ALL);
                            }
                        }
                    }

                if (!state.equals(newState)) {
                    world.setBlockState(pos, newState, Block.NOTIFY_ALL);
                }
            }
        }

        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
    }
}