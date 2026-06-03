package net.jolene.thumbandthicket.block;

import com.mojang.serialization.MapCodec;
import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class PoisonIvyBlock extends MultifaceGrowthBlock implements Fertilizable {

    public static final MapCodec<PoisonIvyBlock> CODEC = createCodec(PoisonIvyBlock::new);
    private final LichenGrower grower = new LichenGrower(this);

    public PoisonIvyBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(ModProperties.AMOUNT, 1));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ModProperties.AMOUNT);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return Direction.stream().anyMatch(direction -> this.grower.canGrow(state, world, pos, direction.getOpposite()));
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (state.get(ModProperties.AMOUNT) > 4) world.setBlockState(pos, state.cycle(ModProperties.AMOUNT), 3);
        this.grower.grow(state, world, pos, random);
    }

    @Override
    protected MapCodec<? extends MultifaceGrowthBlock> getCodec() {
        return CODEC;
    }

    @Override
    public LichenGrower getGrower() {
        return this.grower;
    }

    @Override
    protected boolean canReplace(BlockState state, ItemPlacementContext context) {
        return !context.getStack().isOf(Items.GLOW_LICHEN) || super.canReplace(state, context);
    }
}
