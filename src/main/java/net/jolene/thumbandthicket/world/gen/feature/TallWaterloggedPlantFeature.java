package net.jolene.thumbandthicket.world.gen.feature;

import com.mojang.serialization.Codec;
import net.jolene.thumbandthicket.world.gen.feature.config.TallWaterloggedPlantFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class TallWaterloggedPlantFeature extends Feature<TallWaterloggedPlantFeatureConfig> {
    public TallWaterloggedPlantFeature(Codec<TallWaterloggedPlantFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<TallWaterloggedPlantFeatureConfig> context) {
        TallWaterloggedPlantFeatureConfig config = context.getConfig();
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        BlockPos origin = context.getOrigin();
        BlockState state = config.stateProvider().get(random, origin);

        int placed = 0;
        int tries = config.tries();

        for (int i = 0; i < tries; i++) {
            double angle = random.nextDouble() * Math.PI * 2;
            double radius = random.nextDouble();
            radius = radius * radius;

            int spread = config.xzSpread();
            int dx = (int) (Math.cos(angle) * radius * spread);
            int dz = (int) (Math.sin(angle) * radius * spread);

            BlockPos pos = origin.add(dx, 0, dz);
            pos = new BlockPos(pos.getX(), world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, pos.getX(), pos.getZ()), pos.getZ());

            boolean shallowWater = world.getBlockState(pos).isOf(Blocks.WATER) && state.canPlaceAt(world, pos);

            if (!shallowWater && !world.getBlockState(pos).isAir()) continue;

            boolean waterNearby = false;
            for (BlockPos currentPos : BlockPos.iterate(pos.add(2, 2, 2), pos.add(-2, -2, -2))) if (BlockPredicate.matchingFluids(Fluids.WATER).test(world, currentPos)) waterNearby = true;
            if (!waterNearby) continue;

            if (!world.isAir(pos.up())) continue;
            if (!state.canPlaceAt(world, pos.mutableCopy())) continue;

            TallPlantBlock.placeAt(world, state, pos.mutableCopy(), 2);

            placed++;
        }

        return placed > 0;
    }
}
