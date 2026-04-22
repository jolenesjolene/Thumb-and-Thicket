package net.jolene.thumbandthicket.item;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.jolene.thumbandthicket.ThumbAndThicket;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

public class ModItemTooltips {
    public static void register() {
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            if (itemStack.isOf(Items.SHEARS)) {
                list.add(Text.translatable("tooltip.thumbandthicket.shears1"));
                list.add(Text.translatable("tooltip.thumbandthicket.shears2"));
                list.add(Text.translatable("tooltip.thumbandthicket.shears3"));
            }

        });
    }

    public static void registerModItemTooltips() {
        ThumbAndThicket.LOGGER.info("Registering Tooltips for " + ThumbAndThicket.MOD_ID);
        register();
    }
}

