package net.jolene.thumbandthicket.entity.client.Remodels;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.HostileEntity;

public class CreeperRemodel<C extends HostileEntity> extends SinglePartEntityModel<C> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart feet;
    public CreeperRemodel(ModelPart root1, ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.feet = root.getChild("feet");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(36, 60).cuboid(-5.0F, -15.0F, -3.0F, 10.0F, 14.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 58).cuboid(-5.5F, -15.5F, -3.5F, 11.0F, 15.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 19.0F, 0.0F));

        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(13, 8).cuboid(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, new Dilation(0.0F))
                .uv(0, 32).cuboid(-6.5F, -12.5F, -6.5F, 13.0F, 13.0F, 13.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));

        ModelPartData feet = modelPartData.addChild("feet", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -7.0F, -3.0F, 6.0F, 7.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.0F, -7.0F, 7.0F, 6.0F, 7.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(5.0F, -7.0F, -3.0F, 6.0F, 7.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(5.0F, -7.0F, 7.0F, 6.0F, 7.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 24.0F, -5.0F));
        return TexturedModelData.of(modelData, 80, 80);
    }

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
		feet.render(matrices, vertexConsumer, light, overlay, color);
		head.render(matrices, vertexConsumer, light, overlay, color);
		body.render(matrices, vertexConsumer, light, overlay, color);
	}

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(C entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}