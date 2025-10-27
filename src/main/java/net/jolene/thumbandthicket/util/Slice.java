package net.jolene.thumbandthicket.util;

import net.minecraft.util.StringIdentifiable;

public enum Slice implements StringIdentifiable {
    ZERO("0"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4");


    private final String name;

    Slice(
            final String name
    ) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
