package com.github.thedeathlycow.frostiful.config;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.simple.config.Config;
import com.github.thedeathlycow.simple.config.ConfigFactory;
import com.github.thedeathlycow.simple.config.entry.ConfigEntry;
import com.github.thedeathlycow.simple.config.entry.DoubleEntry;
import com.github.thedeathlycow.simple.config.entry.IntegerEntry;
import net.fabricmc.loader.api.FabricLoader;

public class AttributeConfig {


    public static final ConfigEntry<Double> PERCENT_FROST_REDUCTION_PER_FROST_RESISTANCE = new DoubleEntry("percent_frost_reduction_per_frost_resistance", 10.0D, 0.0D, 100.0D);
    public static final ConfigEntry<Integer> MAX_FROST_MULTIPLIER = new IntegerEntry("max_frost_multiplier", 140, 1);
    public static final ConfigEntry<Double> BASE_ENTITY_FROST_RESISTANCE = new DoubleEntry("base_entity_frost_resistance", 0.0D, 0.0D);
    public static final ConfigEntry<Double> BASE_PLAYER_FROST_RESISTANCE = new DoubleEntry("base_player_frost_resistance", 0.0D, 0.0D);
    public static final ConfigEntry<Double> ENTITY_MAX_FROST = new DoubleEntry("entity_max_frost", 20.0D, 0.0D);
    public static final ConfigEntry<Double> PLAYER_MAX_FROST = new DoubleEntry("player_max_frost", 25.0D, 0.0D);
    public static final Config CONFIG = ConfigFactory.createConfigWithKeys(
            Frostiful.MODID, "attribute_config", FabricLoader.getInstance().getConfigDir(),
            PERCENT_FROST_REDUCTION_PER_FROST_RESISTANCE,
            MAX_FROST_MULTIPLIER,
            BASE_ENTITY_FROST_RESISTANCE,
            BASE_PLAYER_FROST_RESISTANCE,
            ENTITY_MAX_FROST,
            PLAYER_MAX_FROST
    );
}
