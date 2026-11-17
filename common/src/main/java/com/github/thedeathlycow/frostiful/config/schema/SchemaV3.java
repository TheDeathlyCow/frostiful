package com.github.thedeathlycow.frostiful.config.schema;

import com.github.thedeathlycow.frostiful.config.handler.ConfigPaths;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;

public final class SchemaV3 implements ConfigUpdater {
    @Override
    public void run(int originalSchemaVersion) throws IOException {
        JsonObject sourceSettings = JsonCopyHelper.read(ConfigPaths.TEMPERATURE_SOURCE);
        JsonObject itemSettings = JsonCopyHelper.read(ConfigPaths.ITEM);

        JsonCopyHelper.copyFloatProperty(sourceSettings, itemSettings, "glacialArrowTemperatureChangeMultiplier");
        JsonCopyHelper.rename(itemSettings, "glacialArrowTemperatureChangeMultiplier", "glacialArrowFreezingEffectDurationMultiplier");

        Files.writeString(ConfigPaths.TEMPERATURE_SOURCE, sourceSettings.toString());
        Files.writeString(ConfigPaths.ITEM, itemSettings.toString());
    }
}