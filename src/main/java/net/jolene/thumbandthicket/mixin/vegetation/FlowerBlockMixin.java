package net.jolene.thumbandthicket.mixin.vegetation;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.*;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Inject;

import java.util.function.BiFunction;

import static net.minecraft.state.property.Properties.FACING;


@Mixin(FlowerBlock.class)
public class FlowerBlockMixin extends Block implements Fertilizable {
    public FlowerBlockMixin(Settings settings) {
        super(settings);
        setDefaultState(this.stateManager.getDefaultState().with(ModProperties.FLOWERS,1));
    }

    

    @WrapMethod(method = "getOutlineShape")
    private VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, Operation<VoxelShape> original) {
        return switch (state.get(ModProperties.FLOWERS)) {
            case 1 -> Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 10.0, 11.0);
            case 2,3 -> Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 10.0, 14.0);
            default -> throw new IllegalStateException("Unexpected value: " + state.get(ModProperties.FLOWERS));
        };
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ModProperties.FLOWERS).add(FACING);
    }

    @Override
    protected boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (!context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && state.get(ModProperties.FLOWERS) < 3) return true;
        return super.canReplace(state, context);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        Block block = this;
        BlockState state = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (state.isOf(this)) {
            return state.with(ModProperties.FLOWERS, Math.min(3, state.get(ModProperties.FLOWERS) + 1));
        }
        for (Direction direction : ctx.getPlacementDirections()) {
            BlockState blockState1 = direction.getAxis() == Direction.Axis.Y ? block.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing()) : block.getDefaultState().with(FACING, direction.getOpposite());
            if (!blockState1.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) continue;
            return blockState1;
        }
        return super.getPlacementState(ctx);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return state.get(ModProperties.FLOWERS) < 3;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return canPlaceAt(state, world, pos);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int flowers = state.get(ModProperties.FLOWERS);
        if (flowers < 3) world.setBlockState(pos, state.cycle(ModProperties.FLOWERS), 3);
    }

    @Override
    protected float getMaxHorizontalModelOffset() {
        return 0.0f;
    }
}
