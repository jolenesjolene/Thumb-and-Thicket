package net.jolene.thumbandthicket.mixin.entity;

import net.jolene.thumbandthicket.util.GrazingMemoryHolder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.LinkedList;
import java.util.List;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin extends MobEntity implements GrazingMemoryHolder {

    @Unique
    private final LinkedList<BlockPos> thumbandthicket$grazedPositions = new LinkedList<>();
    @Unique
    private boolean hasGrazed = false;

    protected AnimalEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public List<BlockPos> thumbandthicket$getGrazedPositions() {
        return thumbandthicket$grazedPositions;
    }

    @Override
    public void thumbandthicket$addGrazedPosition(BlockPos pos) {
        thumbandthicket$grazedPositions.add(pos.toImmutable());
        if (thumbandthicket$grazedPositions.size() > 16) thumbandthicket$clearGrazedPositions();
    }

    @Override
    public boolean thumbandthicket$hasGrazedPosition(BlockPos pos) {
        return thumbandthicket$grazedPositions.contains(pos);
    }

    @Override
    public void thumbandthicket$clearGrazedPositions() {
        thumbandthicket$grazedPositions.clear();
    }

    @Override
    public boolean thumbandthicket$hasGrazed() {
        return hasGrazed;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeGrazingData(NbtCompound nbt, CallbackInfo ci) {
        NbtList list = new NbtList();

        for (BlockPos pos : thumbandthicket$grazedPositions) {
            NbtCompound posNbt = new NbtCompound();

            posNbt.putInt("x", pos.getX());
            posNbt.putInt("y", pos.getY());
            posNbt.putInt("z", pos.getZ());

            list.add(posNbt);
        }

        nbt.putBoolean("canBreed", hasGrazed);
        nbt.put("GrazedPositions", list);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readGrazingData(NbtCompound nbt, CallbackInfo ci) {
        thumbandthicket$grazedPositions.clear();

        if (!nbt.contains("GrazedPositions")) return;
        NbtList list = nbt.getList("GrazedPositions", NbtElement.COMPOUND_TYPE);

        for (int i = 0; i < list.size(); i++) {
            NbtCompound posNbt = list.getCompound(i);
            thumbandthicket$grazedPositions.add(new BlockPos(posNbt.getInt("x"), posNbt.getInt("y"), posNbt.getInt("z"))
            );
        }
        hasGrazed = nbt.getBoolean("canBreed");
    }

    @Override
    public void onEatingGrass() {
        AnimalEntity entity = (AnimalEntity) (Object)this;
        ((GrazingMemoryHolder)entity).thumbandthicket$addGrazedPosition(entity.getBlockPos().down());
        hasGrazed = true;
        this.emitGameEvent(GameEvent.EAT);

        if (entity.getHealth() < entity.getMaxHealth()) {
            entity.heal(2.0F);
        }
    }

    @Inject(method = "canBreedWith", at = @At(value = "RETURN"), cancellable = true)
    private void thumbandthicket$haveBothGrazed(AnimalEntity other, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue()) {
            AnimalEntity entity = (AnimalEntity) (Object)this;
            GrazingMemoryHolder holder = (GrazingMemoryHolder) entity;
            GrazingMemoryHolder otherHolder = (GrazingMemoryHolder) other;

            if (!holder.thumbandthicket$hasGrazed() || !otherHolder.thumbandthicket$hasGrazed()) cir.setReturnValue(false);
        }
    }

    @Inject(method = "breed(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/AnimalEntity;)V", at = @At("TAIL"))
    private void thumbandthicket$setHasGrazed(ServerWorld world, AnimalEntity other, CallbackInfo ci) {
        hasGrazed = false;
    }

    @Inject(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/AnimalEntity;isBreedingItem(Lnet/minecraft/item/ItemStack;)Z", shift = At.Shift.AFTER), cancellable = true)
    private void thumbandthicket$canBeBread(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (!hasGrazed) cir.setReturnValue(ActionResult.FAIL);
    }
}
