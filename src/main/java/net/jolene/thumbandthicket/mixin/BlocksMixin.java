package net.jolene.thumbandthicket.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.jolene.thumbandthicket.block.MelonBlock;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Blocks.class)
public class BlocksMixin {

    @WrapOperation(method = "<clinit>", at = @At(value = "NEW", target = "(Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/Block;", ordinal = 58))
    private static Block gay(AbstractBlock.Settings settings, Operation<Block> original) {
        return new MelonBlock(AbstractBlock.Settings.create().mapColor(MapColor.LIME).strength(1.0f).sounds(BlockSoundGroup.WOOD).pistonBehavior(PistonBehavior.DESTROY));
    }
}
