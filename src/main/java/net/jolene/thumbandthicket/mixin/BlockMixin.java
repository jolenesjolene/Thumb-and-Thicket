package net.jolene.thumbandthicket.mixin;

import com.blackgear.vanillabackport.common.level.blocks.CactusFlowerBlock;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.util.Rooty;
import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
        if (this instanceof Waterloggable && state.contains(LAVALOGGED)) {
            cir.setReturnValue (defaultState.with(LAVALOGGED, false));
        }
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void appendLogProperties(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci){
        Block block = (Block) (Object) this;
        if (block instanceof MushroomPlantBlock) builder.add(BLOCK_FACE).add(FACING).add(AMOUNT);
        if (block instanceof FungusBlock) builder.add(BLOCK_FACE).add(FACING).add(AMOUNT);
        if (block instanceof CactusFlowerBlock) builder.add(AGE_2);
    }
}
