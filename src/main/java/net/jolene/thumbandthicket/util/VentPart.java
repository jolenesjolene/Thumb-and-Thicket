package net.jolene.thumbandthicket.util;

import net.minecraft.util.StringIdentifiable;

public enum VentPart implements StringIdentifiable {
    BASE("base"),
    TOP("top");


    private final String name;

    VentPart(
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
