package net.jolene.thumbandthicket.block.entity.renderer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jolene.thumbandthicket.block.ClamSlabBlock;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.block.entity.ClamSlabBlockEntity;
import net.jolene.thumbandthicket.util.ModEntityModelLayers;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LidOpenable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;

@Environment(EnvType.CLIENT)
public class ClamSlabBlockEntityRenderer<T extends BlockEntity & LidOpenable> implements BlockEntityRenderer<T> {
    private static final String BASE = "bottom";
    private static final String LID = "lid";
    private final ModelPart clamLid;
    private final ModelPart clamBase;
    private static final Identifier TEXTURE = Identifier.of(MOD_ID, "textures/entity/clam.png");

    public ClamSlabBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        ModelPart modelPart = context.getLayerModelPart(ModEntityModelLayers.CLAM);
        this.clamBase = modelPart.getChild("bottom");
        this.clamLid = modelPart.getChild("lid");
    }

    private int getLightLevel(World world, BlockPos pos) {
        int blockLight = world.getLightLevel(LightType.BLOCK, pos);
        int skyLight = world.getLightLevel(LightType.SKY, pos);
        return LightmapTextureManager.pack(blockLight, skyLight);
    }

    @Override
    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        World world = entity.getWorld();
        boolean bl = world != null;
        BlockState blockState = bl ? entity.getCachedState() : ModBlocks.CLAM_SLAB_BLOCK.getDefaultState().with(Properties.FACING, Direction.SOUTH);
        if (blockState.getBlock() instanceof ClamSlabBlock && entity instanceof ClamSlabBlockEntity clamSlabBlockEntity) {
            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            ItemStack stack = clamSlabBlockEntity.getStack(0);
            
            int rotation = switch (blockState.get(Properties.FACING)) {
                case DOWN, NORTH, UP -> 0;
                case SOUTH -> 180;
                case WEST -> 270;
                case EAST -> 90;
            };

            matrices.push();
            matrices.translate(0.5f, 0.25f, 0.5f);
            matrices.scale(0.5f, 0.5f, 0.5f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(rotation));

            itemRenderer.renderItem(stack, ModelTransformationMode.GUI, getLightLevel(entity.getWorld(), entity.getPos()), OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
            matrices.pop();

            matrices.push();
            float f = blockState.get(Properties.FACING).asRotation();
            matrices.translate(0.5F, 0.5F, 0.5F);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-f));
            matrices.translate(-0.5F, -0.5F, -0.5F);

            float g = entity.getAnimationProgress(tickDelta);
            g = 1.0F - g;
            g = 1.0F - g * g * g;
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(TEXTURE));
            this.render(matrices, vertexConsumer, this.clamLid, this.clamBase, g, light, overlay);
            matrices.pop();
        }
    }

    private void render(MatrixStack matrices, VertexConsumer vertices, ModelPart lid, ModelPart base, float openFactor, int light, int overlay) {
        lid.pitch = -(openFactor * (float) (Math.PI / 2));
        lid.render(matrices, vertices, light, overlay);
        base.render(matrices, vertices, light, overlay);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData base = modelPartData.addChild("bottom", ModelPartBuilder.create().uv(0, 20).cuboid(0.0F, 0.0F, 0.0F, 16.0F, 4.0F, 16.0F), ModelTransform.NONE);
        base.addChild(
                "teeth",
                ModelPartBuilder.create()
                        .uv(0, 10).cuboid(16.0F, 4.0F, 4.0F, 0.0F, 1.0F, 5.0F)
                        .uv(0, 10).cuboid(16.0F, 4.0F, 11.0F, 0.0F, 1.0F, 5.0F)
                        .uv(0, 10).cuboid(0.0F, 4.0F, 4.0F, 0.0F, 1.0F, 5.0F)
                        .uv(0, 10).cuboid(0.0F, 4.0F, 11.0F, 0.0F, 1.0F, 5.0F)
                        .uv(0, 15).cuboid(2.0F, 4.0F, 16.0F, 5.0F, 1.0F, 0.0F)
                        .uv(0, 15).cuboid(9.0F, 4.0F, 16.0F, 5.0F, 1.0F, 0.0F),
                ModelTransform.NONE
        );
        ModelPartData lid = modelPartData.addChild("lid", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, 0.0F, 0.0F, 16.0F, 4.0F, 16.0F), ModelTransform.pivot(0.0F, 4.0F, 0.0F));
        lid.addChild("top_teeth",
                ModelPartBuilder.create()
                        .uv(0, 15).cuboid(0.0F, 3.0F, 24.0F, 3.0F, 1.0F, 0.0F)
                        .uv(0, 15).cuboid(13.0F, 3.0F, 24, 3.0F, 1.0F, 0.0F)
                        .uv(0, 15).cuboid(6.0F, 3.0F, 24.0F, 4.0F, 1.0F, 0.0F)
                        .uv(0, 14).cuboid(0.0F, 3.0F, 23.0F, 0.0F, 1.0F, 1.0F)
                        .uv(0, 11).cuboid(0.0F, 3.0F, 9.0F, 0.0F, 1.0F, 4.0F)
                        .uv(0, 11).cuboid(0.0F, 3.0F, 16.0F, 0.0F, 1.0F, 4.0F)
                        .uv(0, 11).cuboid(16.0F, 3.0F, 9.0F, 0.0F, 1.0F, 4.0F)
                        .uv(0, 11).cuboid(16.0F, 3.0F, 16.0F, 0.0F, 1.0F, 4.0F)
                        .uv(0, 14).cuboid(16.0F, 3.0F, 23.0F, 0.0F, 1.0F, 1.0F),
                ModelTransform.pivot(0.0F, -4.0F, -8.0F)
        );
        return TexturedModelData.of(modelData, 64, 64);
    }
}
