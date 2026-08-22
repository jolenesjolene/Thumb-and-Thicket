package net.jolene.thumbandthicket.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;
import static net.jolene.thumbandthicket.entity.ModEntities.BROWN_BEAR;

public class ModEntityTypeTagsProvider extends FabricTagProvider<EntityType<?>> {
    public ModEntityTypeTagsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, RegistryKeys.ENTITY_TYPE ,completableFuture);
    }

    public static final TagKey<EntityType<?>> LARGE_LITTER = TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MOD_ID , "large_litter"));
    public static final TagKey<EntityType<?>> MEDIUM_LITTER = TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MOD_ID , "medium_litter"));

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(LARGE_LITTER)
                .add(EntityType.PIG)
                .add(EntityType.FOX)
                .add(EntityType.RABBIT);
        getOrCreateTagBuilder(MEDIUM_LITTER)
                .add(EntityType.OCELOT)
                .add(EntityType.POLAR_BEAR)
                .add(BROWN_BEAR)
                .add(EntityType.CAT)
                .add(EntityType.WOLF);
    }
}
