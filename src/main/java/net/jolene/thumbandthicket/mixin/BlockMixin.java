package net.jolene.thumbandthicket.mixin;

import com.blackgear.vanillabackport.common.level.blocks.CactusFlowerBlock;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.util.Rooty;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.jolene.thumbandthicket.util.ModProperties.*;
import static net.minecraft.block.PillarBlock.AXIS;
import static net.minecraft.state.property.Properties.*;

@Mixin(Block.class)
public abstract class BlockMixin {

    @Shadow private BlockState defaultState;

    @Shadow
    public abstract BlockState getDefaultState();

    @Inject(method = "getPlacementState", at = @At("HEAD"), cancellable = true)
    private void modifyPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        Block block = (Block)(Object)this;
        BlockState state = block.getDefaultState();
        if (state.contains(ROOTY) && state.contains(ROOTY)) {
            if (state.get(AXIS).isVertical()) {
                Block blockDown = ctx.getWorld().getBlockState(ctx.getBlockPos().down()).getBlock();
                Block blockUp = ctx.getWorld().getBlockState(ctx.getBlockPos().up()).getBlock();
                if (blockDown.equals(ModBlocks.ROOT_BLOCK)) cir.setReturnValue(defaultState.with(ROOTY, Rooty.TOP));
                if (blockUp.equals(ModBlocks.ROOT_BLOCK)) cir.setReturnValue(defaultState.with(ROOTY, Rooty.BOTTOM));
            }
        }
        if (block instanceof FarmlandBlock) cir.setReturnValue(state.with(FERTILIZED, false));
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void appendLogProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci){
        Block block = (Block) (Object) this;
    }

    @WrapMethod(method = "getDefaultState")
    private BlockState progressionrespun$changeDefaultState(Operation<BlockState> original) {
        Block block = (Block)(Object)this;
        if (block instanceof FarmlandBlock) {
            return original.call().with(FERTILIZED, false);
        }
        return original.call();
    }
}
