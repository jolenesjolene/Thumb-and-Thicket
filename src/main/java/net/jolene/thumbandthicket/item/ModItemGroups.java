package net.jolene.thumbandthicket.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
        public static final ItemGroup THUMB_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
                Identifier.of(ThumbAndThicket.MOD_ID, "thumb_and_thicket"),
                FabricItemGroup.builder().icon(() -> new ItemStack(Items.ROOTED_DIRT))
                        .displayName(Text.translatable("itemgroup.thumbandthicket.thumbandthicket"))
                        .entries((displayContext, entries) -> {
                            entries.add(ModBlocks.ROOT_BLOCK);
                            entries.add(Items.DANDELION);
                            entries.add(ModBlocks.PUFFED_DANDELION);
                            entries.add(Items.LILAC);

                        }).build());



    public static void registerItemGroups() {
        ThumbAndThicket.LOGGER.info("Registering Item Group for " + ThumbAndThicket.MOD_ID);
    }
}
