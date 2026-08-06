package net.jolene.thumbandthicket.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;

public class ModKeys {
    public static final RegistryKey<Block> PALE_GOURD = ofBlock("pale_gourd");
    public static final RegistryKey<Block> PALE_GOURD_STEM = ofBlock("pale_gourd_stem");
    public static final RegistryKey<Block> ATTACHED_PALE_GOURD_STEM = ofBlock("attached_pale_gourd_stem");
    public static final RegistryKey<Item> PALE_GOURD_SEEDS = ofItem("pale_gourd_seeds");

    private static RegistryKey<Block> ofBlock(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(MOD_ID, id));
    }

    private static RegistryKey<Item> ofItem(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, id));
    }
}
