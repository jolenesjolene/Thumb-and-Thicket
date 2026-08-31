package net.jolene.thumbandthicket.mixin.farming;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import net.jolene.thumbandthicket.block.WiltedCropBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.task.FarmerVillagerTask;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(FarmerVillagerTask.class)
public abstract class FarmerVillagerTaskMixin {

    @Shadow
    private @Nullable BlockPos currentTarget;

    @Inject(method = "keepRunning(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/VillagerEntity;J)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;", shift = At.Shift.AFTER, ordinal = 1))
    private void thumbandthicket$removeWiltedCrops(ServerWorld serverWorld, VillagerEntity villagerEntity, long l, CallbackInfo ci, @Local Block block) {
        if (block instanceof WiltedCropBlock) serverWorld.breakBlock(this.currentTarget, false, villagerEntity);
    }

    @WrapMethod(method = "isSuitableTarget")
    private boolean thumbandthicket$chooseWiltedCrop(BlockPos pos, ServerWorld world, Operation<Boolean> original) {
        return original.call(pos, world) || world.getBlockState(pos).getBlock() instanceof WiltedCropBlock;
    }
}
