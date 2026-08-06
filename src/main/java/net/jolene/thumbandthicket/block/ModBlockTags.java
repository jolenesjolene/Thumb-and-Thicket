package net.jolene.thumbandthicket.block;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModBlockTags {
    public static final TagKey<Block> ROOTY_BLOCKS = block("rooty_blocks");
    public static final TagKey<Block> STACKABLE_MUSHROOMS = block("stackable_mushrooms");
    public static final TagKey<Block> SNIPPABLE = block("snippable");
    public static final TagKey<Block> GRASS_BLOCKS = block("grass_blocks");
    public static final TagKey<Block> TALL_CROPS = block("tall_crops");

    public static final TagKey<Block> CHISELABLE_BLOCKS = block("chiselable_blocks");
    public static final TagKey<Block> CHISELED_BLOCKS = block("chiseled_blocks");
    public static final TagKey<Block> MOSSABLE_BLOCKS = block("mossable_blocks");
    public static final TagKey<Block> MOSSY_BLOCKS = block("mossy_blocks");
    public static final TagKey<Block> CRACKABLE_BLOCKS = block("crackable_blocks");
    public static final TagKey<Block> CRACKED_BLOCKS = block("cracked_blocks");

    private static TagKey<Block> block(String name) {
        return TagKey.of(RegistryKeys.BLOCK, ThumbAndThicket.id(name));
    }
}
