package net.jolene.thumbandthicket.entity;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.world.Heightmap;

public class ModEntitySpawns {

    public static void addSpawns() {

        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BiomeTags.IS_FOREST),
                SpawnGroup.CREATURE,
                ModEntities.BROWN_BEAR,
                20,
                1,
                1
        );
        SpawnRestriction.register(
                ModEntities.BROWN_BEAR,
                SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                AnimalEntity::isValidNaturalSpawn
        );
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BiomeTags.IS_FOREST),
                SpawnGroup.CREATURE,
                ModEntities.DEER,
                50,
                3,
                6
        );
        SpawnRestriction.register(
                ModEntities.DEER,
                SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                AnimalEntity::isValidNaturalSpawn
        );
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(BiomeTags.IS_TAIGA),
                SpawnGroup.CREATURE,
                ModEntities.MOOSE,
                40,
                2,
                4
        );
        SpawnRestriction.register(
                ModEntities.MOOSE,
                SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                AnimalEntity::isValidNaturalSpawn
        );
    }
}