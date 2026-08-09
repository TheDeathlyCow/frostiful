package com.github.thedeathlycow.frostiful.client.config.handler;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.google.common.base.Suppliers;

import java.nio.file.Path;
import java.util.function.Supplier;

public final class ClientConfigPaths {
    private static final Supplier<Path> CLIENT_PATH = Suppliers.memoize(() -> Frostiful.getConfigDir().resolve("client"));
    
    public static final Path ACCESSIBILITY = clientPath("accessibility");
    public static final Path DISPLAY = clientPath("display");

    private static Path clientPath(String name) {
        return CLIENT_PATH.get().resolve(name + ".json5");
    }

    private ClientConfigPaths() {

    }
}