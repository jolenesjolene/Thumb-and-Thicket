package net.jolene.thumbandthicket.mixin.vegetation;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import net.jolene.thumbandthicket.mixin.BlockAccessor;
import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HangingRootsBlock.class)
public class HangingRootsBlockMixin extends Block implements Fertilizable {

    public HangingRootsBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "appendProperties", at = @At("RETURN"))
    private void appendProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(ModProperties.TOP);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void thumbandthicket$appendTopProperty(AbstractBlock.Settings settings, CallbackInfo ci) {
        Block hangingRootsBlock = HangingRootsBlock.class.cast(this);
        BlockState defaultBlockState = hangingRootsBlock.getDefaultState();
        ((BlockAccessor) hangingRootsBlock).invokeSetDefaultState(defaultBlockState.with(ModProperties.TOP, true));
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return state.get(ModProperties.TOP);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (state.get(ModProperties.TOP)) world.setBlockState(pos.down(), state.with(ModProperties.TOP, true));
    }

    @ModifyReturnValue(method = "getStateForNeighborUpdate", at = @At("RETURN"))
    private BlockState thumbandthicket$checkIfTop(BlockState original, @Local(ordinal = 0, argsOnly = true) BlockPos pos, @Local(argsOnly = true) WorldAccess world) {
        BlockState state1 = world.getBlockState(pos.down());
        if (original.isOf(Blocks.HANGING_ROOTS)) return state1.isOf(Blocks.HANGING_ROOTS) ? original.with(ModProperties.TOP, false) : original.with(ModProperties.TOP, true);
        return original;
    }

    @ModifyReturnValue(method = "canPlaceAt", at = @At("RETURN"))
    private boolean thumbandthicket$placeOnRoots(boolean original, @Local(ordinal = 1) BlockState blockState) {
        return blockState.isOf(Blocks.HANGING_ROOTS) || original;
    }

    @WrapMethod(method = "getOutlineShape")
    private VoxelShape thumbandthicket$modifyOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, Operation<VoxelShape> original) {
        VoxelShape shape = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
        return state.get(ModProperties.TOP) ? original.call(state, world, pos, context) : shape;
    }
}
