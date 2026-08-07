package net.jolene.thumbandthicket.world.gen.feature;

import com.mojang.serialization.Codec;
import net.jolene.thumbandthicket.world.gen.feature.config.HangingRootsFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class HangingRootsFeature extends Feature<HangingRootsFeatureConfig> {
    public HangingRootsFeature(Codec<HangingRootsFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<HangingRootsFeatureConfig> context) {

        return false;
    }
}
