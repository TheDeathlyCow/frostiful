package com.github.thedeathlycow.frostiful.entity.component;

import com.github.thedeathlycow.frostiful.entity.FShearable;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class PolarBearComponents implements Component, AutoSyncedComponent {

    private static final String LAST_SHEARED_AGE_KEY = "last_sheared_age";

    private int lastShearedAge = -1;

    private final PolarBearEntity provider;
    private final FShearable shearable;

    public PolarBearComponents(PolarBearEntity provider) {
        this.provider = provider;
        this.shearable = (FShearable) provider;
    }

    @Override
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
        buf.writeVarInt(this.lastShearedAge);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
        this.lastShearedAge = buf.readVarInt();
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        if (tag.contains(LAST_SHEARED_AGE_KEY, NbtElement.INT_TYPE)) {
            this.lastShearedAge = tag.getInt(LAST_SHEARED_AGE_KEY);
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        if (shearable.frostiful$wasSheared()) {
            tag.putInt(LAST_SHEARED_AGE_KEY, lastShearedAge);
        }
    }

    public int getLastShearedAge() {
        return lastShearedAge;
    }

    public void setLastShearedAge(int lastShearedAge) {
        if (this.lastShearedAge != lastShearedAge) {
            this.lastShearedAge = lastShearedAge;
            FComponents.POLAR_BEAR_COMPONENTS.sync(this.provider);
        }
    }
}
