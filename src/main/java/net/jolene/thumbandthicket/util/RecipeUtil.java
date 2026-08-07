package net.jolene.thumbandthicket.util;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.recipe.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecipeUtil {
    public static void registerRecipeDisabler() {
        ServerLifecycleEvents.SERVER_STARTED.register(RecipeUtil::applyRecipeFilter);

        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) -> {
            if (success) applyRecipeFilter(server);
        });
    }

    public static void applyRecipeFilter(MinecraftServer server) {
        RecipeManager recipeManager = server.getRecipeManager();
        Collection<RecipeEntry<?>> allRecipes = recipeManager.values();
        List<RecipeEntry<?>> recipesToKeep = new ArrayList<>(allRecipes.size());

        for (RecipeEntry<?> entry : allRecipes) {
            Identifier identifier = entry.id();
            Recipe<?> recipe = entry.value();

            if (recipe instanceof ShapelessRecipe && identifier.getPath().contains("from_honeycomb")) continue;
            if (recipe instanceof ShapedRecipe && ((identifier.getPath().contains("chiseled") && !identifier.getPath().equals("chiseled_bookshelf")) || identifier.getPath().contains("mossy"))) continue;
            if (recipe instanceof StonecuttingRecipe && (identifier.getPath().contains("chiseled") || identifier.getPath().contains("mossy"))) continue;
            if (recipe instanceof SmeltingRecipe && identifier.getPath().contains("cracked")) continue;

            recipesToKeep.add(entry);
        }

        recipeManager.setRecipes(recipesToKeep);
    }
}
