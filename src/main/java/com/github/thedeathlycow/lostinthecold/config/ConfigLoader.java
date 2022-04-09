package com.github.thedeathlycow.lostinthecold.config;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.google.gson.Gson;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;
import java.io.InputStreamReader;

public class ConfigLoader implements SimpleSynchronousResourceReloadListener {

    public ConfigLoader() {
        constructDefaultConfig();
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier(LostInTheCold.MODID, "config_reloader");
    }

    @Override
    public void reload(ResourceManager manager) {
        Gson gson = new Gson();
        for (Identifier id : manager.findResources("lost_in_the_cold_config", path -> path.endsWith(".json"))) {
            try (InputStream inputStream = manager.getResource(id).getInputStream()) {
                try (InputStreamReader reader = new InputStreamReader(inputStream)) {
                    Config added = gson.fromJson(reader, Config.class);
                    LostInTheCold.getConfig().update(added);
                }
            } catch (Exception e) {
                LostInTheCold.LOGGER.error("An error occurred while loading Lost in the Cold config " + id.toString() + " ", e);
            }
        }
    }

    private void constructDefaultConfig() {
        config = new Config();
        config.addEntry("ticks_per_frost_resistance", 1200);
        config.addEntry("base_entity_frost_resistance", 0.11666666666667);
        config.addEntry("base_player_frost_resistance", 3.0D);
        config.addEntry("chilly_biome_freeze_rate", 1);
        config.addEntry("cold_biome_freeze_rate", 5);
        config.addEntry("freezing_biome_freeze_rate", 15);
        config.addEntry("powder_snow_freeze_rate_multiplier", 3.0);
        config.addEntry("wet_freeze_rate_multiplier", 1.5);
        config.addEntry("warm_biome_freeze_rate", 10);
        config.addEntry("on_fire_freeze_rate", 100);
        config.addEntry("warmth_per_light_level", 2);
        config.addEntry("min_warmth_light_level", 7);
    }

    private Config config;


}
