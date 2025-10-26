package net.jolene.thumbandthicket.util;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;

public class ModProperties {
    public static final BooleanProperty ROOTY = BooleanProperty.of("rooty");
    public static final BooleanProperty FRONT = BooleanProperty.of("front");
    public static final EnumProperty<Slice> SLICE = EnumProperty.of("slice", Slice.class);
}
