package net.jolene.thumbandthicket.block;

import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ShortSnowyPlantBlock extends ShortPlantBlock {
    public ShortSnowyPlantBlock(Settings settings) {
        super(settings);
        super.setDefaultState(this.getDefaultState().with(ModProperties.LAYERS, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(ModProperties.LAYERS);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (stack.isOf(Items.SNOW) && state.get(ModProperties.LAYERS) < 8) {
            world.setBlockState(pos, state.cycle(ModProperties.LAYERS), Block.NOTIFY_NEIGHBORS);
            stack.decrementUnlessCreative(1, player);
            return ItemActionResult.SUCCESS;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        if (floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.SNOW_BLOCK)) return true;
        return super.canPlantOnTop(floor, world, pos);
    }

    @Override
    protected boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (context.getStack().isOf(Items.SNOW)) return false;
        return super.canReplace(state, context);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();
        BlockState state = world.getBlockState(pos);

        if (state.isOf(Blocks.SNOW)) {
            int layers = state.get(Properties.LAYERS);
            return this.getDefaultState().with(ModProperties.LAYERS, layers);
        }
        return super.getPlacementState(ctx);
    }
}
