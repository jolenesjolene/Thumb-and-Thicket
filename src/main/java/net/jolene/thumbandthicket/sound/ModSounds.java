package net.jolene.thumbandthicket.sound;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {
    public static final SoundEvent SNIP = registerSoundEvent("snip");


    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(ThumbAndThicket.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerModSounds() {
        ThumbAndThicket.LOGGER.info("Registering Sounds for " + ThumbAndThicket.MOD_ID);
    }
}
