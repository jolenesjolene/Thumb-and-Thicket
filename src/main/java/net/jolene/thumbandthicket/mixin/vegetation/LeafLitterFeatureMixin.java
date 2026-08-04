package net.jolene.thumbandthicket.mixin.vegetation;

import com.blackgear.vanillabackport.common.level.features.LeafLitterFeature;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LeafLitterFeature.class)
public class LeafLitterFeatureMixin {

    @WrapOperation(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/StructureWorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    private boolean gay(StructureWorldAccess instance, BlockPos blockPos, BlockState blockState, int i, Operation<Boolean> original) {
        return original.call(instance, blockPos, blockState.with(Properties.WATERLOGGED, false), i);
    }

}
