package net.jolene.thumbandthicket.entity.custom;

import net.jolene.thumbandthicket.entity.ModEntities;
import net.jolene.thumbandthicket.entity.custom.goals.GnawLogGoal;
import net.jolene.thumbandthicket.entity.custom.goals.LookAtBeehiveGoal;
import net.jolene.thumbandthicket.sound.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.control.MoveControl;
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
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BeaverEntity extends AnimalEntity {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState swimAnimationState = new AnimationState();
    public final AnimationState gnawAnimationState = new AnimationState();

    private static final TrackedData<Boolean> GNAWING =
            DataTracker.registerData(BeaverEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private int idleAnimationTimeout = 0;

    public BeaverEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);

        this.moveControl = new BeaverMoveControl(this);
    }

    public boolean isGnawing() {
        return this.dataTracker.get(GNAWING);
    }

    public void setGnawing(boolean gnawing) {
        this.dataTracker.set(GNAWING, gnawing);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);

        builder.add(GNAWING, false);
    }

    @Override
    protected void initGoals() {

        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new BeaverSwimGoal(this));

        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0D));

        this.goalSelector.add(3, new TemptGoal(
                this,
                1.0D,
                Ingredient.ofItems(Blocks.OAK_SAPLING),
                false
        ));
        this.goalSelector.add(4, new GnawLogGoal(this, 24));
        this.goalSelector.add(5, new FollowParentGoal(this, 1.0D));
        this.goalSelector.add(6, new WanderAroundGoal(this, 1.0F));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0D));

        this.goalSelector.add(6, new LookAtEntityGoal(
                this,
                PlayerEntity.class,
                8.0F
        ));

        this.goalSelector.add(7, new LookAroundGoal(this));

        super.initGoals();
    }

    private static class BeaverSwimGoal extends Goal {

        private final BeaverEntity beaver;

        private double targetX;
        private double targetY;
        private double targetZ;

        public BeaverSwimGoal(BeaverEntity beaver) {
            this.beaver = beaver;
        }

        @Override
        public boolean canStart() {

            if (!this.beaver.isTouchingWater()) {
                return false;
            }

            BlockPos pos = this.beaver.getBlockPos();

            this.targetX = pos.getX() + (this.beaver.getRandom().nextInt(20) - 10);
            this.targetY = pos.getY() + (this.beaver.getRandom().nextInt(9) - 4);
            this.targetZ = pos.getZ() + (this.beaver.getRandom().nextInt(20) - 10);

            return true;
        }

        @Override
        public void start() {

            this.beaver.getNavigation().startMovingTo(
                    this.targetX,
                    this.targetY,
                    this.targetZ,
                    1.0D
            );
        }

        @Override
        public boolean shouldContinue() {

            return this.beaver.isTouchingWater()
                    && !this.beaver.getNavigation().isIdle();
        }
    }

    public static DefaultAttributeContainer.Builder createAttributes() {

        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20.0D);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.BEAVER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.BEAVER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.BEAVER_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {

        this.playSound(
                ModSounds.BEAVER_STEP,
                0.1F,
                1.0F
        );
    }

    @Override
    public void tick() {

        super.tick();

        if (this.getWorld().isClient()) {
            this.setupAnimationStates();
        }
    }

    private void setupAnimationStates() {

        if (this.isGnawing()) {

            if (!this.gnawAnimationState.isRunning()) {
                this.gnawAnimationState.start(this.age);
            }

            this.idleAnimationState.stop();
            this.swimAnimationState.stop();

            return;
        }

        this.gnawAnimationState.stop();


        if (this.isTouchingWater()) {

            if (!this.swimAnimationState.isRunning()) {
                this.swimAnimationState.start(this.age);
            }

            this.idleAnimationState.stop();

            return;
        }


        this.swimAnimationState.stop();


        if (this.idleAnimationTimeout <= 0) {

            this.idleAnimationTimeout = 40;

            if (!this.idleAnimationState.isRunning()) {
                this.idleAnimationState.start(this.age);
            }

        } else {

            --this.idleAnimationTimeout;
        }
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ItemTags.SAPLINGS);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.BEAVER.create(world);
    }

    private static class BeaverMoveControl extends MoveControl {

        private final BeaverEntity beaver;

        public BeaverMoveControl(BeaverEntity beaver) {

            super(beaver);

            this.beaver = beaver;
        }

        @Override
        public void tick() {

            if (this.beaver.isTouchingWater()) {

                if (this.beaver.getNavigation().isIdle()) {

                    this.beaver.setVelocity(
                            this.beaver.getVelocity().multiply(0.9D)
                    );

                    return;
                }

                double x = this.targetX - this.beaver.getX();
                double y = this.targetY - this.beaver.getY();
                double z = this.targetZ - this.beaver.getZ();

                double distance = Math.sqrt(
                        x * x + y * y + z * z
                );

                if (distance < 0.001D) {
                    return;
                }

                float targetYaw =
                        (float) (
                                MathHelper.atan2(z, x)
                                        * (180F / Math.PI)
                        ) - 90F;

                this.beaver.setYaw(
                        MathHelper.clamp(
                                MathHelper.wrapDegrees(
                                        targetYaw - this.beaver.getYaw()
                                ),
                                -15F,
                                15F
                        )
                                + this.beaver.getYaw()
                );

                this.beaver.bodyYaw = this.beaver.getYaw();

                float targetPitch =
                        (float) -(
                                MathHelper.atan2(
                                        y,
                                        Math.sqrt(x * x + z * z)
                                )
                                        * (180F / Math.PI)
                        );

                this.beaver.setPitch(
                        MathHelper.clamp(
                                MathHelper.wrapDegrees(
                                        targetPitch - this.beaver.getPitch()
                                ),
                                -45.5F,
                                45.5F
                        )
                                + this.beaver.getPitch()
                );

                double speed =
                        this.speed *
                                this.beaver.getAttributeValue(
                                        EntityAttributes.GENERIC_MOVEMENT_SPEED
                                );

                this.beaver.setVelocity(
                        this.beaver.getVelocity().add(
                                x / distance * 0.1D * speed,
                                y / distance * 0.1D * speed,
                                z / distance * 0.1D * speed
                        )
                );

            } else {

                super.tick();
            }
        }
    }
}