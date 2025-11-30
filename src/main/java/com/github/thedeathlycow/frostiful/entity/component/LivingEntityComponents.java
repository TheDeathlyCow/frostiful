package com.github.thedeathlycow.frostiful.entity.component;

import com.github.thedeathlycow.frostiful.registry.FComponents;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class LivingEntityComponents implements Component, AutoSyncedComponent {
    private byte skateFlags;

    private final LivingEntity provider;

    public LivingEntityComponents(LivingEntity provider) {
        this.provider = provider;
    }

    public byte getSkateFlags() {
        return skateFlags;
    }

    public void setSkateFlags(byte skateFlags) {
        if (this.skateFlags != skateFlags) {
            this.skateFlags = skateFlags;
            FComponents.ENTITY_COMPONENTS.sync(this.provider);
        }
    }

    @Override
    public void writeSyncPacket(RegistryFriendlyByteBuf buf, ServerPlayer recipient) {
        buf.writeByte(this.skateFlags);
    }

    @Override
    public void applySyncPacket(RegistryFriendlyByteBuf buf) {
        this.skateFlags = buf.readByte();
    }

    @Override
    public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        // nothing to save
    }

    @Override
    public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        // nothing to save
    }
}
