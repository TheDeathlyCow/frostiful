package com.github.thedeathlycow.frostiful.entity.component;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FComponents;
import com.github.thedeathlycow.frostiful.registry.FLootTables;
import com.github.thedeathlycow.frostiful.registry.tag.FEntityTypeTags;
import com.github.thedeathlycow.frostiful.util.FLootHelper;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class BrushableComponent implements Component, AutoSyncedComponent {

    private static final String LAST_BRUSHED_TIME_KEY = "last_brushed_time";
    private static final int BRUSH_COOLDOWN = 20 * 300;
    private long lastBrushTime = -1;

    private final Animal provider;

    public BrushableComponent(Animal provider) {
        this.provider = provider;
    }

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
        BrushableComponent component = FComponents.BRUSHABLE_COMPONENT.getNullable(animal);
        if (component != null && component.isBrushable() && heldItem.is(ConventionalItemTags.BRUSH_TOOLS)) {
            component.brush(player, heldItem);
            if (!animal.level().isClientSide()) {
                heldItem.hurtAndBreak(16, player, hand);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void writeSyncPacket(RegistryFriendlyByteBuf buf, ServerPlayer recipient) {
        buf.writeLong(this.lastBrushTime);
    }

    @Override
    public void applySyncPacket(RegistryFriendlyByteBuf buf) {
        this.lastBrushTime = buf.readLong();
    }

    @Override
    public void readData(ValueInput readView) {
        this.lastBrushTime = readView.getLongOr(LAST_BRUSHED_TIME_KEY, -1);
    }

    @Override
    public void writeData(ValueOutput writeView) {
        if (this.wasBrushed()) {
            writeView.putLong(LAST_BRUSHED_TIME_KEY, this.getLastBrushTime());
        }
    }

    public long getLastBrushTime() {
        return lastBrushTime;
    }

    public void setLastBrushTime(long lastBrushTime) {
        if (this.lastBrushTime != lastBrushTime) {
            this.lastBrushTime = lastBrushTime;
            FComponents.BRUSHABLE_COMPONENT.sync(this.provider);
        }
    }

    public boolean isBrushable() {
        return this.provider.isAlive()
                && !this.provider.isBaby()
                && !this.wasBrushed()
                && this.provider.is(FEntityTypeTags.IS_BRUSHABLE);
    }

    public boolean wasBrushed() {
        return lastBrushTime >= 0L
                && this.provider.level().getGameTime() - lastBrushTime <= BRUSH_COOLDOWN;
    }

    private void brush(Player brusher, ItemStack tool) {
        Level world = provider.level();
        world.playSound(
                null,
                provider,
                SoundEvents.BRUSH_GENERIC,
                SoundSource.PLAYERS,
                1.0f, 1.0f
        );
        provider.gameEvent(GameEvent.SHEAR, brusher);

        if (!world.isClientSide()) {
            ResourceKey<LootTable> furLootTable = getLootTableForAnimal(provider);

            if (furLootTable != null) {
                FLootHelper.dropBrushingLoot(provider, tool, furLootTable);
            } else {
                Frostiful.LOGGER.warn(
                        "Attempted to brush an animal type {} that does not drop fur!",
                        provider.getType().builtInRegistryHolder().toString()
                );
            }

            this.setLastBrushTime(world.getGameTime());
            this.setAngryAt(brusher);
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

    /**
     * Sets the provider to be angry at the brusher if the provider is not tamed
     *
     * @param brusher the player who brushed the provider
     */
    private void setAngryAt(Player brusher) {
        if (brusher.isCreative()) {
            return;
        }

        if (provider instanceof TamableAnimal tameable && tameable.isTame()) {
            return;
        }

        if (provider instanceof NeutralMob angerable) {
            angerable.startPersistentAngerTimer();
            angerable.setPersistentAngerTarget(EntityReference.of(brusher));
        }
    }
}
