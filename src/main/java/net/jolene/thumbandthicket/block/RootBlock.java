package net.jolene.thumbandthicket.block;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.jolene.thumbandthicket.util.Rooty;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import static net.jolene.thumbandthicket.util.ModProperties.ROOTY;

public class RootBlock extends PillarBlock {

    public RootBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {

        BlockState blockAbove = world.getBlockState(pos.offset(Direction.Axis.Y, +1));
        if (blockAbove.isIn(BlockTags.LOGS) && blockAbove.contains(ROOTY)) {

            BlockState newState = blockAbove.with(ROOTY, Rooty.BOTTOM);

            if (!blockAbove.equals(newState) && blockAbove.get(AXIS) == state.get(AXIS)) {
                world.setBlockState(pos.up(), newState, Block.NOTIFY_ALL);
            }
            super.neighborUpdate(blockAbove, world, pos, sourceBlock, sourcePos, notify);
        }
    }
}
