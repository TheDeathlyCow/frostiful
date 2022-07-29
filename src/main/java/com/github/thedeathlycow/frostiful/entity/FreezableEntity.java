package com.github.thedeathlycow.frostiful.entity;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.math.MathHelper;

public interface FreezableEntity {

    String FROSTIFUL_KEY = "Frostiful";
    String FROST_KEY = "CurrentFrost";

    int frostiful$getCurrentFrost();

    int frostiful$getMaxFrost();

    void frostiful$setFrost(int amount);

    default void frostiful$setFrost(int amount, boolean clamp) {
        int toSet = amount;
        if (clamp) {
            toSet = MathHelper.clamp(toSet, 0, this.frostiful$getMaxFrost());
        }
        this.frostiful$setFrost(toSet);
    }

    boolean frostiful$canFreeze();

    default void frostiful$addFrost(int amount) {
        this.frostiful$setFrost(this.frostiful$getCurrentFrost() + amount, true);
    }

    default void frostiful$removeFrost(int amount) {
        this.frostiful$setFrost(this.frostiful$getCurrentFrost() - amount, true);
    }

    default float frostiful$getFrostProgress() {
        final int maxFrost = this.frostiful$getMaxFrost();
        return Math.min(this.frostiful$getCurrentFrost(), maxFrost) / ((float) maxFrost);
    }

    static void frostiful$addFrostToNbt(FreezableEntity entity, NbtCompound nbt) {
        NbtCompound frostifulNbt = new NbtCompound();
        if (entity.frostiful$getCurrentFrost() > 0) {
            frostifulNbt.putInt(FROST_KEY, entity.frostiful$getCurrentFrost());
        }
        nbt.put(FROSTIFUL_KEY, frostifulNbt);
    }

    static void frostiful$setFrostFromNbt(FreezableEntity entity, NbtCompound nbt) {
        int amount = 0;
        if (nbt.contains(FROSTIFUL_KEY, NbtElement.COMPOUND_TYPE)) {
            NbtCompound frostifulNbt = nbt.getCompound(FROSTIFUL_KEY);
            if (frostifulNbt.contains(FROST_KEY, NbtElement.INT_TYPE)) {
                amount = nbt.getInt(FROST_KEY);
            }
        }

        entity.frostiful$setFrost(amount);
    }
}
