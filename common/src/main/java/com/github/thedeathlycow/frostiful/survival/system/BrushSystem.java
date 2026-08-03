package com.github.thedeathlycow.frostiful.survival.system;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FDataAttachments;
import com.github.thedeathlycow.frostiful.registry.FLootTables;
import com.github.thedeathlycow.frostiful.registry.tag.FEntityTypeTags;
import com.github.thedeathlycow.frostiful.util.FLootHelper;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;

public final class BrushSystem {
    private static final int BRUSH_COOLDOWN = 20 * 300;

    /**
     * @param animal The mob that was interacted with
     * @param player The player who interacted with the mob
     * @param hand   The hand they used to interact
     * @param base   Original action result for the interaction
     */
    public static InteractionResult interactWithMob(Animal animal, Player player, InteractionHand hand, InteractionResult base) {
        if (player.isSpectator() || base != InteractionResult.PASS) {
            return base;
        }

        ItemStack heldItem = player.getItemInHand(hand);

        long lastBrushTime = player.getAttachedOrCreate(FDataAttachments.LAST_BRUSH_TIME);
        if (isBrushable(animal, lastBrushTime) && heldItem.is(ConventionalItemTags.BRUSH_TOOLS)) {
            brush(animal, player, heldItem);
            if (!animal.level().isClientSide()) {
                heldItem.hurtAndBreak(16, player, hand);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public static boolean wasBrushed(Entity animal, long lastBrushTime) {
        return lastBrushTime >= 0
                && animal.level().getGameTime() - lastBrushTime <= BRUSH_COOLDOWN;
    }

    private static boolean isBrushable(Animal animal, long lastBrushTime) {
        return animal.isAlive()
                && !animal.isBaby()
                && !wasBrushed(animal, lastBrushTime)
                && animal.is(FEntityTypeTags.IS_BRUSHABLE);
    }

    private static void brush(Animal animal, Player brusher, ItemStack tool) {
        Level world = animal.level();
        world.playSound(
                null,
                animal,
                SoundEvents.BRUSH_GENERIC,
                SoundSource.PLAYERS,
                1.0f, 1.0f
        );
        animal.gameEvent(GameEvent.SHEAR, brusher);

        if (!world.isClientSide()) {
            ResourceKey<LootTable> furLootTable = getLootTableForAnimal(animal);

            if (furLootTable != null) {
                FLootHelper.dropBrushingLoot(animal, tool, furLootTable);
            } else {
                Frostiful.LOGGER.warn(
                        "Attempted to brush an animal type {} that does not drop fur!",
                        animal.getType().builtInRegistryHolder()
                );
            }

            animal.setAttached(FDataAttachments.LAST_BRUSH_TIME, world.getGameTime());
            setAngryAt(animal, brusher);
        }
    }

    @Nullable
    private static ResourceKey<LootTable> getLootTableForAnimal(Animal animal) {
        if (animal.is(FEntityTypeTags.BRUSHING_DROPS_POLAR_BEAR_FUR)) {
            return FLootTables.POLAR_BEAR_BRUSHING_GAMEPLAY;
        } else if (animal.is(FEntityTypeTags.BRUSHING_DROPS_WOLF_FUR)) {
            return FLootTables.WOLF_BRUSHING_GAMEPLAY;
        } else if (animal.is(FEntityTypeTags.BRUSHING_DROPS_OCELOT_FUR)) {
            return FLootTables.OCELOT_BRUSHING_GAMEPLAY;
        } else {
            return null;
        }
    }

    private static void setAngryAt(Animal animal, Player brusher) {
        if (brusher.isCreative()) {
            return;
        }

        if (animal instanceof TamableAnimal tameable && tameable.isTame()) {
            return;
        }

        if (animal instanceof NeutralMob angerable) {
            angerable.startPersistentAngerTimer();
            angerable.setPersistentAngerTarget(EntityReference.of(brusher));
        }
    }

    private BrushSystem() {

    }
}