package net.jolene.thumbandthicket.util;

import net.minecraft.util.StringIdentifiable;

public enum Slice implements StringIdentifiable {
    TOP_LEFT("top_left"),
    TOP_RIGHT("top_right"),
    LOWER_LEFT("lower_left"),
    LOWER_RIGHT("lower_right"),
    NONE("none");


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
