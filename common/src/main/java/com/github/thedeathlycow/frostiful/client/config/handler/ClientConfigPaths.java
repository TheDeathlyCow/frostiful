/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

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