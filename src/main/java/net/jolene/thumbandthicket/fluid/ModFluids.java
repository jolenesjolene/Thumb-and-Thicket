package net.jolene.thumbandthicket.fluid;

import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static net.jolene.thumbandthicket.ThumbAndThicket.MOD_ID;

public class ModFluids {
    public static final FlowableFluid BRINE_SOURCE = Registry.register(Registries.FLUID, Identifier.of(MOD_ID, "brine"), new BrineFluid.Still());
    public static final FlowableFluid FLOWING_BRINE =  Registry.register(Registries.FLUID, Identifier.of(MOD_ID, "flowing_brine"), new BrineFluid.Flowing());
}
