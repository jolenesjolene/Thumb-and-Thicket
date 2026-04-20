package net.jolene.thumbandthicket.util;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;

public class ModProperties {
    public static final EnumProperty<Rooty> ROOTY = EnumProperty.of("rooty", Rooty.class);
    public static final EnumProperty<Slice> SLICE = EnumProperty.of("slice", Slice.class);
    public static final IntProperty AMOUNT = IntProperty.of("amount", 1, 4);
    public static final IntProperty LAYERS = IntProperty.of("layers", 0, 8);
    public static final BooleanProperty SNIPPED = BooleanProperty.of("snipped");
    public static final BooleanProperty LAVALOGGED = BooleanProperty.of("lavalogged");
}
