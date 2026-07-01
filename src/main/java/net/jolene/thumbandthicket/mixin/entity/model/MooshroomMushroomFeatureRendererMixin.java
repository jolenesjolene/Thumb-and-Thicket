package net.jolene.thumbandthicket.mixin.entity.model;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.render.entity.feature.MooshroomMushroomFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MooshroomMushroomFeatureRenderer.class)
public abstract class MooshroomMushroomFeatureRendererMixin {

    @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/MooshroomEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V", ordinal = 1))
    private static void thumbandthicket$renderThirdMushroom(MatrixStack instance, float x, float y, float z, Operation<Void> original) {
        original.call(instance, -0.4F, -0.3F, -0.1F);
    }

    @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/MooshroomEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V", ordinal = 4))
    private static void thumbandthicket$renderSecondMushroom(MatrixStack instance, float x, float y, float z, Operation<Void> original) {
        original.call(instance, -0.2F, -0.3F, -0.3F);
    }

    @WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/passive/MooshroomEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(FFF)V", ordinal = 6))
    private static void thumbandthicket$renderHeadMushroom(MatrixStack instance, float x, float y, float z, Operation<Void> original) {
        original.call(instance, -0.2F, -0.4F, -0.4F);
    }
}
