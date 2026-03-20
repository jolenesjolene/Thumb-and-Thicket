package net.jolene.thumbandthicket.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.util.ModProperties;
import net.jolene.thumbandthicket.util.Rooty;
import net.jolene.thumbandthicket.util.Slice;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.BiConsumer;

import static net.jolene.thumbandthicket.util.ModProperties.*;
import static net.jolene.thumbandthicket.ThumbAndThicket.*;

@Debug(export = true)
@Mixin(TrunkPlacer.class)
public class TrunkPlacerMixin {
    @WrapMethod(method = "setToDirt")
    private static void setToRoot(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos pos, TreeFeatureConfig config, Operation<Void> original) {
        Block rootBlock = ModBlocks.ROOT_BLOCK;
        BlockState state = rootBlock.getDefaultState();

        replacer.accept(pos, state);

        if (world instanceof WorldAccess worldAccess) {
            worldAccess.setBlockState(pos, state, Block.NOTIFY_ALL);
        }
    }

    @WrapOperation(method = "getAndSetState(Lnet/minecraft/world/TestableWorld;Ljava/util/function/BiConsumer;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/TreeFeatureConfig;Ljava/util/function/Function;)Z", at = @At(value = "INVOKE", target = "Ljava/util/function/BiConsumer;accept(Ljava/lang/Object;Ljava/lang/Object;)V"))
    private void modifyPlacedState(BiConsumer instance, Object posObj, Object stateObj, Operation<Void> original, @Local(name = "world") TestableWorld world) {
        BlockPos pos = (BlockPos) posObj;
        BlockState state = (BlockState) stateObj;

        BlockState newState = state;


        if (!((TrunkPlacer) (Object) this instanceof DarkOakTrunkPlacer)) {
            if (state.contains(ROOTY) && state.contains(SLICE)) {
                WorldAccess worldAccess = (WorldAccess) world;

                newState = thumbandthicket$determineRootSide(newState, worldAccess, pos);

                if (newState.get(ROOTY) != Rooty.NONE) {
                    newState = thumbandthicket$calculateSlice(newState, worldAccess, pos);
                }

                newState = inheritSlice(newState, worldAccess, pos);
            }
        }

        original.call(instance, pos, newState);
    }

//    @WrapOperation(method = "getAndSetState(Lnet/minecraft/world/TestableWorld;Ljava/util/function/BiConsumer;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/TreeFeatureConfig;Ljava/util/function/Function;)Z", at = @At(value = "INVOKE", target = "Ljava/util/function/BiConsumer;accept(Ljava/lang/Object;Ljava/lang/Object;)V"))
//    private void modifyDarkOakLog(BiConsumer instance, Object posObj, Object stateObj, Operation<Void> original, @Local(argsOnly = true) TestableWorld world, @Local(argsOnly = true) TreeFeatureConfig config, @Local(argsOnly = true) Random random, @Local(argsOnly = true) BlockPos pos) {
//        BlockState newState = config.trunkProvider.get(random, pos);
//
//        if ((TrunkPlacer) (Object) this instanceof DarkOakTrunkPlacer) {
//            BlockState blockBelow = ((WorldAccess) world).getBlockState(pos.down());
//            if (blockBelow.isOf(ModBlocks.ROOT_BLOCK)) {
//                newState = newState.with(ModProperties.ROOTY, Rooty.BOTTOM);
//            } else if (blockBelow.isIn(BlockTags.DIRT)) {
//                ((WorldAccess) world).setBlockState(pos.down(), ModBlocks.ROOT_BLOCK.getDefaultState(), Block.NOTIFY_NEIGHBORS);
//                newState = newState.with(ModProperties.ROOTY, Rooty.BOTTOM);
//            } else {
//                newState = newState.with(ModProperties.ROOTY, Rooty.NONE);
//            }
//
//            int dx = pos.getX() % 2;
//            int dz = pos.getZ() % 2;
//            if (dx == 1 && dz == 1) newState = newState.with(ModProperties.SLICE, Slice.ONE);
//            else if (dx == 0 && dz == 1) newState = newState.with(ModProperties.SLICE, Slice.TWO);
//            else if (dx == 0 && dz == 0) newState = newState.with(ModProperties.SLICE, Slice.THREE);
//            else if (dx == 1 && dz == 0) newState = newState.with(ModProperties.SLICE, Slice.FOUR);
//
//            if (!blockBelow.isOf(newState.getBlock()) || (blockBelow.contains(SLICE) && blockBelow.get(SLICE) == Slice.ZERO)) {
//                newState = newState.with(SLICE, Slice.ZERO);
//            }
//        }
//
//        original.call(instance, pos, newState);
//    }
}
