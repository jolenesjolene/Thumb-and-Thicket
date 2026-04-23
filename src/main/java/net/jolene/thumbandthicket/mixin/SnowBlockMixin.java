package net.jolene.thumbandthicket.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.block.ShortSnowyPlantBlock;
import net.jolene.thumbandthicket.block.TallSnowyPlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SnowBlock.class)
public class SnowBlockMixin {
    @WrapMethod(method = "canReplace")
    private boolean thumbandthicket$canBeReplacedBySnowyPlants(BlockState state, ItemPlacementContext context, Operation<Boolean> original) {
        if (context.getStack().getItem() instanceof BlockItem blockItem) {
            if (blockItem.getBlock() instanceof ShortSnowyPlantBlock || blockItem.getBlock() instanceof TallSnowyPlantBlock) return true;
        }
        return original.call(state, context);
    }
}
