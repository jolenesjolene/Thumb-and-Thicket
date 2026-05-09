package net.jolene.thumbandthicket.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class DewDropCropBlock extends Block implements Fertilizable {
    public static final MapCodec<DewDropCropBlock> CODEC = DewDropCropBlock.createCodec(DewDropCropBlock::new);
    private static final VoxelShape RAYCAST_SHAPE = AbstractCauldronBlock.createCuboidShape(2.0, 4.0, 2.0, 14.0, 16.0, 14.0);
    protected static final VoxelShape OUTLINE_SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.union(AbstractCauldronBlock.createCuboidShape(0.0, 0.0, 4.0, 16.0, 3.0, 12.0), AbstractCauldronBlock.createCuboidShape(4.0, 0.0, 0.0, 12.0, 3.0, 16.0), AbstractCauldronBlock.createCuboidShape(2.0, 0.0, 2.0, 14.0, 3.0, 14.0), RAYCAST_SHAPE), BooleanBiFunction.ONLY_FIRST);

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPE;
    }

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
        return !world.isSkyVisible(pos);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return world.getBlockState(pos.up()).isReplaceable() && world.getBlockState(pos.up(2)).isReplaceable();
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (state.get(Properties.AGE_5) < 5) {
            world.setBlockState(pos, state.cycle(Properties.AGE_5), 3);
        } else if (world.getBlockState(pos.up()).isReplaceable() && world.getBlockState(pos.up(2)).isReplaceable()){
            WayTooTallPlantBlock.placeAt(world, ModBlocks.DEW_DROP.getDefaultState(), pos, 3);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.AGE_5);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextBetween(1,10) == 1 && canGrow(world, random, pos, state)) {
            grow(world, random, pos, state);
        }
        super.randomTick(state, world, pos, random);
    }
}
