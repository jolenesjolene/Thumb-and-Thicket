package net.jolene.thumbandthicket;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayer;

public class ThumbAndThicketClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(Blocks.BIRCH_LOG, RenderLayer.getCutout());
    }
}