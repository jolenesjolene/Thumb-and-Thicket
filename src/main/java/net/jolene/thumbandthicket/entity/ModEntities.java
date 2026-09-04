package net.jolene.thumbandthicket.entity;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.entity.custom.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<BeaverEntity> BEAVER = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(ThumbAndThicket.MOD_ID, "beaver"),
            EntityType.Builder.create(BeaverEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.75f, 0.75f).build());
    public static final EntityType<BrownBearEntity> BROWN_BEAR = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(ThumbAndThicket.MOD_ID, "brown_bear"),
            EntityType.Builder.create(BrownBearEntity::new, SpawnGroup.CREATURE)
                    .dimensions(1.5f, 1.5f).build());
    public static final EntityType<MooseEntity> MOOSE = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(ThumbAndThicket.MOD_ID, "moose"),
            EntityType.Builder.create(MooseEntity::new, SpawnGroup.CREATURE)
                    .dimensions(1.5f, 2.25f).build());
    public static final EntityType<DeerEntity> DEER = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(ThumbAndThicket.MOD_ID, "deer"),
            EntityType.Builder.create(DeerEntity::new, SpawnGroup.CREATURE)
                    .dimensions(1.0f, 1.75f).build());


    public static void registerModEntities() {
        ThumbAndThicket.LOGGER.info("Registering Mod Entities for " + ThumbAndThicket.MOD_ID);
    }
}