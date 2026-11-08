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

package com.github.thedeathlycow.frostiful.config.handler;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.section.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

import java.nio.file.Path;

public final class ConfigHandlers {
    public static final ConfigClassHandler<BlockSettings> BLOCK = createHandler(
            BlockSettings.class,
            "common/block",
            ConfigPaths.BLOCK
    );
    public static final ConfigClassHandler<EntitySettings> ENTITY = createHandler(
            EntitySettings.class,
            "common/entity",
            ConfigPaths.ENTITY
    );
    public static final ConfigClassHandler<EnvironmentSettings> ENVIRONMENT = createHandler(
            EnvironmentSettings.class,
            "common/environment",
            ConfigPaths.ENVIRONMENT
    );
    public static final ConfigClassHandler<ItemSettings> ITEM = createHandler(
            ItemSettings.class,
            "common/item",
            ConfigPaths.ITEM
    );
    public static final ConfigClassHandler<SoakingSettings> SOAKING = createHandler(
            SoakingSettings.class,
            "common/soaking",
            ConfigPaths.SOAKING
    );
    public static final ConfigClassHandler<TemperatureSourceSettings> TEMPERATURE_SOURCE = createHandler(
            TemperatureSourceSettings.class,
            "common/temperature_source",
            ConfigPaths.TEMPERATURE_SOURCE
    );
    public static final ConfigClassHandler<WeatherSettings> WEATHER = createHandler(
            WeatherSettings.class,
            "common/weather",
            ConfigPaths.WEATHER
    );

    public static <T> ConfigClassHandler<T> createHandler(Class<T> cls, String id, Path path) {
        return ConfigClassHandler.createBuilder(cls)
                .id(Frostiful.id(id))
                .serializer(
                        config -> GsonConfigSerializerBuilder.create(config)
                                .setPath(path)
                                .setJson5(true)
                                .build()
                )
                .build();
    }

    private ConfigHandlers() {

    }
}