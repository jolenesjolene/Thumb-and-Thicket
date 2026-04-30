package net.jolene.thumbandthicket.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.effect.ModEffects;
import net.jolene.thumbandthicket.entity.ModEntities;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

public class ModItems {


    // The Prickly Effect will cause nearby entities to take cactus-like damage from the player

    public static final FoodComponent PRICKLY_PEAR_FOOD_COMPONENT = new FoodComponent.Builder().alwaysEdible().snack().nutrition(4).saturationModifier(0.3f).build();
    public static final FoodComponent GOLDEN_PRICKLY_PEAR_FOOD_COMPONENT = new FoodComponent.Builder().alwaysEdible().snack().nutrition(4).saturationModifier(1.2f).statusEffect(new StatusEffectInstance(ModEffects.PRICKLY, 100, 0), 1.0F).build();
    public static final FoodComponent ROTTEN_APPLE_FOOD_COMPONENT = new FoodComponent.Builder().nutrition(2).saturationModifier(0.3F).statusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 100, 0), 0.6F).build();

    public static final Item ROTTEN_APPLE = register(new Item(new Item.Settings().maxCount(64).food(ROTTEN_APPLE_FOOD_COMPONENT)), "rotten_apple");
    public static final Item PRICKLY_PEAR = register(new Item(new Item.Settings().maxCount(64).food(PRICKLY_PEAR_FOOD_COMPONENT)), "prickly_pear");
    public static final Item GOLDEN_PRICKLY_PEAR = register(new Item(new Item.Settings().rarity(Rarity.RARE).maxCount(64).food(GOLDEN_PRICKLY_PEAR_FOOD_COMPONENT)), "golden_prickly_pear");

    public static final Item BROWN_BEAR_SPAWN_EGG = register(
            new SpawnEggItem(ModEntities.BROWN_BEAR, 0x000000, 0x000000, new Item.Settings()),
            "brown_bear_spawn_egg");

    private static Item register(Item item, String name) {
        return Registry.register(Registries.ITEM, ThumbAndThicket.id(name), item);
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK)
                .register(group -> {
                    group.addAfter(Items.APPLE, ModItems.ROTTEN_APPLE);
                    group.addAfter(Items.ENCHANTED_GOLDEN_APPLE, ModItems.PRICKLY_PEAR);
                    group.addAfter(ModItems.PRICKLY_PEAR, ModItems.GOLDEN_PRICKLY_PEAR);
                });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS)
                .register(group -> {
                    group.addAfter(Items.POLAR_BEAR_SPAWN_EGG, ModItems.BROWN_BEAR_SPAWN_EGG);
                });
    }
}
