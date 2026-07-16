package net.jolene.thumbandthicket.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.fluid.ModFluids;
import net.jolene.thumbandthicket.util.ModCauldronBehavior;
import net.jolene.thumbandthicket.util.ModProperties;
import net.jolene.thumbandthicket.world.gen.ModConfiguredFeatures;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.effect.StatusEffects;
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
    public static final Block ROOTED_GRASS = register(new RootedGrassBlock(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.HANGING_ROOTS).ticksRandomly()), "rooted_grass", true);
    public static final Block ROOTED_PODZOL = register(new RootedPodzolBlock(AbstractBlock.Settings.copy(Blocks.PODZOL).sounds(BlockSoundGroup.HANGING_ROOTS)), "rooted_podzol", true);
    public static final Block PUFFED_DANDELION = register(new PuffedDandelionBlock(AbstractBlock.Settings.copy(Blocks.DANDELION)), "puffed_dandelion", true);
    public static final Block ROSE = register(new FlowerBlock(StatusEffects.SATURATION, 10,AbstractBlock.Settings.copy(Blocks.POPPY)), "rose", true);
    public static final Block BLUE_ROSE = register(new FlowerBlock(StatusEffects.POISON, 10,AbstractBlock.Settings.copy(Blocks.POPPY)), "blue_rose", true);
    public static final Block CLOVERS = register(new CloverBlock(AbstractBlock.Settings.copy(Blocks.PINK_PETALS)), "clovers", true);
    public static final Block SNOWY_BUSH = register(new ShortSnowyPlantBlock(AbstractBlock.Settings.create().mapColor(MapColor.WHITE).replaceable().noCollision().nonOpaque().blockVision((state, world, pos) -> state.get(ModProperties.LAYERS) >= 8).hardness(0.1f).sounds(BlockSoundGroup.GRASS).pistonBehavior(PistonBehavior.DESTROY).dynamicBounds()), "snowy_bush", true);
    public static final Block SNOWY_SHORT_GRASS = register(new ShortSnowyPlantBlock(AbstractBlock.Settings.create().mapColor(MapColor.WHITE).replaceable().noCollision().nonOpaque().blockVision((state, world, pos) -> state.get(ModProperties.LAYERS) >= 8).hardness(0.1f).sounds(BlockSoundGroup.GRASS).pistonBehavior(PistonBehavior.DESTROY).dynamicBounds()), "snowy_short_grass", true);
    public static final Block SNOWY_TALL_GRASS = register(new TallSnowyPlantBlock(AbstractBlock.Settings.create().mapColor(MapColor.WHITE).replaceable().noCollision().nonOpaque().blockVision((state, world, pos) -> state.get(ModProperties.LAYERS) >= 8).hardness(0.1f).sounds(BlockSoundGroup.GRASS).pistonBehavior(PistonBehavior.DESTROY).dynamicBounds()), "snowy_tall_grass", true);
    public static final Block SNOWY_SHORT_FERN = register(new ShortSnowyPlantBlock(AbstractBlock.Settings.create().mapColor(MapColor.WHITE).replaceable().noCollision().nonOpaque().blockVision((state, world, pos) -> state.get(ModProperties.LAYERS) >= 8).hardness(0.1f).sounds(BlockSoundGroup.GRASS).pistonBehavior(PistonBehavior.DESTROY).dynamicBounds()), "snowy_fern", true);
    public static final Block SNOWY_LARGE_FERN = register(new TallSnowyPlantBlock(AbstractBlock.Settings.create().mapColor(MapColor.WHITE).replaceable().noCollision().nonOpaque().blockVision((state, world, pos) -> state.get(ModProperties.LAYERS) >= 8).hardness(0.1f).sounds(BlockSoundGroup.GRASS).pistonBehavior(PistonBehavior.DESTROY).dynamicBounds()), "snowy_large_fern", true);
    public static final Block TINGED_SHORT_GRASS = register(new ShortPlantBlock(AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).dynamicBounds()), "tinged_short_grass", true);
    public static final Block LAVENDER = register(new FlowerBlock(StatusEffects.SATURATION, 10,AbstractBlock.Settings.copy(Blocks.POPPY)), "lavender", true);
    public static final Block MYCELIAL_SPROUTS = register(new SproutsBlock(AbstractBlock.Settings.create().noCollision().nonOpaque().sounds(BlockSoundGroup.FUNGUS).replaceable().mapColor(MapColor.MAGENTA).pistonBehavior(PistonBehavior.DESTROY).breakInstantly().offset(AbstractBlock.OffsetType.XZ)), "mycelial_sprouts", true);
    public static final Block SHORT_LILAC = register(new FlowerBlock(StatusEffects.POISON, 10,AbstractBlock.Settings.copy(Blocks.LILAC)), "short_lilac", true);
    public static final Block PURPLE_MUSHROOM = register(new MushroomPlantBlock(ModConfiguredFeatures.HUGE_PURPLE_MUSHROOM_KEY, AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM)), "purple_mushroom", true);
    public static final Block PURPLE_MUSHROOM_BLOCK = register(new MushroomBlock(AbstractBlock.Settings.copy(Blocks.RED_MUSHROOM_BLOCK)), "purple_mushroom_block", true);
    public static final Block MILKWEED = register(new WaterloggedTallPlantBlock(AbstractBlock.Settings.copy(Blocks.LARGE_FERN)), "milkweed", true);
    public static final Block CATTAIL = register(new WaterloggedTallPlantBlock(AbstractBlock.Settings.copy(Blocks.LARGE_FERN)), "cattail", true);
    public static final Block BEACH_GRASS = register(new TallPlantBlock(AbstractBlock.Settings.copy(Blocks.LARGE_FERN)), "beach_grass", true);
    public static final Block WILTED_CROP = register(new WiltedCropBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.CROP).hardness(0.0f).noCollision()), "wilted_crop", false);
    public static final Block DEW_DROP = register(new DewDropPlantBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.CROP).hardness(0.0f).noCollision()), "dew_drop", true);
    public static final Block DEW_CAULDRON = register(new DewCauldronBlock(AbstractBlock.Settings.copy(Blocks.CAULDRON), ModCauldronBehavior.DEW_CAULDRON_BEHAVIOR), "dew_cauldron", false);
    public static final Block DEW_DROP_CROP_CAULDRON = register(new DewDropCropBlock(AbstractBlock.Settings.copy(Blocks.CAULDRON).ticksRandomly()), "dew_drop_crop_cauldron", true);
    public static final Block FLOWER_CROP = register(new FlowerCropBlock(AbstractBlock.Settings.copy(Blocks.CARROTS).ticksRandomly()), "flower_crop");
    public static final Block BRINE_BLOCK = register(new FluidBlock(ModFluids.BRINE_SOURCE, AbstractBlock.Settings.create().mapColor(MapColor.TEAL).noCollision().nonOpaque().replaceable()), "brine_block", true);
    public static final Block HYDROTHERMAL_VENT_BLOCK = register(new HydrothermalVentBlockBlock(AbstractBlock.Settings.create().hardness(1.5f).mapColor(MapColor.BROWN)), "hydrothermal_vent_block", true);
    public static final Block HYDROTHERMAL_VENT = register(new HydrothermalVentBlock(AbstractBlock.Settings.create().hardness(1.5f).mapColor(MapColor.BROWN)), "hydrothermal_vent", true);
    public static final Block CLAM_SLAB_BLOCK = register(new ClamSlabBlock(AbstractBlock.Settings.create().hardness(1.5f).mapColor(MapColor.BROWN)), "clam_slab", true);
    public static final Block PERMAFROST = register(new Block(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK).mapColor(MapColor.BROWN)), "permafrost", true);
    public static final Block MUDDY_GRASS = register(new Block(AbstractBlock.Settings.copy(Blocks.GRASS_BLOCK).mapColor(MapColor.BROWN)), "muddy_grass", true);

    private static Block register(Block block, String name, boolean hasItem) {
        Identifier id = ThumbAndThicket.id(name);
        if (hasItem) {
            Item item = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, item);
        }
        return Registry.register(Registries.BLOCK, id, block);

    }

    public static Block register(Block block, String name) {
        Identifier id = ThumbAndThicket.id(name);
        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void registerModBlocks() {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.addBefore(Items.HANGING_ROOTS, ROOT_BLOCK);
            entries.addAfter(Items.ROOTED_DIRT, ROOTED_GRASS, ROOTED_PODZOL);
            entries.addAfter(Items.PINK_PETALS, CLOVERS);
            entries.addAfter(Items.SHORT_GRASS, SNOWY_SHORT_GRASS);
            entries.addAfter(Items.TALL_GRASS, SNOWY_TALL_GRASS);
            entries.addAfter(Items.FERN, SNOWY_SHORT_FERN);
            entries.addAfter(Items.LARGE_FERN, SNOWY_LARGE_FERN);
            entries.addAfter(Items.DANDELION, PUFFED_DANDELION);
            entries.addAfter(Items.POPPY, ROSE);
            entries.addAfter(ModBlocks.ROSE, BLUE_ROSE);
            entries.addAfter(Items.NETHER_SPROUTS, MYCELIAL_SPROUTS);
            entries.addAfter(com.blackgear.vanillabackport.common.registries.ModBlocks.BUSH.get(), SNOWY_BUSH);
        });
    }


    public static void initialize() {
    }
}

