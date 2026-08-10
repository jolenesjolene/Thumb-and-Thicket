package net.jolene.thumbandthicket.item;
import net.minecraft.item.Item;
import net.minecraft.resource.featuretoggle.FeatureSet;

import static net.jolene.thumbandthicket.ThumbAndThicket.isModLoaded;

public class ChiselItem extends Item {
    public ChiselItem(Settings settings) {
        super(settings);
    }


    @Override
    public boolean isEnabled(FeatureSet enabledFeatures) {
        return !isModLoaded("progression_respun");
    }
}
