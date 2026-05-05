package net.jolene.thumbandthicket.world.gen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public record TallWaterloggedPlantFeatureConfig(int tries, int xzSpread, int ySpread, BlockStateProvider stateProvider) implements FeatureConfig {
    public static final Codec<TallWaterloggedPlantFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.INT.fieldOf("tries").forGetter(TallWaterloggedPlantFeatureConfig::tries), Codec.INT.fieldOf("xz_spread").forGetter(TallWaterloggedPlantFeatureConfig::xzSpread),  Codec.INT.fieldOf("y_spread").forGetter(TallWaterloggedPlantFeatureConfig::ySpread), BlockStateProvider.TYPE_CODEC.fieldOf("to_place").forGetter(TallWaterloggedPlantFeatureConfig::stateProvider)).apply(instance, TallWaterloggedPlantFeatureConfig::new));}
