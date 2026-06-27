package net.jolene.thumbandthicket.mixin.entity;

import net.jolene.thumbandthicket.util.GrazingAnimator;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PigEntity.class)
public class PigEntityMixin extends MobEntity implements GrazingAnimator {

    @Unique
    private EatGrassGoal eatGrassGoal;
    @Unique
    private int eatGrassTimer;
    @Unique
    private static final int MAX_GRASS_TIMER = 40;

    protected PigEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("HEAD"))
    private void thumbandthicket$addEatGrassGoal(CallbackInfo ci) {
        this.eatGrassGoal = new EatGrassGoal(this);
        this.goalSelector.add(5, this.eatGrassGoal);
    }

    @Override
    protected void mobTick() {
        this.eatGrassTimer = this.eatGrassGoal.getTimer();
        super.mobTick();
    }

    public void tickMovement() {
        if (this.getWorld().isClient) {
            this.eatGrassTimer = Math.max(0, this.eatGrassTimer - 1);
        }

        super.tickMovement();
    }

    public void handleStatus(byte status) {
        if (status == 10) {
            this.eatGrassTimer = 40;
        } else {
            super.handleStatus(status);
        }

    }

    @Override
    public float thumbandthicket$getNeckAngle(float delta) {
        if (this.eatGrassTimer <= 0) {
            return 0.0F;
        } else if (this.eatGrassTimer >= 4 && this.eatGrassTimer <= 36) {
            return 1.0F;
        } else {
            return this.eatGrassTimer < 4 ? ((float)this.eatGrassTimer - delta) / 4.0F : -((float)(this.eatGrassTimer - 40) - delta) / 4.0F;
        }
    }

    @Override
    public float thumbandthicket$getHeadAngle(float delta) {
        if (this.eatGrassTimer > 4 && this.eatGrassTimer <= 36) {
            float f = ((float)(this.eatGrassTimer - 4) - delta) / 32.0F;
            return ((float)Math.PI / 5F) + 0.21991149F * MathHelper.sin(f * 28.7F);
        } else {
            return this.eatGrassTimer > 0 ? ((float)Math.PI / 5F) : this.getPitch() * ((float)Math.PI / 180F);
        }
    }
}
