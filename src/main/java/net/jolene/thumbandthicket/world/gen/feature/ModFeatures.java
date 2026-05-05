package net.jolene.thumbandthicket.world.gen.feature;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.HugeMushroomFeatureConfig;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;

public class ModFeatures {

    public static final Identifier HUGE_PURPLE_MUSHROOM_ID = Identifier.of(MOD_ID, "huge_purple_mushroom_feature");
    public static final HugePurpleMushroomFeature HUGE_PURPLE_MUSHROOM_FEATURE = new HugePurpleMushroomFeature(HugeMushroomFeatureConfig.CODEC);

    public static final Identifier TALL_WATERLOGGED_PLANT_ID = Identifier.of(MOD_ID, "tall_waterlogged_plant_feature");
    public static final HugePurpleMushroomFeature TALL_WATERLOGGED_PLANT_FEATURE = new HugePurpleMushroomFeature(HugeMushroomFeatureConfig.CODEC);
    public static void registerModFeatures() {
        Registry.register(Registries.FEATURE, HUGE_PURPLE_MUSHROOM_ID, HUGE_PURPLE_MUSHROOM_FEATURE);
    }
}
