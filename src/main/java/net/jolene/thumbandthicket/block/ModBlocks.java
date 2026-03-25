package net.jolene.thumbandthicket.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.jolene.thumbandthicket.ThumbAndThicket;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block ROOT_BLOCK = register(new RootBlock(AbstractBlock.Settings.copy(Blocks.ROOTED_DIRT).sounds(BlockSoundGroup.HANGING_ROOTS)), "root_block", true);
    public static final Block ROOTED_GRASS = register(new RootedDirtBlock(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.HANGING_ROOTS)), "rooted_grass", true);
    public static final Block PUFFED_DANDELION = register(new PuffedDandelionBlock(AbstractBlock.Settings.copy(Blocks.DANDELION)), "puffed_dandelion", true);
    public static final Block CLOVERS = register(new FlowerbedBlock(AbstractBlock.Settings.copy(Blocks.PINK_PETALS)), "clovers", true);
    public static final Block SNOWY_GRASS = register(new ShortPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS)), "snowy_grass", true);

    private static Block register(Block block, String name, boolean hasItem) {
        Identifier id = ThumbAndThicket.id(name);
        if (hasItem) {
            Item item = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, item);
        }
        return Registry.register(Registries.BLOCK, id, block);

    }

    public static void registerModBlocks() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.addBefore(Items.HANGING_ROOTS, ROOT_BLOCK);
            entries.addAfter(Items.ROOTED_DIRT, ROOTED_GRASS);
            entries.addAfter(Items.PINK_PETALS, CLOVERS);
            entries.addAfter(Items.SHORT_GRASS, SNOWY_GRASS);
        });
    }


    public static void initialize() {
    }
}

