package net.jolene.thumbandthicket.block;

import com.mojang.serialization.MapCodec;
import net.jolene.thumbandthicket.block.entity.FlowerCropBlockEntity;
import net.jolene.thumbandthicket.component.ModDataComponentTypes;
import net.jolene.thumbandthicket.item.ModItems;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class CaveCropBlock extends CropBlock implements Fertilizable {

    public static final MapCodec<CaveCropBlock> CODEC = CaveCropBlock.createCodec(CaveCropBlock::new);

    public CaveCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof FlowerCropBlockEntity flowerCropBlockEntity && !world.isClient) {
            flowerCropBlockEntity.thumbandthicket$setFlower(itemStack.getComponents().get(ModDataComponentTypes.FLOWER_TYPE));
        }
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return !this.isMature(state);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        this.applyGrowth(world, pos, state);
    }

    protected boolean canPlantOnTop(BlockState floor) {
        return floor.isOf(Blocks.GRAVEL);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        float f;
        int i;
        if (world.getBaseLightLevel(pos, 0) <= 6 && (i = this.getAge(state)) < 2 && random.nextInt((int)(25.0f / (f = getAvailableMoisture(this, world, pos))) + 1) == 0) {
            world.setBlockState(pos, this.withAge(i + 1), Block.NOTIFY_LISTENERS);
        } else if (world.getBaseLightLevel(pos, 0) <= 6 && (i = this.getAge(state)) == 2 && random.nextInt(12) == 0) {
            if (state.getBlock() instanceof CaveCropBlock caveCropBlock) world.setBlockState(pos, this.withAge(i+1), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return canPlantOnTop(world.getBlockState(pos.down()));
    }

    public void applyGrowth(World world, BlockPos pos, BlockState state) {
        int j;
        int i = this.getAge(state) + 1;
        if (i > (j = this.getMaxAge())) {
            i = j;
        }
        world.setBlockState(pos, this.withAge(i), Block.NOTIFY_LISTENERS);
    }

    protected IntProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return 3;
    }

    public int getAge(BlockState state) {
        return state.get(this.getAgeProperty());
    }

    public BlockState withAge(int age) {
        return this.getDefaultState().with(this.getAgeProperty(), age);
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return ModItems.CAVE_PARSNIP.getDefaultStack();
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        if (type == NavigationType.AIR && !this.collidable) {
            return true;
        }
        return super.canPathfindThrough(state, type);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
}
