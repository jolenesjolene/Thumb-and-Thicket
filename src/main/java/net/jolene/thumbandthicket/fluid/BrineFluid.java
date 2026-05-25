package net.jolene.thumbandthicket.fluid;

import com.google.common.collect.Maps;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.*;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

import java.util.Map;
import java.util.Optional;

public abstract class BrineFluid extends FlowableFluid {
    private final Map<FluidState, VoxelShape> shapeCache = Maps.newIdentityHashMap();

    @Override
    public VoxelShape getShape(FluidState state, BlockView world, BlockPos pos) {
        return state.getLevel() == 9 && (isFluidAboveEqual(state, world, pos) || isFluidAboveWater(state, world, pos))
                ? VoxelShapes.fullCube()
                : this.shapeCache.computeIfAbsent(state, state2 -> VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, state2.getHeight(world, pos), 1.0));
    }

    @Override
    public void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random) {
        if (!state.isStill() && !(Boolean)state.get(FALLING)) {
            if (random.nextInt(64) == 0) {
                world.playSound((double)pos.getX() + (double)0.5F, (double)pos.getY() + (double)0.5F, (double)pos.getZ() + (double)0.5F, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
            }
        } else if (random.nextInt(10) == 0) {
            world.addParticle(ParticleTypes.UNDERWATER, (double)pos.getX() + random.nextDouble(), (double)pos.getY() + random.nextDouble(), (double)pos.getZ() + random.nextDouble(), (double)0.0F, (double)0.0F, (double)0.0F);
        }
    }

    private static boolean isFluidAboveWater(FluidState state, BlockView world, BlockPos pos) {
        return world.getFluidState(pos.up()).getFluid().isIn(FluidTags.WATER);
    }

    private boolean isFluidAboveEqual(FluidState state, BlockView world, BlockPos pos) {
        return world.getFluidState(pos.up()).getFluid().matchesType(this);
    }


    @Override
    protected boolean isInfinite(World world) {
        return false;
    }

    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        final BlockEntity entity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, entity);
    }

    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }

    @Override
    protected int getMaxFlowDistance(WorldView world) {
        return 2;
    }

    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return 3;
    }

    @Override
    protected float getBlastResistance() {
        return 100.0f;
    }

    @Override
    public Fluid getFlowing() {
        return ModFluids.FLOWING_BRINE;
    }

    @Override
    public Fluid getStill() {
        return ModFluids.BRINE_SOURCE;
    }

    @Override
    public Item getBucketItem() {
        return ModItems.BRINE_BUCKET;
    }

    @Override
    public BlockState toBlockState(FluidState state) {
        return ModBlocks.BRINE_BLOCK.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
    }

    @Override
    public int getTickRate(WorldView world) {
        return 20;
    }

    @Override
    protected int getNextTickDelay(World world, BlockPos pos, FluidState oldState, FluidState newState) {
        return 20;
    }

    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == ModFluids.BRINE_SOURCE || fluid == ModFluids.FLOWING_BRINE;
    }

    @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(SoundEvents.ITEM_BUCKET_FILL);
    }

    @Override
    protected boolean hasRandomTicks() {
        return true;
    }

    public static class Still extends BrineFluid {
        public int getLevel(FluidState state) {
            return 8;
        }

        public boolean isStill(FluidState state) {
            return true;
        }
    }

    public static class Flowing extends BrineFluid {
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        public int getLevel(FluidState state) {
            return state.get(LEVEL);
        }

        public boolean isStill(FluidState state) {
            return false;
        }
    }
}
