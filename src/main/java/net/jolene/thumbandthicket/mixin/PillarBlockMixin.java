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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jolene.thumbandthicket.util.ModProperties.*;
import static net.jolene.thumbandthicket.ThumbAndThicket.*;
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
        if (accessor.getInstrument() == NoteBlockInstrument.BASS && accessor.getSoundGroup() == BlockSoundGroup.CHERRY_WOOD) builder.add(SLICE).add(ROOTY);
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
        if (state.contains(ROOTY) && state.contains(SLICE)) {
            World world = ctx.getWorld();
            BlockPos pos = ctx.getBlockPos();

            state = thumbandthicket$determineRootSide(state, world, pos);
            if (state.get(ROOTY) != Rooty.NONE) {
                state = thumbandthicket$calculateSlice(state, world, pos);
            }
            state = thumbandthicket$inheritSlice(state, world, pos);

            cir.setReturnValue(state);
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient) {
            BlockState newState = state;
            Direction.Axis axis = newState.get(AXIS);
            Direction rootBlockDirection = thumbandthicket$determineRootBlockDirection(state, pos, world, ModBlocks.ROOT_BLOCK);

            if (newState.contains(SLICE)) {
                Direction logBlockDirection = thumbandthicket$determineRootBlockDirection(state, pos, world, state.getBlock());
                if (logBlockDirection != null){
                    Direction invertedlogBlockDirection = thumbandthicket$getInvertedDirection(logBlockDirection);
                    BlockState logBelow = world.getBlockState(pos.offset(logBlockDirection));
                    BlockState logBelow1 = world.getBlockState(pos.offset(invertedlogBlockDirection));
                    if (logBelow.contains(SLICE) && logBelow.get(SLICE) != Slice.ZERO) {
                        newState = newState.with(SLICE, logBelow.get(SLICE));
                    } else if (logBelow1.contains(SLICE) && logBelow1.get(SLICE) != Slice.ZERO){
                        newState = newState.with(SLICE, logBelow1.get(SLICE));
                    }
                }

                if (rootBlockDirection != null) {
                    BlockState blockBelow = world.getBlockState(pos.offset(rootBlockDirection));
                    if (blockBelow.get(AXIS) == axis) {
                        newState = thumbandthicket$determineRootSide(newState, world, pos);
                        newState = thumbandthicket$calculateSlice(newState, world, pos);
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