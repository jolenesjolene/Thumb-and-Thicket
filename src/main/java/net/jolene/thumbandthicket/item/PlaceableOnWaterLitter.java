package net.jolene.thumbandthicket.item;

import com.blackgear.vanillabackport.common.level.blocks.LeafLitterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PlaceableOnWaterItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class PlaceableOnWaterLitter extends BlockItem {
    public PlaceableOnWaterLitter(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);

        if (state.isOf(this.getBlock()) && state.contains(LeafLitterBlock.AMOUNT) && state.get(LeafLitterBlock.AMOUNT) < 4) return super.useOnBlock(context);
        return ActionResult.PASS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        BlockHitResult blockHitResult = PlaceableOnWaterItem.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        BlockHitResult blockHitResult2 = blockHitResult.withBlockPos(blockHitResult.getBlockPos());
        ActionResult actionResult = super.useOnBlock(new ItemUsageContext(user, hand, blockHitResult2));
        return new TypedActionResult<>(actionResult, user.getStackInHand(hand));
    }

}
