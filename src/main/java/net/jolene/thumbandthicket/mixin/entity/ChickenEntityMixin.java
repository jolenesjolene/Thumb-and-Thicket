package net.jolene.thumbandthicket.mixin.entity;

import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.entity.custom.goals.FindNestGoal;
import net.jolene.thumbandthicket.util.EggLayInterface;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin extends AnimalEntity implements EggLayInterface {

    @Shadow
    public int eggLayTime;

    protected ChickenEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/ChickenEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V", shift = At.Shift.BEFORE), cancellable = true)
    private void thumbandthicket$placeEgg(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "initGoals", at = @At("TAIL"))
    private void thumbandthicket$addNestGoal(CallbackInfo ci) {
        this.goalSelector.add(0, new FindNestGoal(ChickenEntity.class.cast(this), ModBlocks.CHICKEN_EGG_BLOCK));
        super.initGoals();
    }

    @Override
    public boolean thumbAndThicket$isReadyToLay() {
        return this.eggLayTime <= 0;
    }

    @Override
    public void thumbAndThicket$resetEggLayTime() {
        ChickenEntity chicken = (ChickenEntity) (Object) this;
        this.eggLayTime = chicken.getRandom().nextInt(6000) + 6000;
    }
}
