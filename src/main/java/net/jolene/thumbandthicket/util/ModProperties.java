package net.jolene.thumbandthicket.util;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;

public class ModProperties {
    public static final EnumProperty<Rooty> ROOTY = EnumProperty.of("rooty", Rooty.class);
    public static final EnumProperty<Slice> SLICE = EnumProperty.of("slice", Slice.class);
    public static final EnumProperty<TripleTallBlock> TALL_PLANT_PART_VERTICAL = EnumProperty.of("tall_plant_part_vertical", TripleTallBlock.class);
    public static final EnumProperty<TallPlantPartHorizontal> TALL_PLANT_PART_HORIZONTAL = EnumProperty.of("tall_plant_part_horizontal", TallPlantPartHorizontal.class);
    public static final IntProperty AMOUNT = IntProperty.of("amount", 1, 4);
    public static final IntProperty LAYERS = IntProperty.of("layers", 0, 8);
    public static final IntProperty FLOWERS = IntProperty.of("flowers", 1, 3);
    public static final BooleanProperty SNIPPED = BooleanProperty.of("snipped");
    public static final BooleanProperty LAVALOGGED = BooleanProperty.of("lavalogged");
    public static final BooleanProperty TOP = BooleanProperty.of("top");
    public static final BooleanProperty FERTILIZED = BooleanProperty.of("fertilized");
    public static final BooleanProperty GOLDEN = BooleanProperty.of("golden");
    public static final IntProperty LEVEL_3 = IntProperty.of("level", 0, 3);
}
