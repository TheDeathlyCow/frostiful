package com.github.thedeathlycow.frostiful.config;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.simple.config.Config;
import com.github.thedeathlycow.simple.config.ConfigFactory;
import com.github.thedeathlycow.simple.config.entry.ConfigEntry;
import com.github.thedeathlycow.simple.config.entry.FloatEntry;
import com.github.thedeathlycow.simple.config.entry.IntegerEntry;
import net.fabricmc.loader.api.FabricLoader;

public class IcicleConfig {

    public static final ConfigEntry<Float> BECOME_UNSTABLE_CHANCE = new FloatEntry("become_unstable_chance", 0.05f, 0.0f, 1.0f);
    public static final ConfigEntry<Float> GROW_CHANCE = new FloatEntry("grow_chance", 0.02f, 0.0f, 1.0f);
    public static final ConfigEntry<Float> GROW_CHANGE_DURING_RAIN = new FloatEntry("grow_chance_during_rain", 0.09f, 0.0f, 1.0f);
    public static final ConfigEntry<Float> GROW_CHANCE_DURING_THUNDER = new FloatEntry("grow_chance_during_thunder", 0.15f, 0.0f, 1.0f);
    public static final ConfigEntry<Integer> FROST_ARROW_FREEZE_AMOUNT = new IntegerEntry("frost_arrow_freeze_amount", 100, 0, 5000);

    public static final Config CONFIG = ConfigFactory.createConfigWithKeys(
            Frostiful.MODID, "icicle_config", FabricLoader.getInstance().getConfigDir(),
            BECOME_UNSTABLE_CHANCE,
            GROW_CHANCE,
            GROW_CHANGE_DURING_RAIN,
            GROW_CHANCE_DURING_THUNDER,
            FROST_ARROW_FREEZE_AMOUNT
    );

}
