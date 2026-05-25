package net.jolene.thumbandthicket.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BrineFluidBlock extends FluidBlock {
    public BrineFluidBlock(FlowableFluid fluid, Settings settings) {
        super(fluid, settings);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof ItemEntity)) {
            entity.damage(world.getDamageSources().dryOut(), 1);
        }
        super.onEntityCollision(state, world, pos, entity);
    }
}
