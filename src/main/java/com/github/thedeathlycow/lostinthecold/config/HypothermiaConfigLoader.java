package com.github.thedeathlycow.lostinthecold.config;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class HypothermiaConfigLoader {

    public static HypothermiaConfig load() {
        HypothermiaConfig config = null;
        File configFile = new File(LostInTheCold.getDataFolder(), "config.json");
        try (FileReader reader = new FileReader(configFile)) {
            JsonElement element = JsonParser.parseReader(reader);
            config = HypothermiaConfig.deserialize(element);
        } catch (Exception e) {
            LostInTheCold.LOGGER.warn("Error loading hypothermia config, loading default config instead:", e);
            config = DEFAULT_CONFIG;
        }
        return config;
    }

    private static final HypothermiaConfig DEFAULT_CONFIG = new HypothermiaConfig(
            new FreezeRateConfig(1, 5, 15, 3.0, 1.5),
            new FrostResistanceConfig(60, 3.0),
            new ThawRateConfig(-10, -100, 2, 1)
    );
}
