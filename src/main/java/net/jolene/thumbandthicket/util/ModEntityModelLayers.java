package net.jolene.thumbandthicket.util;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.jolene.thumbandthicket.block.entity.renderer.ClamSlabBlockEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;

public class ModEntityModelLayers {
    public static final EntityModelLayer CLAM = new EntityModelLayer(Identifier.of(MOD_ID, "clam"), "main");

    public static void registerModelLayers() {
        EntityModelLayerRegistry.registerModelLayer(
                ModEntityModelLayers.CLAM,
                ClamSlabBlockEntityRenderer::getTexturedModelData
        );
    }
}