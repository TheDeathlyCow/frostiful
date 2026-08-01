package com.github.thedeathlycow.frostiful.entity.attachment;

import com.github.thedeathlycow.frostiful.registry.FCardinalComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v8.component.CardinalComponent;

public class LivingEntityComponents implements CardinalComponent, AutoSyncedComponent {
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
            FCardinalComponents.ENTITY_COMPONENTS.sync(this.provider);
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
    public void readData(ValueInput readView) {
        // nothing to read
    }

    @Override
    public void writeData(ValueOutput writeView) {
        // noting to write
    }
}
