package net.jolene.thumbandthicket.util;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;

public class ModProperties {
    public static final BooleanProperty ROOTY = BooleanProperty.of("rooty");
    public static final EnumProperty<Side> SIDE = EnumProperty.of("side", Side.class);
    public static final EnumProperty<Slice> SLICE = EnumProperty.of("slice", Slice.class);
}
