package net.jolene.thumbandthicket.entity.custom.goals;

import net.jolene.thumbandthicket.block.ModBlockTags;
import net.jolene.thumbandthicket.util.EggLayInterface;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

import static net.minecraft.state.property.Properties.EGGS;

public class FindNestGoal extends Goal {
    private final AnimalEntity animalEntity;
    private final Block eggBlock;
    @Nullable private BlockPos targetPos;
    private int delay;

    public FindNestGoal(AnimalEntity animalEntity, Block eggBlock) {
        this.animalEntity = animalEntity;
        this.eggBlock = eggBlock;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        this.targetPos = locateClosestNest(this.animalEntity.getWorld(), this.animalEntity, 10);
        if (targetPos != null) {
            BlockState state = animalEntity.getWorld().getBlockState(targetPos.up());
            if (!state.isReplaceable() || (state.isOf(eggBlock) && state.get(EGGS) == 4)) return false;
        }
        return ((EggLayInterface) animalEntity).thumbAndThicket$isReadyToLay() && animalEntity.isAlive() && !animalEntity.isBaby() && !animalEntity.hasPassengers() && targetPos != null;
    }

    @Override
    public boolean shouldContinue() {
        return this.targetPos != null && ((EggLayInterface) animalEntity).thumbAndThicket$isReadyToLay() && animalEntity.isAlive();
    }

    @Override
    public void start() {
        if (this.targetPos != null) this.animalEntity.getNavigation().startMovingTo(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ(), 1.0);
    }

    @Override
    public void stop() {
        this.animalEntity.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (--this.delay > 0) return;
        this.delay = this.getTickCount(10);
        if (this.targetPos == null) return;
        this.animalEntity.getNavigation().startMovingTo(this.targetPos.getX(), this.targetPos.getY(), this.targetPos.getZ(), 1.0);
        if (this.animalEntity.getBlockPos().getSquaredDistance(this.targetPos) < 1.5 || this.animalEntity.getNavigation().isIdle()) layEgg();
    }

    public void layEgg() {
        World world = animalEntity.getWorld();
        BlockPos pos = animalEntity.getBlockPos();
        BlockState state = world.getBlockState(pos);

        if (animalEntity instanceof ChickenEntity chickenEntity) {
        }

        if (state.isReplaceable() && !state.isOf(eggBlock)) {
            world.setBlockState(pos, eggBlock.getDefaultState());
            animalEntity.emitGameEvent(GameEvent.ENTITY_PLACE);
            ((EggLayInterface) animalEntity).thumbAndThicket$resetEggLayTime();
        } else if (state.isOf(eggBlock)) {
            int amount = state.get(EGGS);
            int hatch = state.get(Properties.HATCH);
            if (amount < 4) world.setBlockState(pos, eggBlock.getDefaultState().with(EGGS, amount + 1).with(Properties.HATCH, hatch));
            animalEntity.emitGameEvent(GameEvent.ENTITY_PLACE);
            ((EggLayInterface) animalEntity).thumbAndThicket$resetEggLayTime();
        }
        targetPos = null;
    }

    @Nullable
    protected BlockPos locateClosestNest(BlockView world, Entity entity, int range) {
        BlockPos blockPos = entity.getBlockPos();
        if (!world.getBlockState(blockPos).getCollisionShape(world, blockPos).isEmpty()) return null;
        return BlockPos.findClosest(entity.getBlockPos(), range, range, pos -> world.getBlockState(pos).isIn(ModBlockTags.NEST_BLOCKS)).orElse(null);
    }
}
