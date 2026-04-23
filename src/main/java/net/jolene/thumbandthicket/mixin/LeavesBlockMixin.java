package net.jolene.thumbandthicket.mixin;

import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin extends Block {

    public LeavesBlockMixin(Settings settings) {
        super(settings);
        super.setDefaultState(this.getDefaultState().with(ModProperties.TOP, true));
    }

    @Inject(method = "getStateForNeighborUpdate", at = @At("RETURN"), cancellable = true)
    private void thumbandthicket$checkIfTop(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
        BlockState state1 = world.getBlockState(pos.up());
        if (!state1.isOf(Blocks.AIR)) {
            cir.setReturnValue(state.with(ModProperties.TOP, false));
        }
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void thumbandthicket$addTop(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(ModProperties.TOP);
    }

    @Inject(method = "getPlacementState", at = @At("RETURN"), cancellable = true)
    private void thumbandthicket$addTop(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
        World world = ctx.getWorld();
        BlockPos pos = ctx.getBlockPos();
        BlockState state = world.getBlockState(pos.up());
        if (!state.isOf(Blocks.AIR)) {
            cir.setReturnValue(cir.getReturnValue().with(ModProperties.TOP, false));
        }
    }
}
