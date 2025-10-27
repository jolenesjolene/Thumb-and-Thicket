package net.jolene.thumbandthicket.util;

import net.minecraft.util.StringIdentifiable;

public enum Rooty implements StringIdentifiable {
    TOP("top"),
    BOTTOM("bottom"),
    NONE("none");


    private final String name;

    Rooty(
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
