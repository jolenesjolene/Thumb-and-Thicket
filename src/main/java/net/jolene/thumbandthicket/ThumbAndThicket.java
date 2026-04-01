package net.jolene.thumbandthicket;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.jolene.thumbandthicket.block.entity.ModBlockEntities;
import net.jolene.thumbandthicket.block.ModBlocks;
import net.jolene.thumbandthicket.item.ModItemGroups;
import net.jolene.thumbandthicket.item.ModItems;
import net.jolene.thumbandthicket.util.Rooty;
import net.jolene.thumbandthicket.util.Slice;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.jolene.thumbandthicket.util.ModProperties.ROOTY;
import static net.jolene.thumbandthicket.util.ModProperties.SLICE;
import static net.minecraft.block.PillarBlock.AXIS;

public class ThumbAndThicket implements ModInitializer {
	public static final String MOD_ID = "thumbandthicket";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModBlocks.registerModBlocks();
        ModItems.initialize();
        ModBlockEntities.register();
        thumbandthicket$registerResourcePacks();
		LOGGER.info("Muddy!");
	}


	public static Identifier id(String name) {
		return Identifier.of(MOD_ID, name);
	}

    public static @NotNull Direction thumbandthicket$getInvertedDirection(Direction rootBlockDirection) {
        Direction rootBlockDirectionInverted = rootBlockDirection;

        switch (rootBlockDirection) {
            case UP -> rootBlockDirectionInverted = Direction.DOWN;
            case DOWN -> rootBlockDirectionInverted = Direction.UP;
            case WEST -> rootBlockDirectionInverted = Direction.EAST;
            case EAST -> rootBlockDirectionInverted = Direction.WEST;
            case NORTH -> rootBlockDirectionInverted = Direction.SOUTH;
            case SOUTH -> rootBlockDirectionInverted = Direction.NORTH;
        }
        return rootBlockDirectionInverted;
    }

    public static Direction thumbandthicket$determineRootBlockDirection(BlockState state, BlockPos pos, WorldAccess world, Block block) {
        Direction.Axis axis = state.get(AXIS);

        if (axis.isVertical()) {
            BlockState below = world.getBlockState(pos.down());
            BlockState above = world.getBlockState(pos.up());
            if (below.getBlock() == block && below.get(AXIS) == axis) return Direction.DOWN;
            if (above.getBlock() == block && above.get(AXIS) == axis) return Direction.UP;
        } else if (axis == Direction.Axis.X) {
            BlockState west = world.getBlockState(pos.west());
            BlockState east = world.getBlockState(pos.east());
            if (west.getBlock() == block && west.get(AXIS) == axis) return Direction.WEST;
            if (east.getBlock() == block && east.get(AXIS) == axis) return Direction.EAST;
        } else {
            BlockState north = world.getBlockState(pos.north());
            BlockState south = world.getBlockState(pos.south());
            if (north.getBlock() == block && north.get(AXIS) == axis) return Direction.NORTH;
            if (south.getBlock() == block && south.get(AXIS) == axis) return Direction.SOUTH;
        }
        return null;
    }

    public static BlockState thumbandthicket$determineRootSide(BlockState state, WorldAccess world, BlockPos pos) {

        Direction rootBlockDirection = thumbandthicket$determineRootBlockDirection(state, pos, world, ModBlocks.ROOT_BLOCK);
        if (!state.contains(ROOTY) || rootBlockDirection == null) return state;

        switch (rootBlockDirection) {
            case DOWN, WEST, NORTH -> {
                if (world.getBlockState(pos.offset(rootBlockDirection)).getBlock() == ModBlocks.ROOT_BLOCK) return state.with(ROOTY, Rooty.BOTTOM);
            }
            case UP, EAST, SOUTH -> {
                if (world.getBlockState(pos.offset(rootBlockDirection)).getBlock() == ModBlocks.ROOT_BLOCK) return state.with(ROOTY, Rooty.TOP);
            }
        }

        return state;
    }

    public static BlockState thumbandthicket$calculateSlice(BlockState state, WorldAccess world, BlockPos pos) {
        Direction.Axis axis = state.get(AXIS);
        Block block = state.getBlock();

        Direction[] dirs;
        if (axis == Direction.Axis.Y) dirs = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
        else if (axis == Direction.Axis.X) dirs = new Direction[]{Direction.UP, Direction.SOUTH, Direction.DOWN, Direction.NORTH};
        else dirs = new Direction[]{Direction.UP, Direction.EAST, Direction.DOWN, Direction.WEST};

        boolean[] connected = new boolean[4];
        for (int i = 0; i < dirs.length; i++) {
            BlockState neighbor = world.getBlockState(pos.offset(dirs[i]));
            connected[i] = neighbor.getBlock() == block && neighbor.contains(ROOTY) && neighbor.get(ROOTY) == state.get(ROOTY) && neighbor.get(AXIS) == axis;
        }

        if (connected[0] && connected[1]) return state.with(SLICE, Slice.TWO);
        if (connected[1] && connected[2]) return state.with(SLICE, Slice.THREE);
        if (connected[2] && connected[3]) return state.with(SLICE, Slice.FOUR);
        if (connected[3] && connected[0]) return state.with(SLICE, Slice.ONE);

        return state;
    }

    public static BlockState thumbandthicket$inheritSlice(BlockState state, WorldAccess world, BlockPos pos) {

        Direction.Axis axis = state.get(AXIS);
        Block block = state.getBlock();

        Direction rootBlockDirection = thumbandthicket$determineRootBlockDirection(state, pos, world, state.getBlock());

        Direction dir1, dir2;
        if (rootBlockDirection != null) {
            if (world.getBlockState(pos.offset(rootBlockDirection)).contains(AXIS)) {
                if (axis == world.getBlockState(pos.offset(rootBlockDirection)).get(AXIS)) {
                    dir1 = thumbandthicket$getInvertedDirection(rootBlockDirection);
                    dir2 = rootBlockDirection;
                    BlockPos neighborPos1 = pos.offset(dir1);
                    BlockPos neighborPos2 = pos.offset(dir2);

                    for (BlockPos neighborPos : new BlockPos[]{neighborPos1, neighborPos2}) {
                        BlockState neighbor = world.getBlockState(neighborPos);
                        if (neighbor.getBlock() == block && neighbor.contains(SLICE) && neighbor.get(AXIS) == axis) {
                            return state.with(SLICE, neighbor.get(SLICE));
                        }
                    }
                }
            }
        }
        return state;
    }

    public static void thumbandthicket$registerResourcePacks() {
        ModContainer modContainer = FabricLoader.getInstance()
                .getModContainer(MOD_ID)
                .orElseThrow(() -> new IllegalStateException("Missing mod modContainer"));

        ResourceManagerHelper.registerBuiltinResourcePack(
                Identifier.of(MOD_ID, "tat_wood"), modContainer,
                Text.translatable("pack.thumbandthicket.wood_states"),
                ResourcePackActivationType.ALWAYS_ENABLED
        );
    }

    public static Item thumbandthicket$getItemByName(String name) {
        for (Item item : Registries.ITEM) {
            Identifier id = Registries.ITEM.getId(item);
            if (id.getPath().equals(name)) {
                return item;
            }
        }
        return Items.AIR;
    }

    public static Block thumbandthicket$getBlockByName(String name) {
        for (Block block : Registries.BLOCK) {
            Identifier id = Registries.BLOCK.getId(block);
            if (id.getPath().equals(name)) {
                return block;
            }
        }
        return Blocks.AIR;
    }
}