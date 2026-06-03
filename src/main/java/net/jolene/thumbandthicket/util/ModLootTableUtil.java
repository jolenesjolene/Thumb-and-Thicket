package net.jolene.thumbandthicket.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;

public class ModLootTableUtil {
    private static final BiMap<Identifier, LootContextType> MAP = HashBiMap.create();

    public static final RegistryKey<LootTable> CLAM_LOOT = RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(MOD_ID, "gameplay/clam_loot"));

    public static final LootContextType CLAM = register(Identifier.of(MOD_ID, "gameplay/clam_loot"), builder -> builder.require(LootContextParameters.ORIGIN).require(LootContextParameters.BLOCK_ENTITY));

    private static LootContextType register(Identifier identifier, Consumer<LootContextType.Builder> type) {
        LootContextType.Builder builder = new LootContextType.Builder();
        type.accept(builder);
        LootContextType lootContextType = builder.build();
        LootContextType lootContextType2 = MAP.put(identifier, lootContextType);
        if (lootContextType2 != null) {
            throw new IllegalStateException("Loot table parameter set " + identifier + " is already registered");
        } else {
            return lootContextType;
        }
    }
}