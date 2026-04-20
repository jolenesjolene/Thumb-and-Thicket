package net.jolene.thumbandthicket.world.gen;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> HUGE_PURPLE_MUSHROOM_PLACED = registerKey("huge_purple_mushroom_placed");
    public static final RegistryKey<PlacedFeature> SNOWY_GRASS = registerKey("snowy_grass_placed");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, HUGE_PURPLE_MUSHROOM_PLACED, configuredFeatures.getOrThrow(ModConfiguredFeatures.PURPLE_MUSHROOM_KEY));
        register(context, SNOWY_GRASS, configuredFeatures.getOrThrow(ModConfiguredFeatures.SNOWY_GRASS_KEY));
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
