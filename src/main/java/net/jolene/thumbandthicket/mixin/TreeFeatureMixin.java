package net.jolene.thumbandthicket.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.world.gen.ModConfiguredFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableWorld;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TreeFeature.class)
public abstract class TreeFeatureMixin {

    @WrapMethod(method = "setBlockStateWithoutUpdatingNeighbors")
    private static void gay(ModifiableWorld world, BlockPos pos, BlockState state, Operation<Void> original) {
        Block rootBlock = ModBlocks.ROOT_BLOCK;
        BlockState state1 = rootBlock.getDefaultState();
//        world.setBlockState(pos.down(), state1, Block.NOTIFY_ALL);
        world.setBlockState(pos, state, Block.NOTIFY_ALL);
    }

    @Inject(method = "generate(Lnet/minecraft/world/gen/feature/util/FeatureContext;)Z", at = @At(value = "TAIL"))
    private void thumbandthicket$generateRootedDirt(FeatureContext<TreeFeatureConfig> context, CallbackInfoReturnable<Boolean> cir, @Local StructureWorldAccess world, @Local BlockPos pos) {

        var configuredFeatures = world.getRegistryManager().get(RegistryKeys.CONFIGURED_FEATURE);
        ConfiguredFeature<?,?> configuredFeature = configuredFeatures.getOrThrow(ModConfiguredFeatures.ROOTED_GRASS_KEY);

        configuredFeature.generate(world, context.getGenerator(), context.getRandom(), pos.down(2));
    }
}
