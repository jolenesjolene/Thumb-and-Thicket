package net.jolene.thumbandthicket.mixin.farming;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.block.WiltedCropBlock;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;

import static net.jolene.thumbandthicket.ThumbAndThicket.WITHERED_CROPS;
import static net.jolene.thumbandthicket.util.ModProperties.FERTILIZED;

@Debug(export = true)
@Mixin(CropBlock.class)
public abstract class CropBlockMixin {

    @Shadow
    public abstract int getAge(BlockState state);

    @Shadow
    protected static float getAvailableMoisture(Block block, BlockView world, BlockPos pos) {
        return 0;
    }

    @Shadow
    public abstract BlockState withAge(int age);

    @Shadow
    @Final
    public static IntProperty AGE;

    @Unique
    private float thumbandthicket$getWiltingChancePerAge(int age) {
        return switch (age) {
            case 2 -> 0.1f;
            case 3 -> 0.125f;
            case 4 -> 0.15f;
            case 5 -> 0.175f;
            case 6 -> 0.2f;
            case 7 -> 0.25f;
            default -> 0;
        };
    }

    @Unique
    private float getWitherChancePerWitheredCrop(World world, BlockPos pos) {
        float multiplier = 0.5f;
        for (BlockPos blockPos : WITHERED_CROPS) {
            if ((world.getBlockState(pos.add(blockPos)).getBlock() instanceof WiltedCropBlock)) {
                multiplier += 0.25f;
            }
        }
        return multiplier;
    }

    @WrapMethod(method = "randomTick")
    private void thumbandthicket$getMaxAgeWilting(BlockState state, ServerWorld world, BlockPos pos, Random random, Operation<Void> original) {
        BlockState farmland = world.getBlockState(pos.down());
        if (farmland.getBlock() instanceof FarmlandBlock && !farmland.get(FERTILIZED)) {
            if (world.getBaseLightLevel(pos, 0) >= 9) {
                int i = this.getAge(state);
                float f = getAvailableMoisture((Block) (Object) this, world, pos);
                if (random.nextInt((int) (25.0F / f) + 1) == 0) {
                    float multiplier = getWitherChancePerWitheredCrop(world, pos);
                    if (thumbandthicket$getWiltingChancePerAge(i) > 0 && random.nextFloat() < thumbandthicket$getWiltingChancePerAge(i) * multiplier) {
                        world.setBlockState(pos, ModBlocks.WILTED_CROP.getDefaultState(), Block.NOTIFY_LISTENERS);
                    } else {
                        world.setBlockState(pos, this.withAge(i + 1), Block.NOTIFY_LISTENERS);
                    }
                }
            }
        } else {
            original.call(state, world, pos, random);
        }
    }
}
