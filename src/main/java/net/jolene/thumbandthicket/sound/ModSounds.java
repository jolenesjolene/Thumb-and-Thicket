package net.jolene.thumbandthicket.sound;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent SNIP = registerSoundEvent("snip");

    public static final SoundEvent BEAVER_AMBIENT = registerSoundEvent("entity.beaver.ambient");
    public static final SoundEvent BEAVER_HURT = registerSoundEvent("entity.beaver.hurt");
    public static final SoundEvent BEAVER_STEP = registerSoundEvent("entity.beaver.step");
    public static final SoundEvent BEAVER_DEATH= registerSoundEvent("entity.beaver.death");

    public static final SoundEvent BROWN_BEAR_AMBIENT = registerSoundEvent("entity.brown_bear.ambient");
    public static final SoundEvent BROWN_BEAR_HURT = registerSoundEvent("entity.brown_bear.hurt");
    public static final SoundEvent BROWN_BEAR_STEP = registerSoundEvent("entity.brown_bear.step");
    public static final SoundEvent BROWN_BEAR_DEATH= registerSoundEvent("entity.brown_bear.death");
    public static final SoundEvent BROWN_BEAR_ATTACK= registerSoundEvent("entity.brown_bear.attack");

    public static final SoundEvent MOOSE_AMBIENT = registerSoundEvent("entity.moose.ambient");
    public static final SoundEvent MOOSE_HURT = registerSoundEvent("entity.moose.hurt");
    public static final SoundEvent MOOSE_STEP = registerSoundEvent("entity.moose.step");
    public static final SoundEvent MOOSE_DEATH= registerSoundEvent("entity.moose.death");
    public static final SoundEvent MOOSE_ATTACK= registerSoundEvent("entity.moose.attack");

    public static final SoundEvent LORAX_AMBIENT = registerSoundEvent("entity.lorax.ambient");
    public static final SoundEvent LORAX_HURT = registerSoundEvent("entity.lorax.hurt");
    public static final SoundEvent LORAX_FLY = registerSoundEvent("entity.lorax.fly");
    public static final SoundEvent LORAX_DEATH= registerSoundEvent("entity.lorax.death");
    public static final SoundEvent LORAX_ATTACK= registerSoundEvent("entity.lorax.attack");



    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(ThumbAndThicket.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerModSounds() {
        ThumbAndThicket.LOGGER.info("Registering Sounds for " + ThumbAndThicket.MOD_ID);
    }
}
