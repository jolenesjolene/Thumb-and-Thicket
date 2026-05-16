package net.jolene.thumbandthicket.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

import static net.jolene.thumbandthicket.block.entity.ModBlockEntities.FLOWER_CROP_BLOCK;

public class FlowerCropBlockEntity extends BlockEntity {

    private String FLOWER = "";

    public FlowerCropBlockEntity(BlockPos pos, BlockState state) {
        super(FLOWER_CROP_BLOCK, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putString("StoredFlower", FLOWER.toString());
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        FLOWER = nbt.getString("StoredFlower");
        super.readNbt(nbt, registryLookup);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }

    public String thumbandthicket$getFlower() {
        return FLOWER;
    }

    public void thumbandthicket$setFlower(String string) {
        FLOWER = string;
    }
}
