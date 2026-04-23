package net.jolene.thumbandthicket.world.gen;

import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;
import static net.jolene.thumbandthicket.world.gen.feature.ModFeatures.HUGE_PURPLE_MUSHROOM_FEATURE;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> PURPLE_MUSHROOM_KEY = registerKey("huge_purple_mushroom");
//    public static final RegistryKey<ConfiguredFeature<?, ?>> SNOWY_TAIGA_GRASS_KEY = registerKey("snowy_taiga_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SNOWY_GRASS_KEY = registerKey("snowy_grass");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, PURPLE_MUSHROOM_KEY, HUGE_PURPLE_MUSHROOM_FEATURE, new HugeMushroomFeatureConfig(
                BlockStateProvider.of(ModBlocks.PURPLE_MUSHROOM_BLOCK), BlockStateProvider.of(Blocks.MUSHROOM_STEM),1));
//        register(context, SNOWY_TAIGA_GRASS_KEY, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(Blockstate)));
        register(
                context, SNOWY_GRASS_KEY, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(
                        Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(
                                ModBlocks.SNOWY_SHORT_GRASS.getDefaultState().with(ModProperties.LAYERS, 1)))));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}