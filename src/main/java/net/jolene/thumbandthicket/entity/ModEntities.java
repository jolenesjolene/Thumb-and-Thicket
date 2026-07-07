package net.jolene.thumbandthicket.entity;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.entity.custom.BrownBearEntity;
import net.jolene.thumbandthicket.entity.custom.LoraxEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<BrownBearEntity> BROWN_BEAR = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(ThumbAndThicket.MOD_ID, "brown_bear"),
            EntityType.Builder.create(BrownBearEntity::new, SpawnGroup.CREATURE)
                    .dimensions(1.5f, 1.5f).build());
    public static final EntityType<LoraxEntity> LORAX = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(ThumbAndThicket.MOD_ID, "lorax"),
            EntityType.Builder.create(LoraxEntity::new, SpawnGroup.CREATURE)
                    .dimensions(1f, 1f).build());


    public static void registerModEntities() {
        ThumbAndThicket.LOGGER.info("Registering Mod Entities for " + ThumbAndThicket.MOD_ID);
    }
}