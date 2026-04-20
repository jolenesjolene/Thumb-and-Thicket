package net.jolene.thumbandthicket.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.sound.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShearsItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import static net.jolene.thumbandthicket.block.ModBlockTags.SNIPPABLE;
import static net.jolene.thumbandthicket.util.ModProperties.SNIPPED;

@Mixin(ShearsItem.class)
public class ShearsItemMixin {

    @WrapMethod(method = "useOnBlock")
    private ActionResult thumbandthicket$snipVines(ItemUsageContext context, Operation<ActionResult> original) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        Hand hand = context.getHand();
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();
        if (state.contains(SNIPPED) && (state.isIn(SNIPPABLE) || state.getBlock() instanceof SaplingBlock)) {
            if (!state.get(SNIPPED)) {
                world.setBlockState(pos, state.with(SNIPPED, true));
                EquipmentSlot slot = null;
                switch (hand) {
                    case MAIN_HAND -> slot = EquipmentSlot.MAINHAND;
                    case OFF_HAND -> slot = EquipmentSlot.OFFHAND;
                }
                float f = MathHelper.nextBetween(world.random, 0.8f, 1.2f);
                world.playSound(
                        null,
                        pos,
                        SoundEvents.ENTITY_BOGGED_SHEAR,
                        SoundCategory.BLOCKS,
                        1.0F,
                        f
                );
                if (!player.isCreative()) stack.damage(1, player, slot);
                return ActionResult.SUCCESS;
            }
        }
        return original.call(context);
    }
}
