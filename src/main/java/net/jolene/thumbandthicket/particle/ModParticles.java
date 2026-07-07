package net.jolene.thumbandthicket.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.jolene.thumbandthicket.ThumbAndThicket;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {
    public static final SimpleParticleType PUFF =
            registerParticle("lavender", FabricParticleTypes.simple());

    private static SimpleParticleType registerParticle(String name, SimpleParticleType particleType) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(ThumbAndThicket.MOD_ID, name), particleType);
    }

    public static void registerModParticles() {
        ThumbAndThicket.LOGGER.info("Registering Particles for " + ThumbAndThicket.MOD_ID);
    }
}