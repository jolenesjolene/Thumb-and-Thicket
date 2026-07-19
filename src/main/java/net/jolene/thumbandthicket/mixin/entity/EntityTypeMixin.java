package net.jolene.thumbandthicket.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityType.class)
public class EntityTypeMixin {

    @WrapOperation(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;register(Ljava/lang/String;Lnet/minecraft/entity/EntityType$Builder;)Lnet/minecraft/entity/EntityType;", ordinal = 22))
    private static EntityType<CowEntity> gay(String id, EntityType.Builder<CowEntity> type, Operation<EntityType<CowEntity>> original) {
        return original.call(id, type.dimensions(0.9F,1.6F).eyeHeight(1.3F));
    }
}
