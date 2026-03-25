package net.jolene.thumbandthicket.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.jolene.thumbandthicket.ThumbAndThicket;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems {

    public static final FoodComponent PRICKLY_PEAR_FOOD_COMPONENT = new FoodComponent.Builder().alwaysEdible().snack().nutrition(4).saturationModifier(0.3f).build();

    public static final Item PRICKLY_PEAR = register(new Item(new Item.Settings().maxCount(16).food(PRICKLY_PEAR_FOOD_COMPONENT)), "prickly_pear");

    private static Item register(Item item, String name) {
        return Registry.register(Registries.ITEM, ThumbAndThicket.id(name), item);
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS)
                .register(group -> {
                    group.addAfter(Items.APPLE, ModItems.PRICKLY_PEAR);
                });
    }
}
