package com.github.thedeathlycow.frostiful.entity;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

public interface FreezableEntity {

    int frostiful$getCurrentFrost();

    int frostiful$getMaxFrost();

    void frostiful$setFrost(int amount);

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

    default boolean frostiful$canFreeze() {
        return true;
    }

    default NbtElement toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("CurrentFrost", this.frostiful$getCurrentFrost());
        return nbt;
    }

    static void setFrostFromNbt(FreezableEntity entity, NbtCompound nbt) {
        if (nbt.contains("CurrentFrost", NbtElement.INT_TYPE)) {
            entity.frostiful$setFrost(nbt.getInt("CurrentFrost"));
        } else {
            entity.frostiful$setFrost(0);
        }
    }
}
