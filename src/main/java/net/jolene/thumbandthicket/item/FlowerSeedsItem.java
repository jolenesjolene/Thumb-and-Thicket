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

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ActionResult actionResult = this.place(new ItemPlacementContext(context));
        if (!actionResult.isAccepted() && context.getStack().contains(DataComponentTypes.FOOD)) {
            ActionResult actionResult2 = super.use(context.getWorld(), context.getPlayer(), context.getHand()).getResult();
            return actionResult2 == ActionResult.CONSUME ? ActionResult.CONSUME_PARTIAL : actionResult2;
        }
        return actionResult;
    }
}
