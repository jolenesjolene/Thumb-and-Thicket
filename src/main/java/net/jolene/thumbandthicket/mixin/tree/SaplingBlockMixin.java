package net.jolene.thumbandthicket.mixin.tree;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import static net.jolene.thumbandthicket.util.ModProperties.SNIPPED;

@Mixin(SaplingBlock.class)
public class SaplingBlockMixin extends Block {

    @Unique private static final IntProperty AGE = Properties.AGE_3;

    @Shadow @Final protected SaplingGenerator generator;

    @Shadow @Final public static IntProperty STAGE;

    @Shadow @Final protected static VoxelShape SHAPE;

    @Unique private static final VoxelShape SHAPE_SMALL = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);
    @Unique private static final VoxelShape SHAPE_TALL = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 22.0, 14.0);

    protected SaplingBlockMixin(Settings settings) {
        super(settings);
        super.setDefaultState(this.getDefaultState().with(SNIPPED, false).with(AGE, 0).with(STAGE, 0));
    }

    @WrapMethod(method = "getOutlineShape")
    private VoxelShape thumbandthicket$outlineWithAge(BlockState state, BlockView world, BlockPos pos, ShapeContext context, Operation<VoxelShape> original) {
        if (state.contains(AGE)) {
            int age = state.get(AGE);
            switch (age) {
                case 0 -> {
                    return SHAPE_SMALL;
                }
                case 1, 2 -> {
                    return SHAPE;
                }
                case 3 -> {
                    return SHAPE_TALL;
                }
            }
        }
        return original.call(state, world, pos, context);
    }

    @WrapMethod(method = "generate")
    public void thumbandthicket$generateWithAge(ServerWorld world, BlockPos pos, BlockState state, Random random, Operation<Void> original) {
        if (state.get(AGE) <= 2) {
            world.setBlockState(pos, state.cycle(AGE), Block.NOTIFY_NEIGHBORS);
        } else {
            this.generator.generate(world, world.getChunkManager().getChunkGenerator(), pos, state, random);
        }
    }

    @WrapMethod(method = "appendProperties")
    private void thumbandthicket$appendProperties(StateManager.Builder<Block, BlockState> builder, Operation<Void> original) {
        super.appendProperties(builder);
        builder.add(AGE);
        builder.add(STAGE);
    }

    @Override
    protected float getMaxHorizontalModelOffset() {
        return 2f;
    }
}
