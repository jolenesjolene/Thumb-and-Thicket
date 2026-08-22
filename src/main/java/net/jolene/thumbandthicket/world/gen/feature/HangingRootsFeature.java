package net.jolene.thumbandthicket.world.gen.feature;

import com.mojang.serialization.Codec;
import net.jolene.thumbandthicket.util.HangingPart;
import net.jolene.thumbandthicket.util.ModProperties;
import net.jolene.thumbandthicket.world.gen.feature.config.HangingRootsFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.CaveSurface;
import net.minecraft.world.gen.feature.util.DripstoneHelper;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Optional;

public class HangingRootsFeature extends Feature<HangingRootsFeatureConfig> {
    public HangingRootsFeature(Codec<HangingRootsFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<HangingRootsFeatureConfig> context) {
        HangingRootsFeatureConfig config = context.getConfig();
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        BlockPos origin = context.getOrigin();
        BlockState state = config.stateProvider().get(random, origin);
        boolean longer = false;
        boolean evenLonger = false;

        int placed = 0;
        int tries = config.tries();
        int ceilingHeight = config.ceilingHeight();

        for (int i = 0; i < tries; i++) {
            double angle = random.nextDouble() * Math.PI * 2;
            double radius = random.nextDouble();
            radius = radius * radius;

            int spread = config.xzSpread();
            int dx = (int) (Math.cos(angle) * radius * spread);
            int dz = (int) (Math.sin(angle) * radius * spread);

            BlockPos pos = origin.add(dx, 0, dz);
            Optional<CaveSurface> caveSurface = CaveSurface.create(world, pos, ceilingHeight, DripstoneHelper::canGenerate, DripstoneHelper::cannotGenerate);

            if (world.getBlockState(pos.up()).isAir()) continue;
            if (!world.getBlockState(pos.down()).isAir()) continue;

            if (random.nextBetween(0,10) == 0) {
                longer = true;
                state = state.with(ModProperties.HANGING_PART, HangingPart.BASE);
                if (random.nextBetween(0,10) == 0) {
                    evenLonger = true;
                }
            }

            caveSurface.ifPresent(surface -> pos.add(0, surface.getCeilingHeight().getAsInt(), 0));

            if (!state.canPlaceAt(world, pos)) continue;
            world.setBlockState(pos, state,2);
            if (longer) {
                if (evenLonger) {
                    world.setBlockState(pos.down(), state.with(ModProperties.HANGING_PART, HangingPart.MIDDLE), 2);
                    world.setBlockState(pos.down(2), state.with(ModProperties.HANGING_PART, HangingPart.TOP), 2);
                }
                else world.setBlockState(pos.down(), state.with(ModProperties.HANGING_PART, HangingPart.TOP), 2);
            }
            placed++;
        }

        return placed > 0;
    }
}
