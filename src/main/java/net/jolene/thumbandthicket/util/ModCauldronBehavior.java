package net.jolene.thumbandthicket.util;

import net.jolene.thumbandthicket.item.ModItems;
import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ItemActionResult;
import net.minecraft.world.event.GameEvent;

import java.util.Map;

public class ModCauldronBehavior {
    public static final CauldronBehavior.CauldronBehaviorMap DEW_CAULDRON_BEHAVIOR = CauldronBehavior.createMap("water");

    public static void registerCauldronBehavior() {
        Map<Item, CauldronBehavior> map2 = DEW_CAULDRON_BEHAVIOR.map();
        map2.put(Items.GLASS_BOTTLE, (state, world, pos, player, hand, stack) -> {
            if (!world.isClient) {
                Item item = stack.getItem();
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, new ItemStack(ModItems.DEW_BOTTLE)));
                player.incrementStat(Stats.USE_CAULDRON);
                player.incrementStat(Stats.USED.getOrCreateStat(item));
                LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
                world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.emitGameEvent(null, GameEvent.FLUID_PICKUP, pos);
            }
            return ItemActionResult.success(world.isClient);
        });
    }
}
