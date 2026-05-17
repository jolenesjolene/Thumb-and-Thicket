package net.jolene.thumbandthicket.item;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.component.ModDataComponentTypes;
import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class FlowerSeedsItem extends AliasedBlockItem {

    public FlowerSeedsItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        String string = stack.get(ModDataComponentTypes.FLOWER_TYPE);
        if (string != null) {
            return ThumbAndThicket.thumbandthicket$getBlockByName(string).getName().append(" ").append(Text.translatable("item.thumbandthicket.seeds"));
        }
        return super.getName(stack);
    }
}
