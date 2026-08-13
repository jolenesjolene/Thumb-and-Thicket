package net.jolene.thumbandthicket.mixin.vegetation;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import net.jolene.thumbandthicket.mixin.BlockAccessor;
import net.jolene.thumbandthicket.util.HangingPart;
import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.*;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HangingRootsBlock.class)
public class HangingRootsBlockMixin extends Block {

    public HangingRootsBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "appendProperties", at = @At("RETURN"))
    private void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(ModProperties.HANGING_PART);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void thumbandthicket$appendTopProperty(AbstractBlock.Settings settings, CallbackInfo ci) {
        Block hangingRootsBlock = HangingRootsBlock.class.cast(this);
        BlockState defaultBlockState = hangingRootsBlock.getDefaultState();
        ((BlockAccessor) hangingRootsBlock).invokeSetDefaultState(defaultBlockState.with(ModProperties.HANGING_PART, HangingPart.TOP));
    }

    @ModifyReturnValue(method = "getStateForNeighborUpdate", at = @At("RETURN"))
    private BlockState thumbandthicket$checkIfTop(BlockState original, @Local(ordinal = 0, argsOnly = true) BlockPos pos, @Local(argsOnly = true) WorldAccess world) {
        BlockState downState = world.getBlockState(pos.down());
        BlockState upState = world.getBlockState(pos.up());
        if (original.isOf(Blocks.HANGING_ROOTS)) {
            if (!upState.isOf(Blocks.HANGING_ROOTS) && !downState.isOf(Blocks.HANGING_ROOTS)) return original.with(ModProperties.HANGING_PART, HangingPart.TOP);
            if (!upState.isOf(Blocks.HANGING_ROOTS) && downState.isOf(Blocks.HANGING_ROOTS)) return original.with(ModProperties.HANGING_PART, HangingPart.BASE);
            if (upState.isOf(Blocks.HANGING_ROOTS) && downState.isOf(Blocks.HANGING_ROOTS)) return original.with(ModProperties.HANGING_PART, HangingPart.MIDDLE);
            if (upState.isOf(Blocks.HANGING_ROOTS) && !downState.isOf(Blocks.HANGING_ROOTS)) return original.with(ModProperties.HANGING_PART, HangingPart.TOP);
        }
        return original;
    }

    @ModifyReturnValue(method = "canPlaceAt", at = @At("RETURN"))
    private boolean thumbandthicket$placeOnRoots(boolean original, @Local(ordinal = 1) BlockState blockState) {
        return blockState.isOf(Blocks.HANGING_ROOTS) || original;
    }

    @WrapMethod(method = "getOutlineShape")
    private VoxelShape thumbandthicket$modifyOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, Operation<VoxelShape> original) {
        VoxelShape shape = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
        return state.get(ModProperties.HANGING_PART) == HangingPart.TOP ? original.call(state, world, pos, context) : shape;
    }
}
