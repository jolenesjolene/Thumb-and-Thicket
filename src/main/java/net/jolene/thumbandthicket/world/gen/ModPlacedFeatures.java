package net.jolene.thumbandthicket.world.gen;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.NoiseThresholdCountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.List;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;

public class ModPlacedFeatures {

    public static final RegistryKey<PlacedFeature> HUGE_PURPLE_MUSHROOM_PLACED = registerKey("huge_purple_mushroom_placed");
    public static final RegistryKey<PlacedFeature> SNOWY_GRASS = registerKey("snowy_grass_placed");
    public static final RegistryKey<PlacedFeature> SNOWY_TAIGA_GRASS = registerKey("snowy_taiga_grass_placed");
    public static final RegistryKey<PlacedFeature> SNOWY_LARGE_FERN = registerKey("snowy_large_fern_placed");
    public static final RegistryKey<PlacedFeature> SNOWY_TALL_GRASS = registerKey("snowy_tall_grass_placed");

    public static final RegistryKey<PlacedFeature> CLOVERS = registerKey("clover_placed");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, HUGE_PURPLE_MUSHROOM_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.PURPLE_MUSHROOM_KEY));
        register(context, SNOWY_GRASS, configuredFeatures.getOrThrow(ModConfiguredFeatures.SNOWY_GRASS_KEY), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);
        register(context, SNOWY_TALL_GRASS, configuredFeatures.getOrThrow(ModConfiguredFeatures.SNOWY_GRASS_KEY), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);
        register(context, SNOWY_LARGE_FERN, configuredFeatures.getOrThrow(ModConfiguredFeatures.SNOWY_GRASS_KEY), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);
        register(context, SNOWY_TAIGA_GRASS, configuredFeatures.getOrThrow(ModConfiguredFeatures.SNOWY_TAIGA_GRASS_KEY), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);

        register(context, CLOVERS, configuredFeatures.getOrThrow(ModConfiguredFeatures.CLOVERS_KEY), NoiseThresholdCountPlacementModifier.of(-0.8, 5, 10), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }
}
