package net.jolene.thumbandthicket.util;

import net.minecraft.util.StringIdentifiable;

public enum TripleTallBlock implements StringIdentifiable {
    BOTTOM("bottom"),
    MIDDLE("middle"),
    TOP("top");


    private final String name;

    TripleTallBlock(
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
