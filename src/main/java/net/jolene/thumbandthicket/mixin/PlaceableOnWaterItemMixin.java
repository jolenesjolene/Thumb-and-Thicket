package net.jolene.thumbandthicket.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlaceableOnWaterItem.class)
public class PlaceableOnWaterItemMixin extends BlockItem {

    public PlaceableOnWaterItemMixin(Block block, Settings settings) {
        super(block, settings);
    }

    @WrapMethod(method = "use")
    private TypedActionResult<ItemStack> thumbandthicket$placeAsWaterlogged(World world, PlayerEntity user, Hand hand, Operation<TypedActionResult<ItemStack>> original) {
        BlockHitResult blockHitResult = PlaceableOnWaterItem.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        BlockHitResult blockHitResult2 = blockHitResult.withBlockPos(blockHitResult.getBlockPos());

        ActionResult actionResult = super.useOnBlock(new ItemUsageContext(user, hand, blockHitResult2));
        return new TypedActionResult<>(actionResult, user.getStackInHand(hand));
    }
}
