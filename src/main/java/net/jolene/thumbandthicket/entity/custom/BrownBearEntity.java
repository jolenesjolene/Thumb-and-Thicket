package net.jolene.thumbandthicket.entity.custom;

import net.jolene.thumbandthicket.entity.ModEntities;
import net.jolene.thumbandthicket.entity.custom.goals.LookAtBeehiveGoal;
import net.jolene.thumbandthicket.sound.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BrownBearEntity extends AnimalEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    // Angry State
    private static final TrackedData<Boolean> ANGRY =
            DataTracker.registerData(BrownBearEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public BrownBearEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        // Behaviour
        this.goalSelector.add(0, new SwimGoal(this));

        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.75D, true));

        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0F));
        this.goalSelector.add(3, new TemptGoal(this, 1.0F, Ingredient.ofItems(Items.HONEYCOMB), false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.0F));
        this.goalSelector.add(5, new WanderAroundGoal(this, 1.0F));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0F));
        this.goalSelector.add(5, new LookAtBeehiveGoal(this, 8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));

        // Retaliation Goal
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        super.initGoals();
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23F)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10) // stronger now
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.5);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.BROWN_BEAR_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.BROWN_BEAR_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.BROWN_BEAR_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(ModSounds.BROWN_BEAR_STEP, 0.1F, 1.25F);
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.playSound(ModSounds.BROWN_BEAR_ATTACK, 1.0F, 1.0F);
        return super.tryAttack(target);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean result = super.damage(source, amount);

        if (source.getAttacker() instanceof LivingEntity attacker) {
            this.setTarget(attacker);
            this.setAngry(true);
        }

        return result;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }

        if (!this.getWorld().isClient()) {
            if (this.getTarget() == null) {
                this.setAngry(false);
            }
        }
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 60;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.HONEYCOMB);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.BROWN_BEAR.create(world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ANGRY, false);
    }

    public boolean isAngry() {
        return this.dataTracker.get(ANGRY);
    }

    public void setAngry(boolean angry) {
        this.dataTracker.set(ANGRY, angry);
    }
}
