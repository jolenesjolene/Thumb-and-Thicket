package net.jolene.thumbandthicket.entity.custom.goals;

import net.jolene.thumbandthicket.entity.custom.DeerEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;

import java.util.EnumSet;

public class DeerPanicGoal extends Goal {
    private final DeerEntity deer;
    private final double speed;

    private int panicTicks;

    private PlayerEntity nearbyPlayer;

    public DeerPanicGoal(DeerEntity deer, double speed) {
        this.deer = deer;
        this.speed = speed;

        this.setControls(EnumSet.of(
                Control.MOVE
        ));
    }

    @Override
    public boolean canStart() {
        if (this.deer.getAttacker() != null) {
            return true;
        }

        PlayerEntity player = this.deer.getWorld().getClosestPlayer(
                this.deer,
                10.0D
        );

        if (player != null && !player.isCreative() && !player.isSpectator()) {
            this.nearbyPlayer = player;
            return true;
        }

        return false;
    }

    @Override
    public void start() {
        this.panicTicks = 20 * 60;
    }

    @Override
    public boolean shouldContinue() {
        return this.panicTicks > 0;
    }

    @Override
    public void tick() {
        this.panicTicks--;

        PlayerEntity player = this.deer.getWorld().getClosestPlayer(
                this.deer,
                32.0D
        );

        if (player != null && !player.isCreative() && !player.isSpectator()) {
            this.nearbyPlayer = player;
        }

        if (this.nearbyPlayer != null) {
            double x = this.deer.getX() - this.nearbyPlayer.getX();
            double z = this.deer.getZ() - this.nearbyPlayer.getZ();

            double distance = Math.sqrt(x * x + z * z);

            if (distance > 0.001D) {
                x /= distance;
                z /= distance;

                double runDistance = 12.0D;

                double targetX = this.deer.getX() + x * runDistance;
                double targetZ = this.deer.getZ() + z * runDistance;

                this.deer.getNavigation().startMovingTo(
                        targetX,
                        this.deer.getY(),
                        targetZ,
                        this.speed
                );
            }
        }
    }

    @Override
    public void stop() {
        this.nearbyPlayer = null;
    }
}