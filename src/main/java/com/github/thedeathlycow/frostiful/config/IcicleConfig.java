package com.github.thedeathlycow.frostiful.config;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.simple.config.Config;
import com.github.thedeathlycow.simple.config.ConfigFactory;
import com.github.thedeathlycow.simple.config.key.ConfigEntry;
import com.github.thedeathlycow.simple.config.key.DoubleEntry;
import net.fabricmc.loader.api.FabricLoader;

public class IcicleConfig {

    public static final Config CONFIG;
    public static final ConfigEntry<Double> BECOME_UNSTABLE_CHANCE = new DoubleEntry("become_unstable_chance", 0.01D, 0.0D, 1.0D);
    public static final ConfigEntry<Double> GROW_CHANCE = new DoubleEntry("grow_chance", 0.02D, 0.0D, 1.0D);

    static {
        CONFIG = ConfigFactory.createConfigWithKeys(
                Frostiful.MODID, "icicle_config", FabricLoader.getInstance().getConfigDir(),
                BECOME_UNSTABLE_CHANCE,
                GROW_CHANCE
        );
    }

}
