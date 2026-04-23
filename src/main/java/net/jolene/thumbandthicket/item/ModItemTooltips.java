package net.jolene.thumbandthicket.item;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.jolene.thumbandthicket.ThumbAndThicket;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

public class ModItemTooltips {
    public static void register() {
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            if (itemStack.isOf(Items.SHEARS)) {
                if (Screen.hasShiftDown()) {
                    list.add(1, Text.translatable("tooltip.thumbandthicket.shears"));
                    list.add(2, Text.translatable("tooltip.thumbandthicket.shears1"));
                    list.add(3, Text.translatable("tooltip.thumbandthicket.shears2"));
                    } else {
                    list.add(1, Text.translatable("tooltip.thumbandthicket.shears"));
                    list.add(2, Text.translatable("tooltip.thumbandthicket.shift"));
                }
            }

        });
    }

    public static void registerModItemTooltips() {
        ThumbAndThicket.LOGGER.info("Registering Tooltips for " + ThumbAndThicket.MOD_ID);
        register();
    }
}

