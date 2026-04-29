package net.jolene.thumbandthicket.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
        public static final ItemGroup THUMB_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
                Identifier.of(ThumbAndThicket.MOD_ID, "thumb_and_thicket"),
                FabricItemGroup.builder().icon(() -> new ItemStack(Items.OAK_SAPLING))
                        .displayName(Text.translatable("itemgroup.thumbandthicket.thumbandthicket"))
                        .entries((displayContext, entries) -> {
                            entries.add(Items.OAK_SAPLING);
                            entries.add(Items.SPRUCE_SAPLING);
                            entries.add(Items.BIRCH_SAPLING);
                            entries.add(Items.JUNGLE_SAPLING);
                            entries.add(Items.ACACIA_SAPLING);
                            entries.add(Items.DARK_OAK_SAPLING);
                            entries.add(Items.MANGROVE_PROPAGULE);
                            entries.add(Items.CHERRY_SAPLING);
                            entries.add(Items.SHEARS);
                            entries.add(Items.DANDELION);
                            entries.add(ModBlocks.PUFFED_DANDELION);
                            entries.add(ModBlocks.CLOVERS);
                            entries.add(Blocks.LILY_PAD);
                            entries.add(ModBlocks.LAVENDER);
                            entries.add(ModBlocks.MILKWEED);
                            entries.add(ModBlocks.PURPLE_MUSHROOM);
                            entries.add(ModBlocks.PURPLE_MUSHROOM_BLOCK);
                            entries.add(ModBlocks.TINGED_SHORT_GRASS);
                            entries.add(ModBlocks.SHORT_LILAC);
                            entries.add(Blocks.LILAC);
                            entries.add(ModItems.PRICKLY_PEAR);
                            entries.add(ModItems.GOLDEN_PRICKLY_PEAR);
                            entries.add(Items.RED_MUSHROOM);
                            entries.add(Items.BROWN_MUSHROOM);
                            entries.add(Items.WHEAT_SEEDS);
                            entries.add(Items.WHEAT);
                            entries.add(ModBlocks.ROOTED_GRASS);
                            entries.add(Blocks.ROOTED_DIRT);
                            entries.add(ModBlocks.ROOT_BLOCK);
                            entries.add(ModBlocks.SNOWY_SHORT_GRASS);
                            entries.add(ModBlocks.SNOWY_TALL_GRASS);
                            entries.add(ModBlocks.SNOWY_SHORT_FERN);
                            entries.add(ModBlocks.SNOWY_LARGE_FERN);
                            entries.add(ModBlocks.SNOWY_BUSH);
                            entries.add(ModItems.BROWN_BEAR_SPAWN_EGG);
                        }).build());



    public static void registerItemGroups() {
        ThumbAndThicket.LOGGER.info("Registering Item Group for " + ThumbAndThicket.MOD_ID);
    }
}
