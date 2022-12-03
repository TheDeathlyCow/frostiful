package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.util.FNbtHelper;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.math.MathHelper;

public interface SoakableEntity {

    String WET_KEY = "TicksWet";

    void frostiful$setWetTicks(int amount);

    int frostiful$getWetTicks();

    default boolean frostiful$isWet() {
        return this.frostiful$getWetTicks() > 0;
    }

    default boolean frostiful$isSoaked() {
        return this.frostiful$getWetTicks() >= this.frostiful$getMaxWetTicks();
    }

    default int frostiful$getMaxWetTicks() {
        return 20 * 30;
    }

    default float frostiful$getWetnessScale() {
        int maxWetness = this.frostiful$getMaxWetTicks();
        if (maxWetness == 0) {
            return 0.0f;
        }

        return MathHelper.clamp(
                ((float)this.frostiful$getWetTicks()) / maxWetness,
                0.0f, 1.0f
        );
    }

    static void frostiful$addDataToNbt(SoakableEntity entity, NbtCompound nbt) {
        NbtCompound frostifulNbt = FNbtHelper.getOrDefault(
                nbt,
                FreezableEntity.FROSTIFUL_KEY, NbtElement.COMPOUND_TYPE,
                NbtCompound::getCompound,
                FNbtHelper.NEW_COMPOUND_FALLBACK
        );


        if (entity.frostiful$isWet()) {
            frostifulNbt.putInt(WET_KEY, entity.frostiful$getWetTicks());
        }


        nbt.put(FreezableEntity.FROSTIFUL_KEY, frostifulNbt);
    }

    static void frostiful$setDataFromNbt(SoakableEntity entity, NbtCompound nbt) {
        if (nbt.contains(FreezableEntity.FROSTIFUL_KEY, NbtElement.COMPOUND_TYPE)) {

            NbtCompound frostifulNbt = nbt.getCompound(FreezableEntity.FROSTIFUL_KEY);
            if (frostifulNbt.contains(WET_KEY, NbtElement.INT_TYPE)) {
                int wetTicks = frostifulNbt.getInt(WET_KEY);
                entity.frostiful$setWetTicks(wetTicks);
            } else {
                entity.frostiful$setWetTicks(0);
            }

        }
    }
}
