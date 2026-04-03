package net.jolene.thumbandthicket.block;

import com.mojang.serialization.MapCodec;
import net.jolene.thumbandthicket.block.entity.RootBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static net.jolene.thumbandthicket.block.entity.ModBlockEntities.ROOT_BLOCK_ENTITY;

public class RootBlock extends BlockWithEntity implements BlockEntityProvider {

    public static final MapCodec<RootBlock> CODEC = RootBlock.createCodec(RootBlock::new);

    public RootBlock(Settings settings) {
        super(settings);
        super.setDefaultState(this.getDefaultState().with(Properties.AXIS, Direction.Axis.Y));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RootBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (world.isClient) {
            return null;
        }
        return validateTicker(type, ROOT_BLOCK_ENTITY, (world1, pos, state1, blockEntity) -> {

            if (world1.getTime() % 20 != 0) return;

            if (world1.getBlockState(pos.north()).isIn(ModBlockTags.ROOTY_BLOCKS) &&
                    world1.getBlockState(pos.east()).isIn(ModBlockTags.ROOTY_BLOCKS) &&
                    world1.getBlockState(pos.south()).isIn(ModBlockTags.ROOTY_BLOCKS) &&
                    world1.getBlockState(pos.west()).isIn(ModBlockTags.ROOTY_BLOCKS)) {

                if (world1.random.nextInt(25) == 0 && world1.getBlockState(pos.up()).isOf(Blocks.AIR)) {
                    Block sapling = ((RootBlockEntity) blockEntity).thumbandthicket$getSapling();
                    if (sapling != Blocks.AIR) {
                        world1.setBlockState(pos.up(), sapling.getDefaultState(), Block.NOTIFY_ALL);
                    }
                }
            }

            if (world1.random.nextInt(3) == 0) {

                BlockPos targetPos = pos.add(
                        world1.random.nextInt(3) - 1,
                        world1.random.nextInt(3) - 1,
                        world1.random.nextInt(3) - 1
                );

                BlockState targetState = world1.getBlockState(targetPos);

                if (targetState.isOf(Blocks.DIRT)) {
                    world1.setBlockState(targetPos, Blocks.ROOTED_DIRT.getDefaultState(), Block.NOTIFY_ALL);
                }
                else if (targetState.isOf(Blocks.GRASS_BLOCK)) {
                    world1.setBlockState(targetPos, ModBlocks.ROOTED_GRASS.getDefaultState(), Block.NOTIFY_ALL);
                }
            }
        });
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.AXIS);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(Properties.AXIS, ctx.getSide().getAxis());
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return PillarBlock.changeRotation(state, rotation);
    }
}