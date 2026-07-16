package net.jolene.thumbandthicket.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorMixin {

    @Inject(method = "generateFeatures", at = @At("TAIL"))
    private void replaceDirtWithMud(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor, CallbackInfo ci) {
        if (!(chunk instanceof ProtoChunk protoChunk)) return;
        convertWetDirt(world, protoChunk);
    }

    @Unique
    private void convertWetDirt(StructureWorldAccess world, ProtoChunk protoChunk) {
        Set<BlockPos> candidates = new HashSet<>();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = protoChunk.getBottomY(); y < protoChunk.getTopY(); y++) {
                    BlockPos pos = new BlockPos(protoChunk.getPos().getStartX() + x, y, protoChunk.getPos().getStartZ() + z);

                    if (protoChunk.getBlockState(pos).isIn(BlockTags.DIRT) && touchesWater(world, pos)) {
                        candidates.add(pos);
                        spreadMud(world, protoChunk, pos, candidates);
                    }
                }
            }
        }

        for (BlockPos pos : candidates) {
            if (protoChunk.getBlockState(pos).isIn(BlockTags.DIRT)) protoChunk.setBlockState(pos, Blocks.MUD.getDefaultState(), false);
        }
    }

    @Unique
    private void spreadMud(StructureWorldAccess world, ProtoChunk chunk, BlockPos origin, Set<BlockPos> candidates) {
        Queue<BlockPos> blockPosQueue = new ArrayDeque<>();
        Queue<Integer> distancesQueue = new ArrayDeque<>();

        Set<BlockPos> visited = new HashSet<>();

        blockPosQueue.add(origin);
        distancesQueue.add(0);
        visited.add(origin);

        while (!blockPosQueue.isEmpty()) {
            BlockPos current = blockPosQueue.poll();
            int distance = distancesQueue.poll();

            if (distance >= 3) continue;

            for (Direction dir : Direction.Type.HORIZONTAL) {
                BlockPos next = current.offset(dir);

                if (visited.contains(next)) continue;
                visited.add(next);

                if (!chunk.getBlockState(next).isIn(BlockTags.DIRT)) continue;

                float chance = switch (distance + 1) {
                    case 1 -> 0.3f;
                    case 2 -> 0.2f;
                    case 3 -> 0.15f;
                    default -> 0.05f;
                };

                if (world.getRandom().nextFloat() < chance) {
                    candidates.add(next);
                    blockPosQueue.add(next);
                    distancesQueue.add(distance + 1);
                }
            }
        }
    }

    @Unique
    private boolean touchesWater(StructureWorldAccess world, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            if (world.getFluidState(pos.offset(dir)).isIn(FluidTags.WATER)) {
                return true;
            }
        }
        return false;
    }


}
