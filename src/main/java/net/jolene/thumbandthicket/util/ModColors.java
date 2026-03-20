package net.jolene.thumbandthicket.util;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.world.biome.GrassColors;

public class ModColors {
    public static void registerBlockColors() {

        ColorProviderRegistry.BLOCK.register((blockState, world, pos, tintIndex) -> {
            if (tintIndex != 0) {
                if (world == null || pos == null) {
                    return GrassColors.getDefaultColor();
                }
                return BiomeColors.getGrassColor(world, pos);
            }
            return -1;
        }, ModBlocks.ROOTED_GRASS);

        ColorProviderRegistry.BLOCK.register((blockState, world, pos, tintIndex) -> {
            if (tintIndex != 0) {
                if (world == null || pos == null) {
                    return GrassColors.getDefaultColor();
                }
                return BiomeColors.getGrassColor(world, pos);
            }
            return -1;
        }, ModBlocks.CLOVERS);
    }
}
