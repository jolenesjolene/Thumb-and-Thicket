package net.jolene.thumbandthicket.util;

import net.minecraft.util.math.BlockPos;

import java.util.List;

public interface GrazingMemoryHolder {
    List<BlockPos> thumbandthicket$getGrazedPositions();

    void thumbandthicket$addGrazedPosition(BlockPos pos);

    boolean thumbandthicket$hasGrazedPosition(BlockPos pos);

    void thumbandthicket$clearGrazedPositions();
}