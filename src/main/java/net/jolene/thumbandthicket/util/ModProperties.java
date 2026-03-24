package net.jolene.thumbandthicket.util;

import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;

public class ModProperties {
    public static final EnumProperty<Rooty> ROOTY = EnumProperty.of("rooty", Rooty.class);
    public static final EnumProperty<Slice> SLICE = EnumProperty.of("slice", Slice.class);
    public static final IntProperty AMOUNT = IntProperty.of("amount", 1, 4);
}
