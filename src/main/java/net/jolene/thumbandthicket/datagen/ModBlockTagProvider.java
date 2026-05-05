package net.jolene.thumbandthicket.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.jolene.thumbandthicket.block.ModBlockTags;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModBlockTags.ROOTY_BLOCKS).add(Blocks.ROOTED_DIRT, ModBlocks.ROOT_BLOCK, ModBlocks.ROOTED_GRASS);
        getOrCreateTagBuilder(ModBlockTags.STACKABLE_MUSHROOMS).add(Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM);
        getOrCreateTagBuilder(ModBlockTags.SNIPPABLE).add(Blocks.VINE, Blocks.SUGAR_CANE);
        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE).add(ModBlocks.SNOWY_SHORT_GRASS);
        getOrCreateTagBuilder(ModBlockTags.GRASS_BLOCKS).add(Blocks.GRASS_BLOCK);
        getOrCreateTagBuilder(ModBlockTags.TALL_CROPS).add(Blocks.WHEAT);
        getOrCreateTagBuilder(BlockTags.DIRT).add(ModBlocks.ROOTED_GRASS, ModBlocks.ROOTED_PODZOL);
        getOrCreateTagBuilder(BlockTags.MUSHROOM_GROW_BLOCK).add(ModBlocks.ROOTED_PODZOL);
        getOrCreateTagBuilder(BlockTags.SNOW).add(ModBlocks.SNOWY_BUSH, ModBlocks.SNOWY_SHORT_GRASS, ModBlocks.SNOWY_SHORT_FERN, ModBlocks.SNOWY_TALL_GRASS, ModBlocks.SNOWY_LARGE_FERN);
    }
}
