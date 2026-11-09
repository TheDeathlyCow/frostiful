package com.github.thedeathlycow.frostiful.config.schema;

import com.github.thedeathlycow.frostiful.config.handler.ConfigPaths;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;

public final class SchemaV2 implements ConfigUpdater {
    @Override
    public void run(int originalSchemaVersion) throws IOException {
        JsonObject blockSettings = JsonCopyHelper.read(ConfigPaths.BLOCK);
        JsonCopyHelper.rename(blockSettings, "campfireWarmthTimeMultiplier", "campfireWarmheartedTimeMultiplier");
        Files.writeString(ConfigPaths.BLOCK, blockSettings.toString());
    }
}