package com.github.thedeathlycow.frostiful.config;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.simple.config.Config;
import com.github.thedeathlycow.simple.config.ConfigFactory;
import com.github.thedeathlycow.simple.config.entry.ConfigEntry;
import com.github.thedeathlycow.simple.config.entry.DoubleEntry;
import com.github.thedeathlycow.simple.config.entry.IntegerEntry;
import net.fabricmc.loader.api.FabricLoader;

public class IcicleConfig {

    public static final ConfigEntry<Double> BECOME_UNSTABLE_CHANCE = new DoubleEntry("become_unstable_chance", 0.01D, 0.0D, 1.0D);
    public static final ConfigEntry<Double> GROW_CHANCE = new DoubleEntry("grow_chance", 0.02D, 0.0D, 1.0D);
    public static final ConfigEntry<Integer> FROST_ARROW_FREEZE_AMOUNT = new IntegerEntry("frost_arrow_freeze_amount", 100, 0, 5000);
    public static final Config CONFIG = ConfigFactory.createConfigWithKeys(
            Frostiful.MODID, "icicle_config", FabricLoader.getInstance().getConfigDir(),
            BECOME_UNSTABLE_CHANCE,
            GROW_CHANCE,
            FROST_ARROW_FREEZE_AMOUNT
    );

}
