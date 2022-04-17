package com.github.thedeathlycow.lostinthecold.config;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.google.gson.*;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ConfigLoader implements SimpleSynchronousResourceReloadListener {

    @Override
    public Identifier getFabricId() {
        return new Identifier(LostInTheCold.MODID, "config_reloader");
    }

    @Override
    public void reload(ResourceManager manager) {
        LostInTheCold.getConfig().reset();
        for (Identifier id : manager.findResources("lost_in_the_cold_config", path -> path.endsWith(".json"))) {
            try (InputStream inputStream = manager.getResource(id).getInputStream()) {
                try (InputStreamReader reader = new InputStreamReader(inputStream)) {
                    JsonObject object = JsonParser.parseReader(reader).getAsJsonObject();
                    updateConfig(object);
                }
            } catch (Exception e) {
                LostInTheCold.LOGGER.error("An error occurred while loading Lost in the Cold config " + id.toString() + " ", e);
            }
        }
    }

    private void updateConfig(JsonObject object) {

        LostInTheColdConfig configIn = new LostInTheColdConfig("reloadConfigIn");

        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            String jsonEntry = entry.getKey();
            ConfigKey<?> key;
            try {
                key = ConfigKeys.REGISTRY.getEntry(jsonEntry);
            }
            catch (IllegalArgumentException exception) {
                // ignore entries that are not valid config keys
                LostInTheCold.LOGGER.info("Could not load config option '" + jsonEntry + "' with reason: " + exception.getMessage());
                continue;
            }
            configIn.addEntry(key);
            configIn.deserializeAndSet(key, entry.getValue());
        }

        LostInTheCold.getConfig().update(configIn);
    }

}
