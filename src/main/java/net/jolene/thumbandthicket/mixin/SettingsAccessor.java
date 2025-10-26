package net.jolene.thumbandthicket.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractBlock.Settings.class)
public interface SettingsAccessor {
    @Accessor("soundGroup")
    BlockSoundGroup getSoundGroup();

    @Accessor("instrument")
    NoteBlockInstrument getInstrument();
}