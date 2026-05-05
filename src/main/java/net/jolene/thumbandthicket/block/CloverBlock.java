package net.jolene.thumbandthicket.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerbedBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class CloverBlock extends FlowerbedBlock {
    public CloverBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FLOWER_AMOUNT)) {
            case 1 -> {
                return Block.createCuboidShape(3.0, 0.0, 3.0, 12.0, 4.0, 12.0);
            }
            case 2, 3, 4 -> {
                return super.getOutlineShape(state.with(FLOWER_AMOUNT, 4), world, pos, context);
            }
        }
        return super.getOutlineShape(state, world, pos, context);
    }
}
