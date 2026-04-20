package net.jolene.thumbandthicket.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;

public class ModFeatureReplacements {
    public static void removeFeatures() {
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_grass")).add(
                ModificationPhase.REMOVALS,
                context -> context.hasTag(ConventionalBiomeTags.IS_COLD),
                context -> context.getGenerationSettings().removeFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        VegetationPlacedFeatures.PATCH_GRASS_BADLANDS
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_grass_and_ferns")).add(
                ModificationPhase.REMOVALS,
                context -> context.hasTag(ConventionalBiomeTags.IS_COLD),
                context -> context.getGenerationSettings().removeFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        VegetationPlacedFeatures.PATCH_GRASS_TAIGA
                )
        );
    }

    public static void addFeatures() {
        BiomeModifications.create(Identifier.of(MOD_ID, "add_snowy_grass")).add(
                ModificationPhase.ADDITIONS,
                context -> context.hasTag(ConventionalBiomeTags.IS_COLD),
                context -> context.getGenerationSettings().addFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        ModPlacedFeatures.SNOWY_GRASS
                )
        );
    }

}
