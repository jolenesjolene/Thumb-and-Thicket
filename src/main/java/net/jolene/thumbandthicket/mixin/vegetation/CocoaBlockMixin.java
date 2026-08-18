package net.jolene.thumbandthicket.mixin.vegetation;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CocoaBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CocoaBlock.class)
public class CocoaBlockMixin extends Block {

    public CocoaBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return Items.JUNGLE_SAPLING.getDefaultStack();
    }
}
