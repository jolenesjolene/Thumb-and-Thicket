package net.jolene.thumbandthicket.mixin.vegetation;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(SporeBlossomBlock.class)
public abstract class SporeBlossomBlockMixin extends Block implements Fertilizable {

    @Shadow
    protected abstract boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos);

    @Unique
    private static final int MAX_AGE = 2;
    @Unique private static final IntProperty AGE = Properties.AGE_2;

    @Unique
    protected IntProperty getAgeProperty() {
        return AGE;
    }

    @Unique
    public int getAge(BlockState state) {
        return state.get(this.getAgeProperty());
    }

    public SporeBlossomBlockMixin(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(Properties.AGE_2, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.AGE_2);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.random.nextInt(5) == 0) {
            grow(world, random, pos, state);
        }
        super.randomTick(state, world, pos, random);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return getAge(state) < MAX_AGE;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return canPlaceAt(state, world, pos);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (getAge(state) < MAX_AGE) world.setBlockState(pos, state.cycle(AGE), Block.NOTIFY_LISTENERS);
    }

    @WrapMethod(method = "randomDisplayTick")
    private void thumbandthicket$lessParticlesPerAge(BlockState state, World world, BlockPos pos, Random random, Operation<Void> original) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        double d = (double) i + random.nextDouble();
        double e = (double) j + 0.7;
        double f = (double) k + random.nextDouble();
        if (getAge(state) == 0) return;
        if (getAge(state) == 1){
            world.addParticle(ParticleTypes.FALLING_SPORE_BLOSSOM, d, e, f, 0.0, 0.0, 0.0);
            return;
        }
        original.call(state, world, pos, random);
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return getAge(state) < MAX_AGE;
    }
}
