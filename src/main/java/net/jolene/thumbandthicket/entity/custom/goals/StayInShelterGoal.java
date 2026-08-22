package net.jolene.thumbandthicket.entity.custom.goals;

import net.minecraft.entity.ai.goal.AvoidSunlightGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class StayInShelterGoal extends AvoidSunlightGoal {
    private final PathAwareEntity mob;
    public StayInShelterGoal(PathAwareEntity mob) {
        super(mob);
        this.mob = mob;
    }

    @Override
    public boolean canStart() {
        return this.mob.getWorld().isRaining() || this.mob.getWorld().isNight();
    }
}
