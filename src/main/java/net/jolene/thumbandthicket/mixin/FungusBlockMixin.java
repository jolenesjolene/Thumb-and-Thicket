package net.jolene.thumbandthicket.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FungusBlock;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import static net.jolene.thumbandthicket.util.ModProperties.AMOUNT;
import static net.minecraft.state.property.Properties.BLOCK_FACE;
import static net.minecraft.state.property.Properties.FACING;

@Mixin(FungusBlock.class)
public class FungusBlockMixin extends Block {
    public FungusBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {

        Direction direction = thumbandthicket$getDirection(state);
        BlockPos blockPos = pos.offset(direction);
        return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction.getOpposite()) && (world.getBaseLightLevel(pos, 0) < 13 || world.getBlockState(blockPos).isIn(BlockTags.MUSHROOM_GROW_BLOCK));
    }

    @Unique
    private static Direction thumbandthicket$getDirection(BlockState state) {
        return switch (state.get(BLOCK_FACE)) {
            case CEILING -> Direction.UP;
            case FLOOR -> Direction.DOWN;
            default -> state.get(Properties.FACING);
        };
    }

    @WrapMethod(method = "grow")
    private void thumbandthicket$growIfFloor(ServerWorld world, Random random, BlockPos pos, BlockState state, Operation<Void> original) {
        if (state.get(BLOCK_FACE) == BlockFace.FLOOR) original.call(world, random, pos, state);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (!context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && state.get(AMOUNT) < 4) {
            return true;
        }
        return super.canReplace(state, context);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Block block = this;
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            return blockState.with(AMOUNT, Math.min(4, blockState.get(AMOUNT) + 1));
        }
        for (Direction direction : ctx.getPlacementDirections()) {
            BlockState blockState1 = direction.getAxis() == Direction.Axis.Y ? block.getDefaultState().with(BLOCK_FACE, direction == Direction.UP ? BlockFace.FLOOR : BlockFace.CEILING).with(Properties.FACING, ctx.getHorizontalPlayerFacing()) : block.getDefaultState().with(BLOCK_FACE, BlockFace.WALL).with(FACING, direction.getOpposite());
            if (!blockState1.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) continue;
            return blockState1;
        }
        return null;
    }
}
