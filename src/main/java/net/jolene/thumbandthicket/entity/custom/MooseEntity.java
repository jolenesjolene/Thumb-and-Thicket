package net.jolene.thumbandthicket.entity.custom;

import net.jolene.thumbandthicket.entity.ModEntities;
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
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MooseEntity extends AnimalEntity {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();

    private static final TrackedData<Boolean> ANGRY =
            DataTracker.registerData(MooseEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private int idleAnimationTimeout;
    private int riderAttackCooldown;
    private int riderTargetCheckCooldown;
    private float smoothedHeadPitch;
    private boolean hasRetaliated;

    public MooseEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!getWorld().isClient) {
            player.startRiding(this);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 4) {
            attackAnimationState.start(age);
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater) {
        double yaw = Math.toRadians(getYaw());

        positionUpdater.accept(
                passenger,
                getX() + Math.sin(yaw) * 0.35D,
                getY() + getHeight() * 0.725F,
                getZ() - Math.cos(yaw) * 0.35D
        );
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (getFirstPassenger() instanceof PlayerEntity player) {
            float yaw = player.getYaw();

            setYaw(yaw);
            setBodyYaw(yaw);
            setHeadYaw(yaw);

            float targetPitch = MathHelper.clamp(player.getPitch(), -45.0F, 25.0F);
            smoothedHeadPitch += (targetPitch - smoothedHeadPitch) * 0.15F;

            setPitch(smoothedHeadPitch);
            prevYaw = yaw;

            super.travel(new Vec3d(
                    player.sidewaysSpeed * 0.2F,
                    0,
                    player.forwardSpeed * 0.4F
            ));
            return;
        }

        super.travel(movementInput);
    }

    private void performAttackEffects(LivingEntity target) {
        getWorld().sendEntityStatus(this, (byte) 4);
        playSound(
                ModSounds.MOOSE_ATTACK,
                1.0F,
                1.0F
        );
        spawnCritParticles(target);
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (hasRetaliated) return false;

        boolean result = super.tryAttack(target);

        if (result && !getWorld().isClient()) {
            hasRetaliated = true;

            if (target instanceof LivingEntity livingTarget) {
                performAttackEffects(livingTarget);
            }

            setTarget(null);
            setAttacker(null);
            getNavigation().stop();
            setAngry(false);
        }

        return result;
    }

    private void riderAttack() {
        if (--riderTargetCheckCooldown > 0) return;
        riderTargetCheckCooldown = 5;

        if (!(getFirstPassenger() instanceof PlayerEntity rider)) return;

        var targets = getWorld().getEntitiesByClass(
                LivingEntity.class,
                getBoundingBox()
                        .stretch(getRotationVec(1.0F))
                        .expand(0.5),
                entity -> entity != this && entity != rider
        );

        if (targets.isEmpty()) return;

        LivingEntity target = targets.getFirst();

        performAttackEffects(target);

        float yaw = getYaw() * MathHelper.RADIANS_PER_DEGREE;

        target.takeKnockback(
                getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK),
                MathHelper.sin(yaw),
                -MathHelper.cos(yaw)
        );

        riderAttackCooldown = 20;
    }

    private void spawnCritParticles(LivingEntity target) {
        if (getWorld() instanceof ServerWorld world) {
            world.spawnParticles(
                    net.minecraft.particle.ParticleTypes.CRIT,
                    target.getX(),
                    target.getBodyY(0.5),
                    target.getZ(),
                    10,
                    0.4,
                    0.5,
                    0.4,
                    0.5
            );
        }
    }

    @Override
    protected void initGoals() {
        goalSelector.add(0, new SwimGoal(this));
        goalSelector.add(1, new MeleeAttackGoal(this, 1.2D, true));
        goalSelector.add(2, new AnimalMateGoal(this, 1.0F));
        goalSelector.add(3, new TemptGoal(this, 1.0F, Ingredient.ofItems(Items.STICK), false));
        goalSelector.add(4, new FollowParentGoal(this, 1.0F));
        goalSelector.add(5, new WanderAroundGoal(this, 1.0F));
        goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        goalSelector.add(7, new LookAroundGoal(this));

        super.initGoals();
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2F)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 6);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.MOOSE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.MOOSE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.MOOSE_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        playSound(ModSounds.MOOSE_STEP, 0.1F, 1.25F);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean result = super.damage(source, amount);

        if (result && source.getAttacker() instanceof LivingEntity attacker) {
            hasRetaliated = false;
            setTarget(attacker);
            setAngry(true);
        }

        return result;
    }

    @Override
    public void tick() {
        super.tick();

        if (riderAttackCooldown > 0) {
            riderAttackCooldown--;
        } else if (getFirstPassenger() instanceof PlayerEntity) {
            riderAttack();
        }

        if (getWorld().isClient()) {
            setupAnimationStates();
        } else if (getTarget() == null && isAngry()) {
            setAngry(false);
        }
    }

    private void setupAnimationStates() {
        if (idleAnimationTimeout-- <= 0) {
            idleAnimationTimeout = 80;
            idleAnimationState.start(age);
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.STICK);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.MOOSE.create(world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ANGRY, false);
    }

    public boolean isAngry() {
        return dataTracker.get(ANGRY);
    }

    public void setAngry(boolean angry) {
        dataTracker.set(ANGRY, angry);
    }

}
