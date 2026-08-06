package net.jolene.thumbandthicket.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.jolene.thumbandthicket.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public static final TagKey<Item> CAN_FERTILIZE_FARMLAND = TagKey.of(RegistryKeys.ITEM, Identifier.of("thumbandthicket" + "can_fertilize_farmland"));

    public static final TagKey<Item> CAN_REPAIR_BRICK = TagKey.of(RegistryKeys.ITEM, Identifier.of("immersive_interactions", "can_repair_brick"));
    public static final TagKey<Item> CAN_APPLY_MOSS = TagKey.of(RegistryKeys.ITEM, Identifier.of("immersive_interactions", "can_apply_moss"));
    public static final TagKey<Item> CAN_APPLY_BARK = TagKey.of(RegistryKeys.ITEM, Identifier.of("immersive_interactions", "can_apply_bark"));
    public static final TagKey<Item> CAN_WAX_COPPER = TagKey.of(RegistryKeys.ITEM, Identifier.of("immersive_interactions", "can_wax_copper"));


    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(CAN_REPAIR_BRICK)
                .add(Items.CLAY_BALL);

        getOrCreateTagBuilder(CAN_APPLY_MOSS)
                .add(Items.MOSS_CARPET)
                .add(ModItems.MOSS_CLUMP);

        getOrCreateTagBuilder(CAN_APPLY_BARK)
                .add(ModItems.BARK);

        getOrCreateTagBuilder(CAN_WAX_COPPER)
                .add(ModItems.WAXED_BRUSH)
                .add(Items.HONEYCOMB);
    }
}