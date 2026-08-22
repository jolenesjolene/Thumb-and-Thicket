package net.jolene.thumbandthicket.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(EscapeDangerGoal.class)
public abstract class EscapeDangerGoalMixin extends Goal {

    @Shadow
    protected final PathAwareEntity mob;

    protected EscapeDangerGoalMixin(PathAwareEntity mob) {
        this.mob = mob;
    }

    @WrapMethod(method = "isInDanger")
    protected boolean thumbandthicket$isInDanger(Operation<Boolean> original) {
        return mob.getAttacker() != null || original.call();
    }

    @WrapMethod(method = "start")
    private void thumbandthicket$makeHerdsPanic(Operation<Void> original) {
        if (!mob.getWorld().isClient()) {
            List<PathAwareEntity> list = mob.getWorld().getEntitiesByClass(PathAwareEntity.class, mob.getBoundingBox().expand(10), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR);
            for (PathAwareEntity entity : list) if (entity.getType() == mob.getType()) if (mob.getAttacker() != null && entity.getAttacker() == null && mob.distanceTo(mob.getAttacker()) < 5 && mob.distanceTo(entity) < 10) entity.setAttacker(mob.getAttacker());
        }
        original.call();
    }
}
