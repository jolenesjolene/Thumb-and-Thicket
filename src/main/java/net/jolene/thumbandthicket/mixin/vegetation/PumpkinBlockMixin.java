package net.jolene.thumbandthicket.mixin.vegetation;

import net.jolene.thumbandthicket.mixin.BlockAccessor;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PumpkinBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.block.FacingBlock.FACING;

@Mixin(PumpkinBlock.class)
public class PumpkinBlockMixin extends Block {

    public PumpkinBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void thumbandthicket$appendSnippedProperty(AbstractBlock.Settings settings, CallbackInfo ci) {
        Block pumpkinBlock = PumpkinBlock.class.cast(this);
        BlockState defaultBlockState = pumpkinBlock.getDefaultState();
        ((BlockAccessor) pumpkinBlock).invokeSetDefaultState(defaultBlockState.with(FACING, Direction.UP));
    }
}
