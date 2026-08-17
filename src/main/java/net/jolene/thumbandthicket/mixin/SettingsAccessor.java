package net.jolene.thumbandthicket.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Function;

@Mixin(AbstractBlock.Settings.class)
public interface SettingsAccessor {
    @Accessor("soundGroup")
    BlockSoundGroup getSoundGroup();

    @Accessor("instrument")
    NoteBlockInstrument getInstrument();

    @Accessor("mapColorProvider")
    Function<BlockState, MapColor> getMapColor();
}