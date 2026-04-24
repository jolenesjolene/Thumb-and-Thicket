package net.jolene.thumbandthicket.world.gen.placementmodifier;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.FeaturePlacementContext;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

import java.util.stream.Stream;

public class SnowPlacementModifier extends PlacementModifier {
    @Override
    public Stream<BlockPos> getPositions(FeaturePlacementContext context, Random random, BlockPos pos) {
        return Stream.empty();
    }

    @Override
    public PlacementModifierType<?> getType() {
        return null;
    }
}
