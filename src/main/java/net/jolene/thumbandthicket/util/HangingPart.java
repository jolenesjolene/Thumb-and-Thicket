package net.jolene.thumbandthicket.util;

import net.minecraft.util.StringIdentifiable;

public enum HangingPart implements StringIdentifiable {
    BASE("base"),
    MIDDLE("middle"),
    TOP("top");


    private final String name;

    HangingPart(
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
