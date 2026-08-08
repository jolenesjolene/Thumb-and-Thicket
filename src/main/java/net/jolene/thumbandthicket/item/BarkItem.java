package net.jolene.thumbandthicket.item;

import net.minecraft.item.Item;
import net.minecraft.resource.featuretoggle.FeatureSet;

import static net.jolene.thumbandthicket.ThumbAndThicket.isModLoaded;

public class BarkItem extends Item {
    public BarkItem(Settings settings) {
        super(settings);
    }
//
    @Override
    public boolean isEnabled(FeatureSet enabledFeatures) {
        return !isModLoaded("farmersdelight");
    }
}
