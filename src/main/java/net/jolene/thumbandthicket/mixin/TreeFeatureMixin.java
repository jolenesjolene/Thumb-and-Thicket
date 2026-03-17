package net.jolene.thumbandthicket.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableWorld;
import net.minecraft.world.gen.feature.TreeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TreeFeature.class)
public abstract class TreeFeatureMixin {

    @WrapMethod(method = "setBlockStateWithoutUpdatingNeighbors")
    private static void gay(ModifiableWorld world, BlockPos pos, BlockState state, Operation<Void> original) {
        Block rootBlock = ModBlocks.ROOT_BLOCK;
        BlockState state1 = rootBlock.getDefaultState();
//        world.setBlockState(pos.down(), state1, Block.NOTIFY_ALL);
        world.setBlockState(pos, state, Block.NOTIFY_ALL);
    }
}
