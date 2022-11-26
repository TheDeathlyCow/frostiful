package com.github.thedeathlycow.frostiful.util;

import net.minecraft.nbt.NbtCompound;

public class FNbtHelper {

    public static <T> T getOrDefault(NbtCompound source, String key, byte type, NbtGetter<T> getter, T fallback) {
        return source.contains(key, type) ? getter.get(source, key) : fallback;
    }

    @FunctionalInterface
    public interface NbtGetter<T> {

        T get(NbtCompound nbt, String key);

    }
}
