package com.github.thedeathlycow.frostiful.config.handler;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.google.common.base.Suppliers;

import java.nio.file.Path;
import java.util.function.Supplier;

public final class ConfigPaths {
    private static final Supplier<Path> COMMON_PATH = Suppliers.memoize(() -> Frostiful.getConfigDir().resolve("common"));

    public static final Path BLOCK = commonPath("block");
    public static final Path ENTITY = commonPath("entity");
    public static final Path ENVIRONMENT = commonPath("environment");
    public static final Path ITEM = commonPath("item");
    public static final Path SOAKING = commonPath("soaking");
    public static final Path TEMPERATURE_SOURCE = commonPath("temperature_source");
    public static final Path WEATHER = commonPath("weather");

    private static Path commonPath(String name) {
        return COMMON_PATH.get().resolve(name + ".json5");
    }

    private ConfigPaths() {

    }
}