package com.github.thedeathlycow.frostiful.config.schema;

import com.github.thedeathlycow.frostiful.config.handler.ConfigPaths;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;

public final class SchemaV3 implements ConfigUpdater {
    @Override
    public void run(int originalSchemaVersion) throws IOException {
        JsonObject sourceSettings = JsonCopyHelper.read(ConfigPaths.TEMPERATURE_SOURCE);

        JsonCopyHelper.rename(sourceSettings, "glacialArrowTemperatureChangeMultiplier", "glacialArrowFreezingEffectDurationMultiplier");
        // TODO: move sun lichen multiplier back to temp sources


        Files.writeString(ConfigPaths.BLOCK, sourceSettings.toString());
    }
}