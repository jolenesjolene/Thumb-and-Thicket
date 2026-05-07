package net.jolene.thumbandthicket.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class DewDropCropBlock extends Block implements Fertilizable {
    public static final MapCodec<DewDropCropBlock> CODEC = DewDropCropBlock.createCodec(DewDropCropBlock::new);

    protected DewDropCropBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(Properties.AGE_5, 0));
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return world.getLightLevel(pos) < 6;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return world.getBlockState(pos.up()).isReplaceable() && world.getBlockState(pos.down()).isReplaceable();
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (state.get(Properties.AGE_5) < 5) {
            world.setBlockState(pos, state.cycle(Properties.AGE_5), 3);
        } else if (world.getBlockState(pos.up()).isReplaceable() && world.getBlockState(pos.down()).isReplaceable()){
            world.setBlockState(pos, ModBlocks.DEW_DROP.getDefaultState(), 3);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.AGE_5);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextBetween(1,10) == 1) {
            grow(world, random, pos, state);
        }
        super.randomTick(state, world, pos, random);
    }
}
