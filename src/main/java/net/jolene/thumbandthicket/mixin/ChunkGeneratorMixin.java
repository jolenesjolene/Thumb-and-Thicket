package net.jolene.thumbandthicket.mixin;

import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
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

    // TRANSITION GENERATION

    @Inject(method = "generateFeatures", at = @At("TAIL"))
    private void thumbandthicket$generateTerrainChanges(
            StructureWorldAccess world,
            Chunk chunk,
            StructureAccessor structureAccessor,
            CallbackInfo ci
    ) {
        if (chunk instanceof ProtoChunk protoChunk) {

            thumbandthicket$convertTerrain(
                    world,
                    protoChunk
            );
        }
    }

    // TERRAIN CONVERSIONS

    @Unique
    private void thumbandthicket$convertTerrain(
            StructureWorldAccess world,
            ProtoChunk chunk
    ) {
        Set<BlockPos> mudCandidates = new HashSet<>();
        Set<BlockPos> wetSandCandidates = new HashSet<>();
        Set<BlockPos> stonyDirtCandidates = new HashSet<>();

        int startX = chunk.getPos().getStartX();
        int startZ = chunk.getPos().getStartZ();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = chunk.getBottomY();
                     y < chunk.getTopY();
                     y++) {

                    BlockPos pos = new BlockPos(
                            startX + x,
                            y,
                            startZ + z
                    );

                    BlockState state =
                            chunk.getBlockState(pos);

                    // WET SAND

                    if (state.isOf(Blocks.SAND)) {

                        if (state.contains(ModProperties.DAMP)) {

                            state = state.with(
                                    ModProperties.DAMP,
                                    false
                            );

                            chunk.setBlockState(
                                    pos,
                                    state,
                                    false
                            );
                        }

                        if (thumbandthicket$shouldBecomeWetSand(
                                world,
                                pos
                        )) {

                            wetSandCandidates.add(
                                    pos.toImmutable()
                            );
                        }
                    }

                    // STONY DIRT

                    if (state.isOf(Blocks.DIRT)
                            && state.contains(ModProperties.STONY)) {

                        if (state.get(ModProperties.STONY)) {

                            state = state.with(
                                    ModProperties.STONY,
                                    false
                            );

                            chunk.setBlockState(
                                    pos,
                                    state,
                                    false
                            );
                        }

                        if (thumbandthicket$touchesStone(
                                chunk,
                                pos
                        )
                                && world.getRandom().nextBoolean()) {

                            stonyDirtCandidates.add(
                                    pos.toImmutable()
                            );
                        }
                    }

                    // DIRT TO MUD

                    if (state.isIn(BlockTags.DIRT)
                            && thumbandthicket$touchesWater(
                            world,
                            pos
                    )) {

                        mudCandidates.add(
                                pos.toImmutable()
                        );

                        thumbandthicket$spreadMud(
                                world,
                                chunk,
                                pos,
                                mudCandidates,
                                3,
                                BlockTags.DIRT
                        );
                    }
                }
            }
        }

        // APPLY MUD

        for (BlockPos pos : mudCandidates) {

            if (chunk.getBlockState(pos)
                    .isIn(BlockTags.DIRT)) {

                chunk.setBlockState(
                        pos,
                        Blocks.MUD.getDefaultState(),
                        false
                );
            }
        }

        // APPLY WET SAND

        for (BlockPos pos : wetSandCandidates) {

            if (chunk.getBlockState(pos)
                    .isOf(Blocks.SAND)) {

                chunk.setBlockState(
                        pos,
                        ModBlocks.WET_SAND.getDefaultState(),
                        false
                );
            }
        }

        // APPLY DAMP SAND

        for (BlockPos wetSandPos : wetSandCandidates) {

            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {

                    if (x == 0 && z == 0) {
                        continue;
                    }

                    BlockPos adjacentPos =
                            wetSandPos.add(
                                    x,
                                    0,
                                    z
                            );

                    BlockState adjacentState =
                            chunk.getBlockState(
                                    adjacentPos
                            );

                    if (adjacentState.isOf(Blocks.SAND)
                            && adjacentState.contains(
                            ModProperties.DAMP
                    )) {

                        chunk.setBlockState(
                                adjacentPos,
                                adjacentState.with(
                                        ModProperties.DAMP,
                                        true
                                ),
                                false
                        );
                    }
                }
            }
        }

        // APPLY STONY DIRT

        for (BlockPos pos : stonyDirtCandidates) {

            BlockState state =
                    chunk.getBlockState(pos);

            if (state.isOf(Blocks.DIRT)
                    && state.contains(ModProperties.STONY)) {

                chunk.setBlockState(
                        pos,
                        state.with(
                                ModProperties.STONY,
                                true
                        ),
                        false
                );
            }
        }
    }

    // SAND TRANSITION RULES

    @Unique
    private boolean thumbandthicket$shouldBecomeWetSand(
            StructureWorldAccess world,
            BlockPos pos
    ) {
        if (world.getFluidState(pos.up())
                .isIn(FluidTags.WATER)
                || world.getFluidState(pos.down())
                .isIn(FluidTags.WATER)) {

            return true;
        }

        int waterCount = 0;

        for (Direction direction :
                Direction.Type.HORIZONTAL) {

            if (world.getFluidState(
                    pos.offset(direction)
            ).isIn(FluidTags.WATER)) {

                waterCount++;
            }
        }

        return waterCount >= 2
                || waterCount == 1
                && world.getRandom().nextFloat() < 0.5f;
    }

    // DIRT TRANSITION RULES

    @Unique
    private boolean thumbandthicket$touchesStone(
            ProtoChunk chunk,
            BlockPos pos
    ) {
        for (Direction direction : Direction.values()) {

            if (chunk.getBlockState(
                    pos.offset(direction)
            ).isOf(Blocks.STONE)) {

                return true;
            }
        }

        return false;
    }

    // MUD SPREADING

    @Unique
    private void thumbandthicket$spreadMud(
            StructureWorldAccess world,
            ProtoChunk chunk,
            BlockPos origin,
            Set<BlockPos> candidates,
            int maxDistance,
            TagKey blockTag
    ) {
        Queue<BlockPos> positions =
                new ArrayDeque<>();

        Queue<Integer> distances =
                new ArrayDeque<>();

        Set<BlockPos> visited =
                new HashSet<>();

        positions.add(origin);
        distances.add(0);
        visited.add(origin);

        while (!positions.isEmpty()) {

            BlockPos current =
                    positions.poll();

            int distance =
                    distances.poll();

            if (distance >= maxDistance) {
                continue;
            }

            for (Direction direction :
                    Direction.Type.HORIZONTAL) {

                BlockPos next =
                        current.offset(direction);

                if (!visited.add(next)
                        || !chunk.getBlockState(next)
                        .isIn(blockTag)) {

                    continue;
                }

                if (world.getRandom().nextFloat()
                        < thumbandthicket$getChance(
                        maxDistance,
                        distance
                )) {

                    candidates.add(
                            next.toImmutable()
                    );

                    positions.add(next);

                    distances.add(
                            distance + 1
                    );
                }
            }
        }
    }

    @Unique
    private static float thumbandthicket$getChance(
            int maxDistance,
            int distance
    ) {
        int nextDistance = distance + 1;

        if (maxDistance == 3) {

            return switch (nextDistance) {
                case 1 -> 0.3f;
                case 2 -> 0.2f;
                case 3 -> 0.15f;
                default -> 0.05f;
            };
        }

        float progress =
                (float) (nextDistance - 1)
                        / (maxDistance - 1);

        return 0.5f - 0.15f * progress;
    }

    // WATER DETECTION

    @Unique
    private boolean thumbandthicket$touchesWater(
            StructureWorldAccess world,
            BlockPos pos
    ) {
        for (Direction direction : Direction.values()) {

            if (world.getFluidState(
                    pos.offset(direction)
            ).isIn(FluidTags.WATER)) {

                return true;
            }
        }

        return false;
    }
}