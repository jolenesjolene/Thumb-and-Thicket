package net.jolene.thumbandthicket.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;

public class ModModelProvider extends FabricModelProvider {
    public static final Map<Model, List<Item>> ITEM_MODEL_LISTS = Map.ofEntries(
            Map.entry(Models.GENERATED, List.of(
                    ModItems.ROTTEN_APPLE,
                    ModItems.PEAR,
                    ModItems.ORANGE,
                    ModItems.CAVE_PARSNIP,
                    ModItems.LUCKY_CLOVER,
                    ModItems.PRICKLY_PEAR,
                    ModItems.DEW_BOTTLE,
                    ModItems.DEW_DROP_SAPLING,
                    ModItems.GOLDEN_PRICKLY_PEAR,
                    ModItems.CHERRY,
                    ModItems.FLOWER_SEEDS,
                    ModItems.BRINE_BUCKET,
                    ModItems.PEARL,
                    ModBlocks.SNOWY_SHORT_GRASS.asItem(),
                    ModBlocks.SNOWY_SHORT_FERN.asItem(),
                    ModBlocks.SNOWY_BUSH.asItem(),
                    ModBlocks.CLOVERS.asItem(),
                    ModBlocks.PUFFED_DANDELION.asItem(),
                    ModBlocks.ROSE.asItem(),
                    ModBlocks.BLUE_ROSE.asItem(),
                    ModBlocks.PURPLE_MUSHROOM.asItem(),
                    ModBlocks.TINGED_SHORT_GRASS.asItem(),
                    ModBlocks.MYCELIAL_SPROUTS.asItem()
            )),
            Map.entry(Models.HANDHELD, List.of(
            ))
    );
    public static final Map<Model, List<Identifier>> COMPAT_ITEM_MODEL_LISTS = Map.ofEntries(
            Map.entry(Models.GENERATED, List.of(
            ))
    );
    public static final Map<Model, List<Block>> BLOCK_MODEL_LISTS = Map.ofEntries(
            Map.entry(Models.CUBE_ALL, List.of(
            )),
            Map.entry(Models.CROSS, List.of(
                    ModBlocks.PUFFED_DANDELION,
                    ModBlocks.SNOWY_SHORT_GRASS
            ))
    );

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        for (var items : ITEM_MODEL_LISTS.entrySet()) {
            Model model = items.getKey();
            for (Item item : items.getValue()) {
                generator.register(item, model);
            }
        }

        for (var items : COMPAT_ITEM_MODEL_LISTS.entrySet()) {
            Model model = items.getKey();
            for (Identifier item : items.getValue()) {
                Identifier itemModelId = item.withPrefixedPath("item/");
                model.upload(itemModelId, TextureMap.layer0(itemModelId), generator.writer);
            }
        }
    }
}
