package net.jolene.thumbandthicket.mixin.entity;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.jolene.thumbandthicket.util.CoatTwoUtil;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SheepEntity.class)
public abstract class SheepEntityMixin extends MobEntity implements CoatTwoUtil {

    @Unique
    private static boolean hasCoatTwo = false;

    protected SheepEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean thumbandthicket$hasCoatTwo() {
        return hasCoatTwo;
    }

    @Inject(method = "onEatingGrass", at = @At("HEAD"))
    public void onEatingGrass(CallbackInfo ci) {
        SheepEntity sheepEntity = (SheepEntity)(Object)(this);
        if (!sheepEntity.isSheared() && canHaveSecondCoat(sheepEntity)){
            hasCoatTwo = true;
        }
    }

    @Unique
    private boolean canHaveSecondCoat(SheepEntity sheepEntity) {
        World world = sheepEntity.getWorld();
        BlockPos pos = sheepEntity.getBlockPos();
        return world.getBiome(pos).isIn(ConventionalBiomeTags.IS_COLD);
    }

    @Inject(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", shift = At.Shift.AFTER))
    private void thumbandthicket$setCoatFalse(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (hasCoatTwo) hasCoatTwo = false;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void thumbandthicket$writeCoatTwo(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("wool_second_layer", hasCoatTwo);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void thumbandthicket$readCoatTwo(NbtCompound nbt, CallbackInfo ci) {
        hasCoatTwo = nbt.getBoolean("Sheared");
    }

    @Inject(method = "initialize", at = @At("HEAD"))
    private void thumbandthicket$setCoatTwo(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, CallbackInfoReturnable<EntityData> cir) {
        SheepEntity sheepEntity = (SheepEntity)(Object)(this);
        if (canHaveSecondCoat(sheepEntity)) {
            hasCoatTwo = true;
        }
    }

    @ModifyVariable(method = "sheared(Lnet/minecraft/sound/SoundCategory;)V", at = @At(value = "STORE"), ordinal = 0)
    private int modifyWoolDropCount(int originalAmount) {
        if (hasCoatTwo) return 2 + Random.create().nextBetween(1,3);
        return 1 + Random.create().nextBetween(1,2);
    }
}
