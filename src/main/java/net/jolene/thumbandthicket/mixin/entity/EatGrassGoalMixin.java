package net.jolene.thumbandthicket.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.util.GrazingMemoryHolder;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EatGrassGoal.class)
public class EatGrassGoalMixin {
    @Shadow
    @Final
    private MobEntity mob;

    @WrapMethod(method = "canStart")
    private boolean thumbandthicket$cancelEatingGrass(Operation<Boolean> original) {
        GrazingMemoryHolder holder = (GrazingMemoryHolder) this.mob;

        if (holder.thumbandthicket$hasGrazedPosition(this.mob.getBlockPos().down())) return false;
        return original.call();
    }
}
