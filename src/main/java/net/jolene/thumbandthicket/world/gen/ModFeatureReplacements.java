package net.jolene.thumbandthicket.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;

public class ModFeatureReplacements {
    public static void removeFeatures() {
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_grass")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasTag(ConventionalBiomeTags.IS_SNOWY_PLAINS) && context.hasPlacedFeature(VegetationPlacedFeatures.PATCH_GRASS_BADLANDS)),
                context -> context.getGenerationSettings().removeFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        VegetationPlacedFeatures.PATCH_GRASS_BADLANDS
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_grass_and_ferns")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasTag(ConventionalBiomeTags.IS_SNOWY) && context.hasPlacedFeature(VegetationPlacedFeatures.PATCH_GRASS_TAIGA_2)),
                context -> context.getGenerationSettings().removeFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        VegetationPlacedFeatures.PATCH_GRASS_TAIGA_2
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_large_ferns")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasTag(ConventionalBiomeTags.IS_SNOWY) && context.hasPlacedFeature(VegetationPlacedFeatures.PATCH_LARGE_FERN)),
                context -> context.getGenerationSettings().removeFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        VegetationPlacedFeatures.PATCH_LARGE_FERN
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_tall_grass")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasTag(ConventionalBiomeTags.IS_SNOWY) && context.hasPlacedFeature(VegetationPlacedFeatures.PATCH_TALL_GRASS_2)),
                context -> context.getGenerationSettings().removeFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        VegetationPlacedFeatures.PATCH_TALL_GRASS_2
                )
        );
    }

    public static void addFeatures() {
        BiomeModifications.create(Identifier.of(MOD_ID, "add_snowy_grass")).add(
                ModificationPhase.ADDITIONS,
                context -> (context.hasTag(ConventionalBiomeTags.IS_SNOWY_PLAINS) && context.hasPlacedFeature(VegetationPlacedFeatures.PATCH_GRASS_BADLANDS)),
                context -> context.getGenerationSettings().addFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        ModPlacedFeatures.SNOWY_GRASS
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "add_snowy_grass_and_ferns")).add(
                ModificationPhase.ADDITIONS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.PATCH_GRASS_TAIGA_2) && context.hasTag(ConventionalBiomeTags.IS_SNOWY)),
                context -> context.getGenerationSettings().addFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        ModPlacedFeatures.SNOWY_TAIGA_GRASS
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "add_snowy_large_fern")).add(
                ModificationPhase.ADDITIONS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.PATCH_LARGE_FERN) && context.hasTag(ConventionalBiomeTags.IS_SNOWY)),
                context -> context.getGenerationSettings().addFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        ModPlacedFeatures.SNOWY_LARGE_FERN
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "add_snowy_tall_grass")).add(
                ModificationPhase.ADDITIONS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.PATCH_TALL_GRASS_2) && context.hasTag(ConventionalBiomeTags.IS_SNOWY)),
                context -> context.getGenerationSettings().addFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        ModPlacedFeatures.SNOWY_TALL_GRASS
                )
        );
        BiomeModifications.addFeature(BiomeSelectors.tag(ConventionalBiomeTags.IS_TEMPERATE), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.CLOVERS);
    }

}
