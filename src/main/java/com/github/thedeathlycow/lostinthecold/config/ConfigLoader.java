package com.github.thedeathlycow.lostinthecold.config;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.checkerframework.checker.units.qual.C;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ConfigLoader implements SimpleSynchronousResourceReloadListener {

    public Config createEmptyConfig(Identifier identifier) {
        Config config = new Config();
        return registerConfig(identifier, config);
    }

    public Config registerConfig(Identifier identifier, Config config) {
        configs.put(identifier, config);
        return config;
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier(LostInTheCold.MODID, "config_reloader");
    }

    @Override
    public void reload(ResourceManager manager) {

        configs.values().forEach(Config::reset);

        for (Identifier id : manager.findResources("config", path -> path.endsWith(".json"))) {
            if (configs.containsKey(id)) {
                loadConfig(manager, id);
            }
        }
    }

    private void loadConfig(ResourceManager manager, Identifier id) {
        Config config = configs.get(id);
        try (InputStream inputStream = manager.getResource(id).getInputStream()) {
            try (InputStreamReader reader = new InputStreamReader(inputStream)) {
                JsonObject object = JsonParser.parseReader(reader).getAsJsonObject();
                updateConfig(config, object);
            }
        } catch (Exception e) {
            LostInTheCold.LOGGER.error("An error occurred while loading Lost in the Cold config " + id.toString() + " ", e);
        }
    }

    private void updateConfig(Config config, JsonObject object) {

        Config configIn = new Config();

        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            String jsonEntry = entry.getKey();
            ConfigKey<?> key;
            try {
                key = ConfigKeys.REGISTRY.getEntry(jsonEntry);
            } catch (IllegalArgumentException exception) {
                // ignore entries that are not valid config keys
                LostInTheCold.LOGGER.info("Could not load config option '" + jsonEntry + "' with reason: " + exception.getMessage());
                continue;
            }
            configIn.addEntry(key);
            configIn.deserializeAndSet(key, entry.getValue());
        }
        config.update(configIn);
    }

    private final Map<Identifier, Config> configs = new HashMap<>();

}
