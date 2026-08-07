package net.jolene.thumbandthicket.util;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.Map;
import java.util.stream.Stream;

import net.minecraft.block.BlockSetType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public record ModBlockSetType(String name, boolean canOpenByHand, boolean canOpenByWindCharge, boolean canButtonBeActivatedByArrows, ModBlockSetType.ActivationRule pressurePlateSensitivity, BlockSoundGroup soundType, SoundEvent doorClose, SoundEvent doorOpen, SoundEvent trapdoorClose, SoundEvent trapdoorOpen, SoundEvent pressurePlateClickOff, SoundEvent pressurePlateClickOn, SoundEvent buttonClickOff, SoundEvent buttonClickOn) {
    private static final Map<String, BlockSetType> VALUES = new Object2ObjectArrayMap<>();
    public static final Codec<BlockSetType> CODEC = Codec.stringResolver(BlockSetType::name, VALUES::get);
    public static final BlockSetType FRUIT = register(new BlockSetType("fruit"));

    public ModBlockSetType(String name) {
        this(name, true, true, true, ModBlockSetType.ActivationRule.EVERYTHING, BlockSoundGroup.WOOD, SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE, SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_OFF, SoundEvents.BLOCK_WOODEN_PRESSURE_PLATE_CLICK_ON, SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_OFF, SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON);
    }

    private static BlockSetType register(BlockSetType blockSetType) {
        VALUES.put(blockSetType.name(), blockSetType);
        return blockSetType;
    }

    public static Stream<BlockSetType> stream() {
        return VALUES.values().stream();
    }

    public enum ActivationRule {
        EVERYTHING,
        MOBS

    }
}


