package net.jolene.thumbandthicket.world.gen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;

public record WaterloggedPlantFeatureConfig(int tries, int xzSpread, int ySpread, BlockStateProvider stateProvider) implements FeatureConfig {
    public static final Codec<WaterloggedPlantFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(Codec.INT.fieldOf("tries").forGetter(WaterloggedPlantFeatureConfig::tries), Codec.INT.fieldOf("xz_spread").forGetter(WaterloggedPlantFeatureConfig::xzSpread),  Codec.INT.fieldOf("y_spread").forGetter(WaterloggedPlantFeatureConfig::ySpread), BlockStateProvider.TYPE_CODEC.fieldOf("state_provider").forGetter(WaterloggedPlantFeatureConfig::stateProvider)).apply(instance, WaterloggedPlantFeatureConfig::new));}
