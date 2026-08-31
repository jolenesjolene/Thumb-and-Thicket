package net.jolene.thumbandthicket.entity.custom.goals;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class LookAtBeehiveGoal extends Goal {

    private final MobEntity mob;
    private final int range;
    @Nullable private BlockPos targetPos;
    private int delay;
    private int lookTime;
    private  int cooldownDelay;

    public LookAtBeehiveGoal(MobEntity mob, int range) {
        this.mob = mob;
        this.range = range;
        this.setControls(EnumSet.of(Goal.Control.LOOK, Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (this.cooldownDelay > 0) {
            this.cooldownDelay--;
            return false;
        }
        if (this.mob.getRandom().nextFloat() >= 0.5) return false;
        this.targetPos = locateClosestHive(this.mob.getWorld(), this.mob, range);
        return targetPos != null && mob.isAlive();
    }

    @Nullable
    protected BlockPos locateClosestHive(BlockView world, Entity entity, int range) {
        BlockPos blockPos = entity.getBlockPos();
        if (!world.getBlockState(blockPos).getCollisionShape(world, blockPos).isEmpty()) return null;
        return BlockPos.findClosest(entity.getBlockPos(), range, range, pos -> world.getBlockState(pos).isIn(BlockTags.BEEHIVES)).orElse(null);
    }

    @Override
    public boolean shouldContinue() {
        return this.targetPos != null && this.lookTime > 0 && mob.isAlive();
    }

    @Override
    public void start() {
        lookTime = this.getTickCount(40 + this.mob.getRandom().nextInt(40));
    }

    @Override
    public void stop() {
        this.targetPos = null;
        this.mob.getNavigation().stop();
        this.cooldownDelay = this.getTickCount(200);
    }

    @Override
    public void tick() {
        if (targetPos != null && this.mob.isAlive()) {
            double distance = this.mob.getBlockPos().getSquaredDistanceFromCenter(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ());
            if (distance >= 8.0) {
                if (--this.delay > 0) return;
                this.delay = this.getTickCount(10);
                if (!this.mob.getNavigation().isFollowingPath()) this.mob.getNavigation().startMovingTo(this.targetPos.getX() + this.mob.getRandom().nextBetween(-2, 2), this.targetPos.getY(), this.targetPos.getZ() + this.mob.getRandom().nextBetween(-2, 2), 0.75);
            } else {
                if (this.mob.getNavigation().isFollowingPath()) this.mob.getNavigation().stop();
                this.mob.getLookControl().lookAt(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ());
                --this.lookTime;
            }
        }
    }
}
