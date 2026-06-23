package net.jolene.thumbandthicket.mixin.entity;

import net.jolene.thumbandthicket.util.GrazingMemoryHolder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.LinkedList;
import java.util.List;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin extends MobEntity implements GrazingMemoryHolder {

    @Unique
    private final LinkedList<BlockPos> thumbandthicket$grazedPositions = new LinkedList<>();

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
        if (thumbandthicket$grazedPositions.size() > 30) thumbandthicket$clearGrazedPositions();
    }

    @Override
    public boolean thumbandthicket$hasGrazedPosition(BlockPos pos) {
        return thumbandthicket$grazedPositions.contains(pos);
    }

    @Override
    public void thumbandthicket$clearGrazedPositions() {
        thumbandthicket$grazedPositions.clear();
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
    }

    @Override
    public void onEatingGrass() {
        AnimalEntity entity = (AnimalEntity) (Object)this;
        ((GrazingMemoryHolder)entity).thumbandthicket$addGrazedPosition(entity.getBlockPos().down());
        this.emitGameEvent(GameEvent.EAT);
    }


}
