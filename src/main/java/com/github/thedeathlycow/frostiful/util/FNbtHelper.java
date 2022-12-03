package com.github.thedeathlycow.frostiful.util;

import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Contract;

public class FNbtHelper {

    public static final Fallback<NbtCompound> NEW_COMPOUND_FALLBACK = NbtCompound::new;

    public static <T> T getOrDefault(NbtCompound source, String key, byte type, NbtGetter<T> getter, Fallback<T> fallback) {
        return source.contains(key, type) ? getter.get(source, key) : fallback.get();
    }

    @FunctionalInterface
    public interface NbtGetter<T> {

        T get(NbtCompound nbt, String key);

    }

    @FunctionalInterface
    public interface Fallback<T> {
        @Contract("-> new")
        T get();
    }
}
