package net.jolene.thumbandthicket.world.gen.feature;

import com.mojang.serialization.Codec;
import net.jolene.thumbandthicket.world.gen.feature.config.TallWaterloggedPlantFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public class TallWaterloggedPlantFeature extends Feature<TallWaterloggedPlantFeatureConfig> {
    public TallWaterloggedPlantFeature(Codec<TallWaterloggedPlantFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<TallWaterloggedPlantFeatureConfig> context) {
        TallWaterloggedPlantFeatureConfig tallWaterloggedPlantFeatureConfig = context.getConfig();
        int tries = tallWaterloggedPlantFeatureConfig.tries();
        int xzSpread = tallWaterloggedPlantFeatureConfig.xzSpread();
        int ySpread = tallWaterloggedPlantFeatureConfig.ySpread();
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();

        BlockPos pos = context.getOrigin();
        int posX = pos.getX();
        int posY = pos.getY();
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        world.getTopY(Heightmap.Type.OCEAN_FLOOR, posX, posY);

        BlockState state = tallWaterloggedPlantFeatureConfig.stateProvider().get(context.getRandom(), pos);
        int j = xzSpread + 1;
        int k = ySpread + 1;

        int i = 0;
        for (int l = 0; l < tallWaterloggedPlantFeatureConfig.tries(); ++l) {
            mutable.set(pos, random.nextInt(j) - random.nextInt(j), random.nextInt(k) - random.nextInt(k), random.nextInt(j) - random.nextInt(j));
            if (!state.canPlaceAt(world, pos)) continue;
            if (state.getBlock() instanceof TallPlantBlock) {
                if (!world.isAir(pos.up())) continue;
                TallPlantBlock.placeAt(world, state, pos, 2);
                ++i;
            }
        }

        return i>0;
    }
}
