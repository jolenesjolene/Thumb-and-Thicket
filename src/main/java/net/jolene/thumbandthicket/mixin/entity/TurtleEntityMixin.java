package net.jolene.thumbandthicket.mixin.entity;

import net.jolene.thumbandthicket.block.ModBlocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ArmadilloEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TurtleEntity.class)
public abstract class TurtleEntityMixin extends AnimalEntity {


    @Unique
    private static final TrackedData<Boolean> BARNACLES = DataTracker.registerData(TurtleEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    protected TurtleEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void thumbandthicket$writeCoatTwo(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("Barnacles", getBarnacles());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void thumbandthicket$readCoatTwo(NbtCompound nbt, CallbackInfo ci) {
        setBarnacles(nbt.getBoolean("Barnacles"));
    }

    @Unique
    private void setBarnacles(boolean bool) {
        this.dataTracker.set(BARNACLES, bool);
    }

    @Unique
    private boolean getBarnacles() {
        return this.dataTracker.get(BARNACLES);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private static void thumbandthicket$initSecondCoat(DataTracker.Builder builder, CallbackInfo ci) {
        builder.add(BARNACLES, false);
    }

    @Inject(method = "initialize", at = @At("HEAD"))
    private void thumbandthicket$setCoatTwo(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, CallbackInfoReturnable<EntityData> cir) {
        TurtleEntity turtleEntity = (TurtleEntity) (Object)(this);
        if (turtleEntity.getRandom().nextInt(10) == 0) setBarnacles(true);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.BRUSH) && this.getBarnacles()) {
            dropItem(ModBlocks.BARNACLES);
            if (getRandom().nextInt(5) == 0) dropItem(Items.TURTLE_SCUTE);
            itemStack.damage(8, player, TurtleEntity.getSlotForHand(hand));
            this.setBarnacles(false);
            return ActionResult.success(this.getWorld().isClient);
        }
        return super.interactMob(player, hand);
    }
}
