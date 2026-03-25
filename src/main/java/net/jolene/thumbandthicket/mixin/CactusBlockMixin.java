package net.jolene.thumbandthicket.mixin;

import com.blackgear.vanillabackport.common.registries.ModBlocks;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.item.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CactusBlock.class)
public class CactusBlockMixin {

    @WrapMethod(method = "onEntityCollision")
    private void gay(BlockState state, World world, BlockPos pos, Entity entity, Operation<Void> original) {
        if (entity instanceof ItemEntity item && (item.getStack().isOf(ModItems.PRICKLY_PEAR) || item.getStack().isOf(ModBlocks.CACTUS_FLOWER.get().asItem()) || item.getStack().isOf(Blocks.CACTUS.asItem()))) {
            return;
        }
        original.call(state, world, pos, entity);
    }
}
