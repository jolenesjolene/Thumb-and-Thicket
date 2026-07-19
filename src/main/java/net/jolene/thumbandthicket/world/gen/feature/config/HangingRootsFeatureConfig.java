package net.jolene.thumbandthicket.world.gen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public record HangingRootsFeatureConfig(int tries, int xzSpread, int ySpread, BlockStateProvider stateProvider) implements FeatureConfig {
    public static final Codec<HangingRootsFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.INT.fieldOf("tries").forGetter(HangingRootsFeatureConfig::tries), Codec.INT.fieldOf("xz_spread").forGetter(HangingRootsFeatureConfig::xzSpread),  Codec.INT.fieldOf("y_spread").forGetter(HangingRootsFeatureConfig::ySpread), BlockStateProvider.TYPE_CODEC.fieldOf("state_provider").forGetter(HangingRootsFeatureConfig::stateProvider)).apply(instance, HangingRootsFeatureConfig::new));
}

