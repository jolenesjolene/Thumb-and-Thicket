package net.jolene.thumbandthicket.util;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.Map;
import java.util.stream.Stream;

public record ModWoodType(String name, BlockSetType setType, BlockSoundGroup soundType, BlockSoundGroup hangingSignSoundType, SoundEvent fenceGateClose, SoundEvent fenceGateOpen) {

        private static final Map<String, WoodType> VALUES = new Object2ObjectArrayMap<>();
        public static final Codec<WoodType> CODEC = Codec.stringResolver(WoodType::name, VALUES::get);
        public static final WoodType FRUIT = ModWoodType.register(new WoodType("fruit", ModBlockSetType.FRUIT));
        public static final WoodType AZALEA = ModWoodType.register(new WoodType("azalea", ModBlockSetType.AZALEA));

        public ModWoodType(String name, BlockSetType setType) {
            this(name, setType, BlockSoundGroup.WOOD, BlockSoundGroup.HANGING_SIGN, SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundEvents.BLOCK_FENCE_GATE_OPEN);
        }

        private static WoodType register(WoodType type) {
            VALUES.put(type.name(), type);
            return type;
        }

        public static Stream<WoodType> stream() {
            return VALUES.values().stream();
        }
}
