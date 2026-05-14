package net.jolene.thumbandthicket.world.gen.treedecorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jolene.thumbandthicket.util.ModProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

public class WallMushroomDecorator extends TreeDecorator {

    public static final MapCodec<WallMushroomDecorator> CODEC = RecordCodecBuilder.mapCodec(wallMushroomDecoratorInstance -> wallMushroomDecoratorInstance.group(Codec.FLOAT.fieldOf("probability").forGetter(mod -> mod.probability), BlockStateProvider.TYPE_CODEC.fieldOf("state_provider").forGetter(mod -> mod.stateProvider)).apply(wallMushroomDecoratorInstance, WallMushroomDecorator::new));
    private final float probability;
    private final BlockStateProvider stateProvider;


    public WallMushroomDecorator (float probability, BlockStateProvider stateProvider) {
        this.probability = probability;
        this.stateProvider = stateProvider;
    }

    @Override
    protected TreeDecoratorType<?> getType() {
        return ModTreeDecorators.WALL_MUSHROOM_DECORATOR_TREE_DECORATOR_TYPE;
    }

    @Override
    public void generate(Generator generator) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int i = 1; i <= 4; ++i) {
            if (generator.getRandom().nextFloat() > probability) continue;
            for (Direction direction : Direction.Type.HORIZONTAL) {
                BlockState state = stateProvider.get(generator.getRandom(), mutable).with(ModProperties.AMOUNT, i).with(Properties.FACING, direction).with(Properties.BLOCK_FACE, BlockFace.WALL);
                if (!generator.isAir(mutable.offset(direction, 1))) continue;
                generator.replace(mutable, state);
            }
        }
    }
}
