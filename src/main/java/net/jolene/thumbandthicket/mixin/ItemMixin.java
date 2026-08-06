package net.jolene.thumbandthicket.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.jolene.thumbandthicket.ThumbAndThicket;
import net.jolene.thumbandthicket.block.ModBlockTags;
import net.jolene.thumbandthicket.datagen.ModItemTagProvider;
import net.jolene.thumbandthicket.item.ChiselItem;
import net.minecraft.block.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Objects;

@Mixin(Item.class)
public class ItemMixin {

    @Shadow
    @Final
    private static Logger LOGGER;

    @Unique
    private static <T extends Comparable<T>> BlockState copyProperty(BlockState newState, BlockState oldState, Property<T> property) {
        return newState.with(property, oldState.get(property));
    }

    @WrapMethod(method = "useOnBlock")
    private ActionResult immersive_interactions$useOnBlock(ItemUsageContext context, Operation<ActionResult> original) {
        ItemStack itemStack = context.getStack();
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        String blockIdString = Registries.BLOCK.getId(state.getBlock()).toString();
        Item barkFD = Registries.ITEM.get(Identifier.of("farmersdelight", "tree_bark"));

        if (!world.isClient) {
            ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();

            SoundEvent soundEvent = null;
            String prependString = "";
            String targetString = "";
            String replaceString = "";
            String newBlockString = "";
            String[] baseBlock = blockIdString.split(":");

            if (itemStack.getItem() instanceof PickaxeItem && state.isIn(ModBlockTags.CRACKABLE_BLOCKS)) {
                prependString = "cracked_";
                soundEvent = SoundEvents.BLOCK_DEEPSLATE_BRICKS_HIT;
            }
            if (state.isIn(ModBlockTags.CRACKED_BLOCKS) && itemStack.isIn(ModItemTagProvider.CAN_REPAIR_BRICK) && !baseBlock[1].contains("infested")) {
                targetString = "cracked_";
                soundEvent = SoundEvents.BLOCK_MUD_STEP;
            }
            if (state.isIn(ModBlockTags.MOSSABLE_BLOCKS) && itemStack.isIn(ModItemTagProvider.CAN_APPLY_MOSS)) {
                prependString = "mossy_";
                soundEvent = SoundEvents.BLOCK_MOSS_HIT;
            }
            if (itemStack.getItem() instanceof ShearsItem && state.isIn(ModBlockTags.MOSSY_BLOCKS)) {
                targetString = "mossy_";
                soundEvent = SoundEvents.BLOCK_GROWING_PLANT_CROP;
            }
            if (state.isIn(BlockTags.LOGS) && (itemStack.isIn(ModItemTagProvider.CAN_APPLY_BARK) || itemStack.isOf(barkFD))) {
                if (baseBlock[1].contains("stripped_")) {
                    targetString = "stripped_";
                }
                if (baseBlock[1].contains("_log") && !baseBlock[1].contains("stripped_")) {
                    targetString = "_log";
                    replaceString = "_wood";
                }
                soundEvent = SoundEvents.BLOCK_WOOD_HIT;
            }
            if (itemStack.getItem() instanceof ChiselItem) {
                if (state.isIn(ModBlockTags.CHISELABLE_BLOCKS)) prependString = "chiseled_";
                if (state.isIn(ModBlockTags.CHISELED_BLOCKS)) targetString = "chiseled_";
                soundEvent = SoundEvents.BLOCK_GRINDSTONE_USE;
            }

            if(!targetString.isEmpty()) newBlockString = baseBlock[1].replace(targetString, replaceString);
            if(!prependString.isEmpty()) newBlockString = prependString + baseBlock[1];
            Block newBlock = ThumbAndThicket.thumbandthicket$getBlockByName(newBlockString);
            if (!(newBlock instanceof AirBlock)) {
                BlockState newState = newBlock.getDefaultState();
                for (Property<?> property : state.getProperties()) if (newState.contains(property)) newState = copyProperty(newState, state, property);

                world.setBlockState(pos, newState);
                world.playSound(null, pos, soundEvent, SoundCategory.BLOCKS);
                if (itemStack.contains(DataComponentTypes.MAX_DAMAGE)) itemStack.damage(1, (ServerWorld) world, (ServerPlayerEntity) context.getPlayer(), item -> Objects.requireNonNull(context.getPlayer()).sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));
                else itemStack.decrementUnlessCreative(1, player);
            }
            world.emitGameEvent(GameEvent.ITEM_INTERACT_FINISH, pos, GameEvent.Emitter.of(player));
            return ActionResult.success(!(newBlock instanceof AirBlock));
        }
        return original.call(context);
    }
}
