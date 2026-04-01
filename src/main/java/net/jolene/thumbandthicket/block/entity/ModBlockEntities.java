package net.jolene.thumbandthicket.block.entity;

import net.jolene.thumbandthicket.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;

public class ModBlockEntities {
    public static BlockEntityType<RootBlockEntity> ROOT_BLOCK_ENTITY;

    public static void register() {
        ROOT_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                Identifier.of(MOD_ID, "root_block"),
                BlockEntityType.Builder.create(RootBlockEntity::new, ModBlocks.ROOT_BLOCK).build()
        );
    }
}
