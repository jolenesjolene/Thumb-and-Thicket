package net.jolene.thumbandthicket.util;

import net.minecraft.util.StringIdentifiable;

public enum Side implements StringIdentifiable {
    TOP("top"),
    BOTTOM("bottom"),
    NONE("none");


    private final String name;

    Side(
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
