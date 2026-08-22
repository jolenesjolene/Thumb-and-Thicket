package net.jolene.thumbandthicket.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.item.ModItems;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.MOSS_BLOCK)
                .pattern("mm")
                .pattern("mm")
                .input('m', ModItems.MOSS_CLUMP)
                .group("multi_bench")
                .criterion(FabricRecipeProvider.hasItem(Items.MOSS_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.MOSS_BLOCK))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.MOSS_CARPET, 2)
                .pattern("mm")
                .input('m', ModItems.MOSS_CLUMP)
                .group("multi_bench")
                .criterion(FabricRecipeProvider.hasItem(Items.MOSS_CARPET), FabricRecipeProvider.conditionsFromItem(Items.MOSS_CARPET))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.BOWL)
                .pattern("b b")
                .pattern(" b ")
                .input('b', ModItems.BARK)
                .group("multi_bench")
                .criterion(FabricRecipeProvider.hasItem(Items.BOWL), FabricRecipeProvider.conditionsFromItem(Items.BOWL))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.CHISEL)
                .pattern(" i ")
                .pattern(" c ")
                .pattern(" s ")
                .input('i', Items.IRON_INGOT)
                .input('c', Items.COPPER_INGOT)
                .input('s', Items.STICK)
                .group("multi_bench")
                .criterion(FabricRecipeProvider.hasItem(ModItems.CHISEL), FabricRecipeProvider.conditionsFromItem(ModItems.CHISEL))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModItems.MOSS_CLUMP, 4)
                .input(Items.MOSS_BLOCK)
                .criterion(FabricRecipeProvider.hasItem(Items.MOSS_BLOCK), FabricRecipeProvider.conditionsFromItem(Items.MOSS_BLOCK))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.WAXED_BRUSH)
                .input(Items.BRUSH)
                .input(Items.HONEYCOMB)
                .input(Items.HONEYCOMB)
                .input(Items.HONEYCOMB)
                .criterion(FabricRecipeProvider.hasItem(ModItems.WAXED_BRUSH), FabricRecipeProvider.conditionsFromItem(ModItems.WAXED_BRUSH))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.STICK, 2)
                .input(ModItems.BARK)
                .criterion(FabricRecipeProvider.hasItem(ModItems.BARK), FabricRecipeProvider.conditionsFromItem(ModItems.BARK))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Items.ROOTED_DIRT)
                .input(Items.DIRT)
                .input(Items.HANGING_ROOTS)
                .criterion(FabricRecipeProvider.hasItem(Items.HANGING_ROOTS), FabricRecipeProvider.conditionsFromItem(Items.HANGING_ROOTS))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ROOTED_GRASS)
                .input(Items.GRASS_BLOCK)
                .input(Items.HANGING_ROOTS)
                .criterion(FabricRecipeProvider.hasItem(Items.HANGING_ROOTS), FabricRecipeProvider.conditionsFromItem(Items.HANGING_ROOTS))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.ROOTED_PODZOL)
                .input(Items.PODZOL)
                .input(Items.HANGING_ROOTS)
                .criterion(FabricRecipeProvider.hasItem(Items.HANGING_ROOTS), FabricRecipeProvider.conditionsFromItem(Items.HANGING_ROOTS))
                .offerTo(exporter);
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.JACK_O_GOURD)
                .input(ModBlocks.JACK_O_GOURD)
                .input(Items.TORCH)
                .criterion(FabricRecipeProvider.hasItem(ModBlocks.CARVED_PALE_GOURD), FabricRecipeProvider.conditionsFromItem(ModBlocks.CARVED_PALE_GOURD))
                .offerTo(exporter);
    }
}
