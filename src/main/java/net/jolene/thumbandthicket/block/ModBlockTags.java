package net.jolene.thumbandthicket.block;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModBlockTags {
    public static final TagKey<Block> ROOTY_BLOCKS = block("rooty_blocks");
    public static final TagKey<Block> STACKABLE_MUSHROOMS = block("stackable_mushrooms");
    public static final TagKey<Block> SNIPPABLE = block("snippable");

    private static TagKey<Block> block(String name) {
        return TagKey.of(RegistryKeys.BLOCK, ThumbAndThicket.id(name));
    }
}
