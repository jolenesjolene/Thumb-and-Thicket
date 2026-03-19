package net.jolene.thumbandthicket.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.PlantBlock;

public class PuffedDandelionBlock extends PlantBlock {
    protected PuffedDandelionBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return createCodec(PuffedDandelionBlock::new);
    }
}
