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
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_normal_red_mushrooms")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL)),
                context -> context.getGenerationSettings().removeFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        VegetationPlacedFeatures.RED_MUSHROOM_NORMAL
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_nether_red_mushrooms")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.RED_MUSHROOM_NETHER)),
                context -> context.getGenerationSettings().removeFeature(
                        GenerationStep.Feature.UNDERGROUND_DECORATION,
                        VegetationPlacedFeatures.RED_MUSHROOM_NETHER
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_taiga_red_mushrooms")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.RED_MUSHROOM_TAIGA)),
                context -> context.getGenerationSettings().removeFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        VegetationPlacedFeatures.RED_MUSHROOM_TAIGA
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_old_growth_red_mushrooms")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.RED_MUSHROOM_OLD_GROWTH)),
                context -> context.getGenerationSettings().removeFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        VegetationPlacedFeatures.RED_MUSHROOM_OLD_GROWTH
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_swamp_red_mushrooms")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.RED_MUSHROOM_SWAMP)),
                context -> context.getGenerationSettings().removeFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        VegetationPlacedFeatures.RED_MUSHROOM_SWAMP
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_normal_brown_mushrooms")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL)),
                context -> context.getGenerationSettings().removeFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_nether_brown_mushrooms")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.BROWN_MUSHROOM_NETHER)),
                context -> context.getGenerationSettings().removeFeature(
                        GenerationStep.Feature.UNDERGROUND_DECORATION,
                        VegetationPlacedFeatures.BROWN_MUSHROOM_NETHER
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_taiga_brown_mushrooms")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.BROWN_MUSHROOM_TAIGA)),
                context -> context.getGenerationSettings().removeFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        VegetationPlacedFeatures.BROWN_MUSHROOM_TAIGA
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_old_growth_brown_mushrooms")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.BROWN_MUSHROOM_OLD_GROWTH)),
                context -> context.getGenerationSettings().removeFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        VegetationPlacedFeatures.BROWN_MUSHROOM_OLD_GROWTH
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "remove_swamp_brown_mushrooms")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.BROWN_MUSHROOM_SWAMP)),
                context -> context.getGenerationSettings().removeFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        VegetationPlacedFeatures.BROWN_MUSHROOM_SWAMP
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
        BiomeModifications.create(Identifier.of(MOD_ID, "add_normal_red_mushrooms")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.RED_MUSHROOM_NORMAL)),
                context -> context.getGenerationSettings().addFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        ModPlacedFeatures.RED_MUSHROOM_NORMAL
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "add_nether_red_mushrooms")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.RED_MUSHROOM_NETHER)),
                context -> context.getGenerationSettings().addFeature(
                        GenerationStep.Feature.UNDERGROUND_DECORATION,
                        ModPlacedFeatures.RED_MUSHROOM_NETHER
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "add_taiga_red_mushrooms")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.RED_MUSHROOM_TAIGA)),
                context -> context.getGenerationSettings().addFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        ModPlacedFeatures.RED_MUSHROOM_TAIGA
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "add_old_growth_red_mushrooms")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.RED_MUSHROOM_OLD_GROWTH)),
                context -> context.getGenerationSettings().addFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        ModPlacedFeatures.RED_MUSHROOM_OLD_GROWTH
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "add_swamp_red_mushrooms")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.RED_MUSHROOM_SWAMP)),
                context -> context.getGenerationSettings().addFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        ModPlacedFeatures.RED_MUSHROOM_SWAMP
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "add_normal_brown_mushrooms")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.BROWN_MUSHROOM_NORMAL)),
                context -> context.getGenerationSettings().addFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        ModPlacedFeatures.BROWN_MUSHROOM_NORMAL
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "add_nether_brown_mushrooms")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.BROWN_MUSHROOM_NETHER)),
                context -> context.getGenerationSettings().addFeature(
                        GenerationStep.Feature.UNDERGROUND_DECORATION,
                        ModPlacedFeatures.BROWN_MUSHROOM_NETHER
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "add_taiga_brown_mushrooms")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.BROWN_MUSHROOM_TAIGA)),
                context -> context.getGenerationSettings().addFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        ModPlacedFeatures.BROWN_MUSHROOM_TAIGA
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "add_old_growth_brown_mushrooms")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.BROWN_MUSHROOM_OLD_GROWTH)),
                context -> context.getGenerationSettings().addFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        ModPlacedFeatures.BROWN_MUSHROOM_OLD_GROWTH
                )
        );
        BiomeModifications.create(Identifier.of(MOD_ID, "add_swamp_brown_mushrooms")).add(
                ModificationPhase.REMOVALS,
                context -> (context.hasPlacedFeature(VegetationPlacedFeatures.BROWN_MUSHROOM_SWAMP)),
                context -> context.getGenerationSettings().addFeature(
                        GenerationStep.Feature.VEGETAL_DECORATION,
                        ModPlacedFeatures.BROWN_MUSHROOM_SWAMP
                )
        );

        BiomeModifications.addFeature(BiomeSelectors.tag(ConventionalBiomeTags.IS_TEMPERATE), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.CLOVERS);
        BiomeModifications.addFeature(BiomeSelectors.tag(ConventionalBiomeTags.IS_DRY), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.TINGED_GRASS);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.MUSHROOM_FIELDS), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.PURPLE_MUSHROOM_TAIGA);
        BiomeModifications.addFeature(BiomeSelectors.tag(ConventionalBiomeTags.IS_TEMPERATE), GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.MILKWEED);

    }

}
