package net.jolene.thumbandthicket.mixin.vegetation;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import static net.minecraft.state.property.Properties.WATERLOGGED;

@Mixin(LilyPadBlock.class)
public class LilyPadBlockMixin extends Block implements Waterloggable {

     //TODO Replace Lilypad Feature

    @Shadow
    @Final
    public static MapCodec<LilyPadBlock> CODEC;
    @Unique
    private static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 14.5, 0.0, 16.0, 16, 16.0);

    protected LilyPadBlockMixin(Settings settings) {
        super(settings);
        this.stateManager.getDefaultState().with(WATERLOGGED, false);
    }

    @WrapMethod(method = "getOutlineShape")
    private VoxelShape thumbandthicket$biggerLilyPadShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, Operation<VoxelShape> original) {
        return SHAPE;
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
        super.appendProperties(builder);
    }

    @WrapMethod(method = "canPlantOnTop")
    private boolean thumbandthicket$canPlantOnTop(BlockState floor, BlockView world, BlockPos pos, Operation<Boolean> original) {
        FluidState fluidState = world.getFluidState(pos);
        FluidState fluidState2 = world.getFluidState(pos.up());
        return (fluidState.getFluid() == Fluids.WATER || floor.getBlock() instanceof IceBlock) && fluidState2.getFluid() == Fluids.EMPTY;
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        FluidState fluid = world.getFluidState(pos);
        FluidState fluid1 = world.getFluidState(pos.up());
        return fluid.isIn(FluidTags.WATER) && fluid.getLevel() == 8 && fluid1.isEmpty();
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        if (fluidState.isIn(FluidTags.WATER) && fluidState.getLevel() == 8) return this.getDefaultState().with(WATERLOGGED, true);
        return null;
    }
}
