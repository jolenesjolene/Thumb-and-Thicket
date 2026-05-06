package net.jolene.thumbandthicket.sound;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent SNIP = registerSoundEvent("snip");
    public static final SoundEvent BROWN_BEAR_AMBIENT = registerSoundEvent("entity.brown_bear.ambient");
    public static final SoundEvent BROWN_BEAR_HURT = registerSoundEvent("entity.brown_bear.hurt");
    public static final SoundEvent BROWN_BEAR_STEP = registerSoundEvent("entity.brown_bear.step");
    public static final SoundEvent BROWN_BEAR_DEATH= registerSoundEvent("entity.brown_bear.death");
    public static final SoundEvent BROWN_BEAR_ATTACK= registerSoundEvent("entity.brown_bear.attack");



    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(ThumbAndThicket.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerModSounds() {
        ThumbAndThicket.LOGGER.info("Registering Sounds for " + ThumbAndThicket.MOD_ID);
    }
}
