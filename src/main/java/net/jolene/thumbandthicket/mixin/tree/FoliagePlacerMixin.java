package net.jolene.thumbandthicket.mixin.tree;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FoliagePlacer.class)
public class FoliagePlacerMixin {

    @WrapOperation(method = "placeFoliageBlock(Lnet/minecraft/world/TestableWorld;Lnet/minecraft/world/gen/foliage/FoliagePlacer$BlockPlacer;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/world/gen/feature/TreeFeatureConfig;Lnet/minecraft/util/math/BlockPos;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/foliage/FoliagePlacer$BlockPlacer;placeBlock(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)V"))
    private static void gay(FoliagePlacer.BlockPlacer instance, BlockPos pos, BlockState blockState, Operation<Void> original, @Local(argsOnly = true) TestableWorld world) {
        BlockState newState = blockState;
        WorldAccess worldAccess = (WorldAccess) world;
        if (blockState.contains(ModProperties.TOP)) {
            if (!worldAccess.getBlockState(pos.up()).isOf(Blocks.AIR))
                newState = blockState.with(ModProperties.TOP, false);
        }
        original.call(instance, pos, newState);
    }
}
