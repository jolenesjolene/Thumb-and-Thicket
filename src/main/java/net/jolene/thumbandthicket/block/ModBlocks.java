package net.jolene.thumbandthicket.block;

import com.blackgear.vanillabackport.common.level.blocks.ActualBushBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.util.ModProperties;
import net.jolene.thumbandthicket.world.gen.ModConfiguredFeatures;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block ROOT_BLOCK = register(new RootBlock(AbstractBlock.Settings.copy(Blocks.ROOTED_DIRT).sounds(BlockSoundGroup.HANGING_ROOTS).ticksRandomly()), "root_block", true);
    public static final Block ROOTED_GRASS = register(new RootedGrassBlock(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.HANGING_ROOTS)), "rooted_grass", true);
    public static final Block PUFFED_DANDELION = register(new PuffedDandelionBlock(AbstractBlock.Settings.copy(Blocks.DANDELION)), "puffed_dandelion", true);
    public static final Block CLOVERS = register(new FlowerbedBlock(AbstractBlock.Settings.copy(Blocks.PINK_PETALS)), "clovers", true);
    public static final Block SNOWY_BUSH = register(new ShortSnowyPlantBlock(AbstractBlock.Settings.copy(com.blackgear.vanillabackport.common.registries.ModBlocks.BUSH.get()).dynamicBounds().ticksRandomly()), "snowy_bush", true);
    public static final Block SNOWY_SHORT_GRASS = register(new ShortSnowyPlantBlock(AbstractBlock.Settings.create().mapColor(MapColor.WHITE).replaceable().noCollision().nonOpaque().blockVision((state, world, pos) -> state.get(ModProperties.LAYERS) >= 8).hardness(0.1f).sounds(BlockSoundGroup.GRASS).pistonBehavior(PistonBehavior.DESTROY).dynamicBounds()), "snowy_short_grass", true);
    public static final Block SNOWY_TALL_GRASS = register(new TallPlantBlock(AbstractBlock.Settings.copy(Blocks.TALL_GRASS).dynamicBounds().ticksRandomly()), "snowy_tall_grass", true);
    public static final Block SNOWY_SHORT_FERN = register(new ShortSnowyPlantBlock(AbstractBlock.Settings.copy(Blocks.FERN).dynamicBounds().ticksRandomly()), "snowy_fern", true);
    public static final Block SNOWY_LARGE_FERN = register(new TallPlantBlock(AbstractBlock.Settings.copy(Blocks.LARGE_FERN).dynamicBounds().ticksRandomly()), "snowy_large_fern", true);
    public static final Block TINGED_SHORT_GRASS = register(new ShortPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).dynamicBounds().ticksRandomly()), "tinged_short_grass", true);
    public static final Block LAVENDER = register(new ShortPlantBlock(AbstractBlock.Settings.copy(Blocks.POPPY)), "lavender", true);
    public static final Block SHORT_LILAC = register(new ShortPlantBlock(AbstractBlock.Settings.copy(Blocks.LILAC)), "short_lilac", true);
    public static final Block PURPLE_MUSHROOM = register(new MushroomPlantBlock(ModConfiguredFeatures.PURPLE_MUSHROOM_KEY, AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM)), "purple_mushroom", true);
    public static final Block PURPLE_MUSHROOM_BLOCK = register(new MushroomBlock(AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM_BLOCK)), "purple_mushroom_block", true);
    public static final Block MILKWEED = register(new TallPlantBlock(AbstractBlock.Settings.copy(Blocks.LARGE_FERN)), "milkweed", true);

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
            entries.addAfter(Items.SHORT_GRASS, SNOWY_SHORT_GRASS);
            entries.addAfter(Items.TALL_GRASS, SNOWY_TALL_GRASS);
            entries.addAfter(Items.FERN, SNOWY_SHORT_FERN);
            entries.addAfter(Items.LARGE_FERN, SNOWY_LARGE_FERN);
            entries.addAfter(Items.DANDELION, PUFFED_DANDELION);
            entries.addAfter(com.blackgear.vanillabackport.common.registries.ModBlocks.BUSH.get(), SNOWY_BUSH);
        });
    }


    public static void initialize() {
    }
}

