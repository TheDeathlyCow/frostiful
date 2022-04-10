package com.github.thedeathlycow.lostinthecold.config;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
        Map<ConfigKey<?>, ConfigValue<?>> configIn = new HashMap<>();

        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            try {
                ConfigKey<?> key = ConfigKeys.valueOf(entry.getKey());
                configIn.put(key, new ConfigValue<>(key.deserialize(entry.getValue())));
            } catch (IllegalArgumentException ignored) {}
        }

        LostInTheCold.getConfig().update(configIn);
    }

}
