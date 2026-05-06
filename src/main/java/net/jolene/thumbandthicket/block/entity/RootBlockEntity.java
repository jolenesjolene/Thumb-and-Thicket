package net.jolene.thumbandthicket.block.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static net.jolene.thumbandthicket.ThumbAndThicket.thumbandthicket$getBlockByName;
import static net.jolene.thumbandthicket.block.entity.ModBlockEntities.ROOT_BLOCK_ENTITY;

public class RootBlockEntity extends BlockEntity {

    private Block SAPLING = Blocks.AIR;

    public RootBlockEntity(BlockPos pos, BlockState state) {
        super(ROOT_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putString("StoredSapling", SAPLING.toString());
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        SAPLING = thumbandthicket$getBlockByName(nbt.getString("StoredSapling"));
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

    public void tick(World world, BlockPos pos, BlockState state) {
        if (SAPLING == Blocks.AIR) {
            BlockState state1 = world.getBlockState(pos.up());
            if (state1.isIn(BlockTags.LOGS)) {
                String sapling = Registries.BLOCK.getId(state1.getBlock()).getPath().replace("_log", "_sapling");
                if (sapling.contains("stripped_")) sapling = sapling.replace("stripped_", "");

                SAPLING = thumbandthicket$getBlockByName(sapling);
            }
        }
    }

    public Block thumbandthicket$getSapling() {
        return SAPLING;
    }
}
