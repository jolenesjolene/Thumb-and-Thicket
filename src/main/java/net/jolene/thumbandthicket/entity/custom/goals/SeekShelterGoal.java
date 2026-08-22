package net.jolene.thumbandthicket.entity.custom.goals;

import net.minecraft.entity.ai.goal.EscapeSunlightGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class SeekShelterGoal extends EscapeSunlightGoal {
    public SeekShelterGoal(PathAwareEntity mob, double speed) {
        super(mob, speed);
    }

    @Override
    public boolean canStart() {
        return (this.mob.getWorld().isRaining() || this.mob.getWorld().isNight()) && this.mob.getWorld().isSkyVisible(this.mob.getBlockPos()) && this.targetShadedPos();
    }
}
