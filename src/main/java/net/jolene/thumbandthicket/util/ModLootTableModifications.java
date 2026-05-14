package net.jolene.thumbandthicket.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.jolene.thumbandthicket.item.ModItems;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.registry.RegistryKey;

public class ModLootTableModifications {
    private static final RegistryKey<LootTable> SNIFFER_DIGGING_GAMEPLAY = LootTables.SNIFFER_DIGGING_GAMEPLAY;

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((registryKey, tableBuilder, tableSource, wrapperLookup) -> {
            if (SNIFFER_DIGGING_GAMEPLAY.equals(registryKey)) {
                tableBuilder.modifyPools(itemEntry -> itemEntry.with((ItemEntry.builder(ModItems.DEW_DROP_SAPLING)).build()));
            }
        });
    }
}
