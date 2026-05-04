package net.jolene.thumbandthicket.world.gen;

import net.jolene.thumbandthicket.world.gen.placementmodifier.SnowPlacementModifier;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;

public class ModPlacedFeatures {


    public static final RegistryKey<PlacedFeature> HUGE_PURPLE_MUSHROOM_PLACED = registerKey("huge_purple_mushroom_placed");
    public static final RegistryKey<PlacedFeature> SNOWY_GRASS = registerKey("snowy_grass_placed");
    public static final RegistryKey<PlacedFeature> SNOWY_TAIGA_GRASS = registerKey("snowy_taiga_grass_placed");
    public static final RegistryKey<PlacedFeature> SNOWY_LARGE_FERN = registerKey("snowy_large_fern_placed");
    public static final RegistryKey<PlacedFeature> SNOWY_TALL_GRASS = registerKey("snowy_tall_grass_placed");

    public static final RegistryKey<PlacedFeature> CLOVERS = registerKey("clover_placed");
    public static final RegistryKey<PlacedFeature> RED_MUSHROOM_NORMAL = registerKey("red_mushroom_normal_placed");
    public static final RegistryKey<PlacedFeature> RED_MUSHROOM_NETHER = registerKey("red_mushroom_nether_placed");
    public static final RegistryKey<PlacedFeature> RED_MUSHROOM_TAIGA = registerKey("red_mushroom_taiga_placed");
    public static final RegistryKey<PlacedFeature> RED_MUSHROOM_OLD_GROWTH = registerKey("red_mushroom_old_growth_placed");
    public static final RegistryKey<PlacedFeature> RED_MUSHROOM_SWAMP = registerKey("red_mushroom_swamp_placed");
    public static final RegistryKey<PlacedFeature> BROWN_MUSHROOM_NORMAL = registerKey("brown_mushroom_normal_placed");
    public static final RegistryKey<PlacedFeature> BROWN_MUSHROOM_NETHER = registerKey("brown_mushroom_nether_placed");
    public static final RegistryKey<PlacedFeature> BROWN_MUSHROOM_TAIGA = registerKey("brown_mushroom_taiga_placed");
    public static final RegistryKey<PlacedFeature> BROWN_MUSHROOM_OLD_GROWTH = registerKey("brown_mushroom_old_growth_placed");
    public static final RegistryKey<PlacedFeature> BROWN_MUSHROOM_SWAMP = registerKey("brown_mushroom_swamp_placed");

    public static final RegistryKey<PlacedFeature> ROOTED_GRASS = registerKey("rooted_grass_placed");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> brownMushroom = registryEntryLookup.getOrThrow(ModConfiguredFeatures.BROWN_MUSHROOM_NORMAL_KEY);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> redMushroom = registryEntryLookup.getOrThrow(ModConfiguredFeatures.RED_MUSHROOM_NORMAL_KEY);

        register(context, HUGE_PURPLE_MUSHROOM_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.PURPLE_MUSHROOM_KEY));
        register(context, SNOWY_GRASS, configuredFeatures.getOrThrow(ModConfiguredFeatures.SNOWY_GRASS_KEY), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, SnowPlacementModifier.of());
        register(context, SNOWY_TALL_GRASS, configuredFeatures.getOrThrow(ModConfiguredFeatures.SNOWY_GRASS_KEY), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, SnowPlacementModifier.of());
        register(context, SNOWY_LARGE_FERN, configuredFeatures.getOrThrow(ModConfiguredFeatures.SNOWY_GRASS_KEY), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, SnowPlacementModifier.of());
        register(context, SNOWY_TAIGA_GRASS, configuredFeatures.getOrThrow(ModConfiguredFeatures.SNOWY_TAIGA_GRASS_KEY), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, SnowPlacementModifier.of());

        register(context, CLOVERS, configuredFeatures.getOrThrow(ModConfiguredFeatures.CLOVERS_KEY), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);

        register(context, RED_MUSHROOM_NORMAL, redMushroom, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(512));
        register(context, RED_MUSHROOM_NETHER, redMushroom, SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_TOP_RANGE, RarityFilterPlacementModifier.of(2));
        register(context, RED_MUSHROOM_TAIGA, redMushroom, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(256));
        register(context, RED_MUSHROOM_OLD_GROWTH, redMushroom, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(171));
        register(context, RED_MUSHROOM_SWAMP, redMushroom, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(64));

        register(context, BROWN_MUSHROOM_NORMAL, brownMushroom, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(256));
        register(context, BROWN_MUSHROOM_NETHER, brownMushroom, SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_TOP_RANGE, RarityFilterPlacementModifier.of(2));
        register(context, BROWN_MUSHROOM_TAIGA, brownMushroom, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(4));
        register(context, BROWN_MUSHROOM_OLD_GROWTH, brownMushroom, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(4), CountPlacementModifier.of(3));
        register(context, BROWN_MUSHROOM_SWAMP, brownMushroom, SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, RarityFilterPlacementModifier.of(1), CountPlacementModifier.of(2));

        register(context, ROOTED_GRASS, configuredFeatures.getOrThrow(ModConfiguredFeatures.ROOTED_GRASS_KEY), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP);
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
