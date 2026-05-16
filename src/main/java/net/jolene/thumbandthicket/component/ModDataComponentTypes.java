package net.jolene.thumbandthicket.component;

import com.mojang.serialization.Codec;
import net.jolene.thumbandthicket.ThumbAndThicket;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final ComponentType<String> FLOWER_TYPE = register("flower_type", builder -> builder.codec(Codec.STRING));

    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(ThumbAndThicket.MOD_ID, name), builderOperator.apply(ComponentType.builder()).build());
    }

    public static void registerDataComponentTypes() {
    }
}