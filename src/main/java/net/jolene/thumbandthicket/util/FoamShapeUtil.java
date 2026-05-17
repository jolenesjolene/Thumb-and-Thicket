package net.jolene.thumbandthicket.util;

import net.jolene.thumbandthicket.ThumbAndThicket;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static net.minecraft.util.math.Direction.Type.HORIZONTAL;

public class FoamShapeUtil {
    @Unique
    private static final List<FoamShapeUtil> SETS = new ArrayList<>();

    @Unique
    private static final FoamShapeUtil
            NORTH = createSpriteSet("block/foam/foam_n"),
            EAST = createSpriteSet("block/foam/foam_e"),
            SOUTH = createSpriteSet("block/foam/foam_s"),
            WEST = createSpriteSet("block/foam/foam_w"),
            SOUTH_WEST = createSpriteSet("block/foam/foam_sw"),
            SOUTH_EAST = createSpriteSet("block/foam/foam_se"),
            NORTH_WEST = createSpriteSet("block/foam/foam_nw"),
            NORTH_EAST = createSpriteSet("block/foam/foam_ne"),
            NORTH_SOUTH = createSpriteSet("block/foam/foam_ns"),
            EAST_WEST = createSpriteSet("block/foam/foam_ew"),
            WEST_NORTH_EAST = createSpriteSet("block/foam/foam_wne"),
            NORTH_EAST_SOUTH = createSpriteSet("block/foam/foam_nes"),
            EAST_SOUTH_WEST = createSpriteSet("block/foam/foam_esw"),
            SOUTH_WEST_NORTH = createSpriteSet("block/foam/foam_swn"),
            ALL = createSpriteSet("block/foam/foam_all"),
            DISCONNECTED = createSpriteSet("block/foam/foam_disconnected");

    @Unique public final Sprite[] sprites = new Sprite[3];
    @Unique public final Identifier identifier;

    public FoamShapeUtil(Identifier identifier) {
        this.identifier = identifier;
        SETS.add(this);
    }

    @Unique
    private static FoamShapeUtil createSpriteSet(String path) {
        return new FoamShapeUtil(Identifier.of(ThumbAndThicket.MOD_ID, path));
    }

    @Unique
    public static void populateSpriteSetArrays(SpriteAtlasTexture textureAtlas) {
        for (FoamShapeUtil set : SETS) {
            set.sprites[0] = textureAtlas.getSprite(set.identifier);
        }
    }

    @Unique
    public static FoamShapeUtil getSpriteSet(BlockRenderView blockRenderView, BlockPos blockPos) {
        if (blockPos == null || blockRenderView == null)
            return DISCONNECTED;

        return Objects.requireNonNullElse(getConnected(blockRenderView, blockPos), getDisconnected(blockRenderView, blockPos));
    }

    @Unique
    @Nullable
    private static FoamShapeUtil getConnected(BlockRenderView blockRenderView, BlockPos blockPos) {
        if (areAllNeighborsNonFluid(blockRenderView, blockPos)) return ALL;

        boolean northNonFluid = !hasConnectibleNeighbor(blockRenderView, blockPos, Direction.NORTH);
        boolean southNonFluid = !hasConnectibleNeighbor(blockRenderView, blockPos, Direction.SOUTH);
        boolean eastNonFluid = !hasConnectibleNeighbor(blockRenderView, blockPos, Direction.EAST);
        boolean westNonFluid = !hasConnectibleNeighbor(blockRenderView, blockPos, Direction.WEST);

        FoamShapeUtil spriteSet = null;

        if (northNonFluid) {
            spriteSet = NORTH;
            if (eastNonFluid) {
                spriteSet = NORTH_EAST;
                if (westNonFluid) return WEST_NORTH_EAST;
            }
            else if (westNonFluid) {
                spriteSet = NORTH_WEST;
                if (southNonFluid) return SOUTH_WEST_NORTH;
            }

            if (southNonFluid) {
                spriteSet = NORTH_SOUTH;
                if (eastNonFluid) return NORTH_EAST_SOUTH;
            }
            return spriteSet;
        }
        else if (eastNonFluid) {
            spriteSet = EAST;
            if (southNonFluid) {
                spriteSet = SOUTH_EAST;
                if (westNonFluid) return EAST_SOUTH_WEST;
            }
            else if (westNonFluid) spriteSet = EAST_WEST;
        }
        else if (southNonFluid) {
            spriteSet = SOUTH;
            if (westNonFluid) return SOUTH_WEST;
        }
        else if (westNonFluid) spriteSet = WEST;


        return spriteSet;
    }

    @Unique
    private static FoamShapeUtil getDisconnected(BlockRenderView blockRenderView, BlockPos blockPos) {
        return DISCONNECTED;
    }

    @Unique
    private static boolean areAllNeighborsNonFluid(BlockRenderView blockRenderView, BlockPos pos) {
        for (Direction direction : HORIZONTAL) {
            if (hasConnectibleNeighbor(blockRenderView, pos, direction)) return false;
        }
        return true;
    }

    @Unique
    private static boolean hasConnectibleNeighbor(BlockRenderView blockRenderView, BlockPos pos, Direction dir) {
        BlockPos blockPos = pos.offset(dir);
        FluidState fluidState = blockRenderView.getFluidState(blockPos);

        return fluidState.isIn(FluidTags.WATER);
    }
}
