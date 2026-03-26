package net.jolene.thumbandthicket.effect;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {
        public static final RegistryEntry<StatusEffect> PRICKLY = registerStatusEffect("prickly",
            new PricklyEffect(StatusEffectCategory.BENEFICIAL, 0x36ebab));


    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
            return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(ThumbAndThicket.MOD_ID, name), statusEffect);
        }

        public static void registerEffects() {
            ThumbAndThicket.LOGGER.info("Registering Effects for" + ThumbAndThicket.MOD_ID);
        }
    }