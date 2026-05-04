package net.jolene.thumbandthicket.world.gen.placementmodifier;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifierType;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;

public class ModPlacementModifierType<P extends PlacementModifier> {

    public static final PlacementModifierType<SnowPlacementModifier> SNOWY_BELOW = Registry.register(Registries.PLACEMENT_MODIFIER_TYPE, Identifier.of(MOD_ID, "snowy_below"), () -> SnowPlacementModifier.MODIFIER_CODEC);
}
