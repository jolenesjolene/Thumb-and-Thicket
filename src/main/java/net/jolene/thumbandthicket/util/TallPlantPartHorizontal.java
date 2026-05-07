package net.jolene.thumbandthicket.util;

import net.minecraft.util.StringIdentifiable;

public enum TallPlantPartHorizontal implements StringIdentifiable {
    LEFT("left"),
    RIGHT("right");


    private final String name;

    TallPlantPartHorizontal(
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
