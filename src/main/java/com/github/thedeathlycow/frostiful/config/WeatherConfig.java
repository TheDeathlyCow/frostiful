package com.github.thedeathlycow.frostiful.config;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.simple.config.Config;
import com.github.thedeathlycow.simple.config.ConfigFactory;
import com.github.thedeathlycow.simple.config.entry.ByteEntry;
import com.github.thedeathlycow.simple.config.entry.ConfigEntry;
import net.fabricmc.loader.api.FabricLoader;

public class WeatherConfig {

    public static final ConfigEntry<Byte> FREEZE_TOP_LAYER_MAX_ACCUMULATION = new ByteEntry("freeze_top_layer_max_accumulation", (byte) 2, (byte) 0, (byte) 8);
    public static final ConfigEntry<Byte> MAX_SNOW_BUILDUP_STEP = new ByteEntry("max_snow_buildup_step", (byte) 2, (byte) 1, (byte) 8);
    public static final ConfigEntry<Byte> MAX_SNOW_BUILDUP = new ByteEntry("max_snow_buildup", (byte) 8, (byte) 0, (byte) 8);
    public static final Config CONFIG = ConfigFactory.createConfigWithKeys(
            Frostiful.MODID, "weather_config", FabricLoader.getInstance().getConfigDir(),
            FREEZE_TOP_LAYER_MAX_ACCUMULATION,
            MAX_SNOW_BUILDUP_STEP,
            MAX_SNOW_BUILDUP
    );

}
