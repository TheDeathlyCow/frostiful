package com.github.thedeathlycow.frostiful.entity.attachment;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FrostifulEntityAttachments;
import com.github.thedeathlycow.frostiful.registry.FLootTables;
import com.github.thedeathlycow.frostiful.registry.tag.FEntityTypeTags;
import com.github.thedeathlycow.frostiful.util.FLootHelper;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BrushableComponent implements INBTSerializable<CompoundTag> {
    private static final String LAST_BRUSHED_TIME_KEY = "last_brushed_time";
    private static final int BRUSH_COOLDOWN = 20 * 300;

    private final IAttachmentHolder provider;
    private long lastBrushTime;

    public BrushableComponent(IAttachmentHolder provider) {
        this(provider, -1);
    }

    private BrushableComponent(IAttachmentHolder provider, long lastBrushTime) {
        this.provider = provider;
        this.lastBrushTime = lastBrushTime;
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
        BrushableComponent component = animal.getData(FrostifulEntityAttachments.BRUSHABLE_COMPONENT);
        if (component.isBrushable() && heldItem.is(ConventionalItemTags.BRUSH_TOOLS)) {
            component.brush(player);
            if (!animal.level().isClientSide) {
                heldItem.hurtAndBreak(16, player, LivingEntity.getSlotForHand(hand));
            }
            return InteractionResult.sidedSuccess(animal.level().isClientSide);
        }

        return InteractionResult.PASS;
    }

    public long getLastBrushTime() {
        return lastBrushTime;
    }

    public void setLastBrushTime(long lastBrushTime) {
        if (this.lastBrushTime != lastBrushTime) {
            this.lastBrushTime = lastBrushTime;
            this.provider.syncData(FrostifulEntityAttachments.BRUSHABLE_COMPONENT);
        }
    }

    public boolean isBrushable() {
        return this.provider instanceof Animal animal
                && animal.isAlive()
                && !animal.isBaby()
                && !this.wasBrushed()
                && animal.getType().is(FEntityTypeTags.IS_BRUSHABLE);
    }

    public boolean wasBrushed() {
        return this.provider instanceof Animal animal
                && lastBrushTime >= 0L
                && animal.level().getDayTime() - lastBrushTime <= BRUSH_COOLDOWN;
    }

    private void brush(Player brusher) {
        if (!(this.provider instanceof Animal animal)) {
            return;
        }

        Level world = animal.level();
        world.playSound(
                null,
                animal,
                SoundEvents.BRUSH_GENERIC,
                SoundSource.PLAYERS,
                1.0f, 1.0f
        );
        animal.gameEvent(GameEvent.SHEAR, brusher);

        if (!world.isClientSide) {
            ResourceKey<LootTable> furLootTable = getLootTableForAnimal(animal);

            if (furLootTable != null) {
                FLootHelper.dropLootFromEntity(animal, furLootTable);
            } else {
                Frostiful.LOGGER.warn(
                        "Attempted to brush an animal type {} that does not drop fur!",
                        animal.getType().builtInRegistryHolder().toString()
                );
            }

            this.setLastBrushTime(world.getGameTime());
            this.setAngryAt(brusher);
        }
    }

    @Nullable
    private static ResourceKey<LootTable> getLootTableForAnimal(Animal animal) {
        EntityType<?> type = animal.getType();
        if (type.is(FEntityTypeTags.BRUSHING_DROPS_POLAR_BEAR_FUR)) {
            return FLootTables.POLAR_BEAR_BRUSHING_GAMEPLAY;
        } else if (type.is(FEntityTypeTags.BRUSHING_DROPS_WOLF_FUR)) {
            return FLootTables.WOLF_BRUSHING_GAMEPLAY;
        } else if (type.is(FEntityTypeTags.BRUSHING_DROPS_OCELOT_FUR)) {
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
            angerable.setPersistentAngerTarget(brusher.getUUID());
        }
    }

    @Override
    @NotNull
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        var tag = new CompoundTag();

        if (this.wasBrushed()) {
            tag.putLong(LAST_BRUSHED_TIME_KEY, this.getLastBrushTime());
        }

        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        if (tag.contains(LAST_BRUSHED_TIME_KEY, Tag.TAG_LONG)) {
            this.lastBrushTime = tag.getLong(LAST_BRUSHED_TIME_KEY);
        }
    }

    public static final class SyncHandler implements AttachmentSyncHandler<BrushableComponent> {
        @Override
        public void write(RegistryFriendlyByteBuf buf, BrushableComponent attachment, boolean initialSync) {
            buf.writeLong(attachment.getLastBrushTime());
        }

        @Override
        public BrushableComponent read(IAttachmentHolder holder, RegistryFriendlyByteBuf buf, @Nullable BrushableComponent previousValue) {
            return new BrushableComponent(holder, buf.readLong());
        }
    }
}
