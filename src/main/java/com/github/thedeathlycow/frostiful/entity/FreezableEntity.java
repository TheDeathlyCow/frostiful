package com.github.thedeathlycow.frostiful.entity;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

public interface FreezableEntity {

    String FROST_KEY = "Frostiful.CurrentFrost";

    int frostiful$getCurrentFrost();

    int frostiful$getMaxFrost();

    void frostiful$setFrost(int amount);

    boolean frostiful$canFreeze();

    default void frostiful$addFrost(int amount) {
        this.frostiful$setFrost(this.frostiful$getCurrentFrost() + amount);
    }

    default void frostiful$removeFrost(int amount) {
        this.frostiful$setFrost(this.frostiful$getCurrentFrost() - amount);
    }

    default float frostiful$getFrostProgress() {
        final int maxFrost = this.frostiful$getMaxFrost();
        return Math.min(this.frostiful$getCurrentFrost(), maxFrost) / ((float) maxFrost);
    }

    default void addToNbt(NbtCompound nbt) {
        if (this.frostiful$getCurrentFrost() > 0) {
            nbt.putInt(FROST_KEY, this.frostiful$getCurrentFrost());
        }
    }

    default void setFrostFromNbt(NbtCompound nbt) {
        if (nbt.contains(FROST_KEY, NbtElement.INT_TYPE)) {
            this.frostiful$setFrost(nbt.getInt(FROST_KEY));
        } else {
            this.frostiful$setFrost(0);
        }
    }
}
