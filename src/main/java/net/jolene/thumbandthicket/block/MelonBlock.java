package net.jolene.thumbandthicket.block;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.BiFunction;

import static net.jolene.thumbandthicket.util.ModProperties.BITES;

public class MelonBlock extends FacingBlock {
    public static final MapCodec<MelonBlock> CODEC = MelonBlock.createCodec(MelonBlock::new);

    public MelonBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(Properties.FACING, Direction.UP).with(BITES,0));
    }

    @Override
    protected MapCodec<? extends FacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.FACING).add(BITES);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            if (tryEat(world, pos, state, player).isAccepted()) {
                return ActionResult.SUCCESS;
            }
            if (player.getStackInHand(Hand.MAIN_HAND).isEmpty()) {
                return ActionResult.CONSUME;
            }
        }
        return tryEat(world, pos, state, player);
    }

    protected static ActionResult tryEat(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (player.getStackInHand(Hand.MAIN_HAND).isIn(TagKey.of(RegistryKeys.ITEM, Identifier.of("farmersdelight", "knives")))) {
            dropStack(world, pos, new ItemStack(Items.MELON_SLICE));
            return ActionResult.SUCCESS;
        }
        if (!player.canConsume(false)) {
            return ActionResult.PASS;
        }
        player.incrementStat(Stats.EAT_CAKE_SLICE);
        player.getHungerManager().add(2, 0.3f);
        int i = state.get(BITES);
        world.emitGameEvent(player, GameEvent.EAT, pos);
        if (i < 3) {
            world.setBlockState(pos, state.with(BITES, i + 1), Block.NOTIFY_ALL);
        } else {
            world.removeBlock(pos, false);
            world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return FACING_AND_BITES_TO_SHAPE.apply(state, state.get(BITES));
    }

    @Unique
    private static final BiFunction<BlockState, Integer, VoxelShape> FACING_AND_BITES_TO_SHAPE = Util.memoize((state, amount) -> {
        VoxelShape voxelShape = VoxelShapes.empty();
        VoxelShape[] voxelShapes;

        VoxelShape[] voxelShapesUp = new VoxelShape[]{Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 16.0, 8.0), Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 16.0, 8.0), Block.createCuboidShape(0.0, 0.0, 8.0, 8.0, 16.0, 16.0), Block.createCuboidShape(8.0, 0.0, 8.0, 16.0, 16.0, 16.0)};
        VoxelShape[] voxelShapesNorth = new VoxelShape[]{Block.createCuboidShape(8.0, 8.0, 0.0, 16.0, 16.0, 16.0), Block.createCuboidShape(0.0, 8.0, 0.0, 8.0, 16.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 8.0, 16.0), Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 8.0, 16.0)};
        VoxelShape[] voxelShapesEast = new VoxelShape[]{Block.createCuboidShape(0.0, 8.0, 8.0, 16.0, 16.0, 16.0), Block.createCuboidShape(0.0, 8.0, 0.0, 16.0, 16.0, 8.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 8.0), Block.createCuboidShape(0.0, 0.0, 8.0, 16.0, 8.0, 16.0)};
        VoxelShape[] voxelShapesSouth = new VoxelShape[]{Block.createCuboidShape(0.0, 8.0, 0.0, 8.0, 16.0, 16.0), Block.createCuboidShape(8.0, 8.0, 0.0, 16.0, 16.0, 16.0), Block.createCuboidShape(8.0, 0.0, 0.0, 16.0, 8.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 8.0, 8.0, 16.0)};
        VoxelShape[] voxelShapesWest = new VoxelShape[]{Block.createCuboidShape(0.0, 8.0, 0.0, 16.0, 16.0, 8.0), Block.createCuboidShape(0.0, 8.0, 8.0, 16.0, 16.0, 16.0), Block.createCuboidShape(0.0, 0.0, 8.0, 16.0, 8.0, 16.0), Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 8.0)};

        Direction facing = state.get(FACING);
        switch (facing) {
            case EAST -> voxelShapes = voxelShapesEast;
            case SOUTH -> voxelShapes = voxelShapesSouth;
            case WEST -> voxelShapes = voxelShapesWest;
            case NORTH -> voxelShapes = voxelShapesNorth;
            default -> voxelShapes = voxelShapesUp;
        }

        for (int i = amount; i < 4; ++i) {
            voxelShape = VoxelShapes.union(voxelShape, voxelShapes[i]);
        }

        return voxelShape;
    });

}
