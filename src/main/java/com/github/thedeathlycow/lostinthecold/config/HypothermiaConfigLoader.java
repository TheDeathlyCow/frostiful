package com.github.thedeathlycow.lostinthecold.config;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileReader;

public class HypothermiaConfigLoader implements SimpleSynchronousResourceReloadListener {

    public HypothermiaConfigLoader(File dataFolder) {
        this.dataFolder = dataFolder;
        this.load();
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier(LostInTheCold.MODID, "config_reloader");
    }

    @Override
    public void reload(ResourceManager manager) {
        this.load();
    }

    public HypothermiaConfig getConfig() {
        return config;
    }

    private final File dataFolder;
    private HypothermiaConfig config;

    private void load() {
        File configFile = new File(dataFolder, "config.json");
        try (FileReader reader = new FileReader(configFile)) {
            JsonElement element = JsonParser.parseReader(reader);
            config = HypothermiaConfig.deserialize(element);
        } catch (Exception e) {
            LostInTheCold.LOGGER.warn("Error loading hypothermia config, loading default config instead:", e);
            config = DEFAULT_CONFIG;
        }
        LostInTheCold.LOGGER.info(String.format("Loaded config: %s", config));
    }

    private static final HypothermiaConfig DEFAULT_CONFIG = new HypothermiaConfig(
            new FreezeRateConfig(1, 5, 15, 3.0, 1.5),
            new FrostResistanceConfig(60, 3.0),
            new ThawRateConfig(-10, -100, 2, 1)
    );
}
