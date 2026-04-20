package net.jolene.thumbandthicket;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.effect.ModEffects;
import net.jolene.thumbandthicket.sound.ModSounds;
import net.jolene.thumbandthicket.util.ModColors;
import net.jolene.thumbandthicket.world.gen.ModFeatureReplacements;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;

public class ThumbAndThicketClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModColors.registerBlockColors();
        ModColors.registerItemColors();
        ModEffects.registerEffects();
        ModSounds.registerModSounds();

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> client.execute(() -> {
            for (RegistryEntry<Block> entry : Registries.BLOCK.iterateEntries(BlockTags.LOGS)) {
                Block block = entry.value();
                BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
            }
        }));

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(
                new SimpleSynchronousResourceReloadListener() {
                    @Override
                    public Identifier getFabricId() {
                        return Identifier.of(MOD_ID, "log_reload_listener");
                    }

                    @Override
                    public void reload(ResourceManager manager) {
                        for (RegistryEntry<Block> entry : Registries.BLOCK.iterateEntries(BlockTags.LOGS)) {
                            Block block = entry.value();
                            BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
                        }
                    }
                }
        );
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROOT_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ROOTED_GRASS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CLOVERS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PUFFED_DANDELION, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SNOWY_SHORT_GRASS, RenderLayer.getCutout());
    }
}