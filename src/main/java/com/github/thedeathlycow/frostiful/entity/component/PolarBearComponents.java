package com.github.thedeathlycow.frostiful.entity.component;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.nbt.NbtCompound;

public class PolarBearComponents implements Component, AutoSyncedComponent {

    private int lastShearedAge = 0;

    private final PolarBearEntity provider;

    public PolarBearComponents(PolarBearEntity provider) {
        this.provider = provider;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {

    }

    @Override
    public void writeToNbt(NbtCompound tag) {

    }
}
