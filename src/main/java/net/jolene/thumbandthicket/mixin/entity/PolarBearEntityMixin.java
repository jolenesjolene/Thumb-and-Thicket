package net.jolene.thumbandthicket.mixin.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PolarBearEntity.class)
public abstract class PolarBearEntityMixin extends AnimalEntity {
    protected PolarBearEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("TAIL"))
    private void thumbandthicket$addNestGoal(CallbackInfo ci) {
        super.initGoals();
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new TemptGoal(this, 1.1, stack -> stack.isIn(ItemTags.FISHES), false));
    }

    @ModifyReturnValue(method = "isBreedingItem", at = @At("RETURN"))
    private boolean thumbandthicket$polarBearBreedingStack(boolean original, ItemStack stack) {
        return stack.isIn(ItemTags.FISHES);
    }
}
