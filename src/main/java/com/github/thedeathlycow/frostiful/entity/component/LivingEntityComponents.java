package com.github.thedeathlycow.frostiful.entity.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class LivingEntityComponents implements Component, AutoSyncedComponent {
    private static final String ROOTED_TICKS_KEY = "rooted_ticks";

    private int rootedTicks;
    private byte skateFlags;

    private final LivingEntity provider;

    public LivingEntityComponents(LivingEntity provider) {
        this.provider = provider;
    }


    public int getRootedTicks() {
        return rootedTicks;
    }

    public void setRootedTicks(int rootedTicks) {
        if (this.rootedTicks != rootedTicks) {
            this.rootedTicks = rootedTicks;
            FComponents.ENTITY_COMPONENTS.sync(this.provider);
        }
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
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
        buf.writeVarInt(this.rootedTicks);
        buf.writeByte(this.skateFlags);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        this.rootedTicks = buf.readVarInt();
        this.skateFlags = buf.readByte();
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.rootedTicks = tag.getInt(ROOTED_TICKS_KEY);
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt(ROOTED_TICKS_KEY, this.rootedTicks);
    }
}
