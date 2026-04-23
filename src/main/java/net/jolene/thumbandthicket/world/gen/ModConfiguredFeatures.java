package net.jolene.thumbandthicket.world.gen;

import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerbedBlock;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;
import static net.jolene.thumbandthicket.world.gen.feature.ModFeatures.HUGE_PURPLE_MUSHROOM_FEATURE;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> PURPLE_MUSHROOM_KEY = registerKey("huge_purple_mushroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SNOWY_TAIGA_GRASS_KEY = registerKey("snowy_taiga_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SNOWY_GRASS_KEY = registerKey("snowy_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SNOWY_TALL_GRASS_KEY = registerKey("snowy_tall_grass");
    public static final RegistryKey<ConfiguredFeature<?, ?>> SNOWY_LARGE_FERN_KEY = registerKey("snowy_large_fern");

    public static final RegistryKey<ConfiguredFeature<?, ?>> CLOVERS_KEY = registerKey("clovers");


    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, PURPLE_MUSHROOM_KEY, HUGE_PURPLE_MUSHROOM_FEATURE, new HugeMushroomFeatureConfig(
                BlockStateProvider.of(ModBlocks.PURPLE_MUSHROOM_BLOCK), BlockStateProvider.of(Blocks.MUSHROOM_STEM),1));
        register(context, SNOWY_TAIGA_GRASS_KEY, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(ModBlocks.SNOWY_SHORT_GRASS.getDefaultState().with(ModProperties.LAYERS, 1)).add(ModBlocks.SNOWY_SHORT_FERN.getDefaultState().with(ModProperties.LAYERS, 1)).build()))));
        register(context, SNOWY_GRASS_KEY, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.SNOWY_SHORT_GRASS.getDefaultState().with(ModProperties.LAYERS, 1)))));
        register(context, SNOWY_TALL_GRASS_KEY, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.SNOWY_TALL_GRASS.getDefaultState().with(ModProperties.LAYERS, 1)))));
        register(context, SNOWY_LARGE_FERN_KEY, Feature.RANDOM_PATCH, ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(BlockStateProvider.of(ModBlocks.SNOWY_LARGE_FERN.getDefaultState().with(ModProperties.LAYERS, 1)))));

        DataPool.Builder<BlockState> cloversBuilder = DataPool.builder();
        for (int i = 1; i <= 4; ++i) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                cloversBuilder.add(ModBlocks.CLOVERS.getDefaultState().with(FlowerbedBlock.FLOWER_AMOUNT, i).with(FlowerbedBlock.FACING, direction), 1);
            }
        }
        register(context, CLOVERS_KEY, Feature.RANDOM_PATCH, new RandomPatchFeatureConfig(8, 7, 3, PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(cloversBuilder)))));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}