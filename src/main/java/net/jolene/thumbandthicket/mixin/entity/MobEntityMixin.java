package net.jolene.thumbandthicket.mixin.entity;

import net.jolene.thumbandthicket.entity.custom.goals.StayInShelterGoal;
import net.jolene.thumbandthicket.entity.custom.goals.SeekShelterGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {

    @Shadow
    @Final
    protected GoalSelector goalSelector;

    @Inject(method = "initGoals", at = @At("HEAD"))
    private void thumbandthicket$addShelterGoals(CallbackInfo ci) {
        if ((Object) this instanceof AnimalEntity animalEntity) {
            if (!(animalEntity instanceof AxolotlEntity)){
                this.goalSelector.add(1, new StayInShelterGoal(animalEntity));
                this.goalSelector.add(2, new SeekShelterGoal(animalEntity, 1));
            }
        }
    }
}
