package net.jolene.thumbandthicket.world.gen.surface;

import com.google.common.collect.ImmutableList;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import net.minecraft.world.gen.surfacebuilder.VanillaSurfaceRules;

public class ModSurfaceRules {
    private static MaterialRules.MaterialRule PERFMAFROST = block(ModBlocks.PERMAFROST);
    private static MaterialRules.MaterialRule DIRT = block(Blocks.DIRT);

    private static MaterialRules.MaterialRule block(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }

    public static MaterialRules.MaterialRule createOverworldSurfaceRule() {
        return createDefaultRule(true, false, true);
    }

    public static MaterialRules.MaterialRule createDefaultRule(boolean surface, boolean bedrockRoof, boolean bedrockFloor) {

        MaterialRules.MaterialRule materialRule = MaterialRules.sequence(MaterialRules.condition(MaterialRules.water(0, 0), PERFMAFROST), DIRT);


        ImmutableList.Builder builder = ImmutableList.builder();
        return MaterialRules.sequence((MaterialRules.MaterialRule[])builder.build().toArray(MaterialRules.MaterialRule[]::new));
    }
}
