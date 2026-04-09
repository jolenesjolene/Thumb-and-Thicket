package net.jolene.thumbandthicket.util;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.item.BlockItem;
import net.minecraft.world.biome.GrassColors;

import java.util.Objects;

public class ModColors {
    public static void registerBlockColors() {

        ColorProviderRegistry.BLOCK.register((blockState, world, pos, tintIndex) -> {
            if (world == null || pos == null) {
                return GrassColors.getDefaultColor();
            }
            return BiomeColors.getGrassColor(world, pos);
        }, ModBlocks.ROOTED_GRASS, ModBlocks.CLOVERS, ModBlocks.SNOWY_SHORT_GRASS, ModBlocks.SNOWY_TALL_GRASS);
    }

    public static void registerItemColors() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            BlockState blockState = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
            return Objects.requireNonNull(ColorProviderRegistry.BLOCK.get(ModBlocks.ROOTED_GRASS)).getColor(blockState, null, null, tintIndex);
        }, ModBlocks.ROOTED_GRASS);
    }
}
