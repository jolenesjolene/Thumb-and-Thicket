package net.jolene.thumbandthicket.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.block.RootBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RootedDirtBlock;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.BiConsumer;

@Mixin(TrunkPlacer.class)
public class TrunkPlacerMixin {
    @WrapMethod(method = "setToDirt")
    private static void setToRoot(TestableWorld world, BiConsumer<BlockPos, BlockState> replacer, Random random, BlockPos pos, TreeFeatureConfig config, Operation<Void> original) {
        Block rootBlock = ModBlocks.ROOT_BLOCK;
        BlockState state = rootBlock.getDefaultState();
        WorldAccess worldAccess = (WorldAccess) world;
        BlockState neighborState = worldAccess.getBlockState(pos.up());
//        state.getStateForNeighborUpdate(Direction.DOWN, state, worldAccess, pos.up(), pos);
//        state.neighborUpdate(, pos.up(), rootBlock, pos, true);
        state.updateNeighbors(worldAccess, pos, Block.NOTIFY_ALL);
        replacer.accept(pos, state);
    }
}
