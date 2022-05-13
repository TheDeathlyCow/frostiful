package com.github.thedeathlycow.frostiful.config;

import com.github.thedeathlycow.frostiful.config.type.FrostStatusEffectEntry;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.util.survival.FrostStatusEffect;
import com.github.thedeathlycow.simple.config.Config;
import com.github.thedeathlycow.simple.config.ConfigFactory;
import com.github.thedeathlycow.simple.config.entry.ConfigEntry;
import com.github.thedeathlycow.simple.config.entry.DoubleEntry;
import com.github.thedeathlycow.simple.config.entry.FloatEntry;
import com.github.thedeathlycow.simple.config.entry.IntegerEntry;
import com.github.thedeathlycow.simple.config.entry.collection.ListEntry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.effect.StatusEffects;

import java.util.List;

public class FreezingConfig {

    public static final ConfigEntry<Integer> BIOME_TEMPERATURE_MULTIPLIER = new IntegerEntry("biome_temperature_multiplier", 4, 0);
    public static final ConfigEntry<Float> PASSIVE_FREEZING_START_TEMP = new FloatEntry("passive_freezing_start_temp", 0.25f, -1.0f, 1.0f);
    public static final ConfigEntry<Double> WET_FREEZE_RATE_MULTIPLIER = new DoubleEntry("wet_freeze_rate_multiplier", 1.5D, 1.0D);
    public static final ConfigEntry<Integer> CANNOT_FREEZE_WARM_RATE = new IntegerEntry("warm_biome_thaw_rate", 10, 0);
    public static final ConfigEntry<Integer> ON_FIRE_THAW_RATE = new IntegerEntry("on_fire_thaw_rate", 100, 0);
    public static final ConfigEntry<Integer> WARMTH_PER_LIGHT_LEVEL = new IntegerEntry("warmth_per_light_level", 2, 0);
    public static final ConfigEntry<Integer> MIN_WARMTH_LIGHT_LEVEL_DAY = new IntegerEntry("min_warmth_light_level_day", 7, 0);
    public static final ConfigEntry<Integer> MIN_WARMTH_LIGHT_LEVEL_NIGHT = new IntegerEntry("min_warmth_light_level_night", 10, 0);
    public static final ConfigEntry<Integer> FREEZE_DAMAGE_AMOUNT = new IntegerEntry("freeze_damage_amount", 2, 0);
    public static final ConfigEntry<Integer> FREEZE_EXTRA_DAMAGE_AMOUNT = new IntegerEntry("freeze_extra_damage_amount", 5, 0);
    public static final ConfigEntry<Integer> POWDER_SNOW_INCREASE_PER_TICK = new IntegerEntry("powder_snow_increase_per_tick", 20, 0);
    public static final ListEntry<FrostStatusEffect> PASSIVE_FREEZING_EFFECTS = new FrostStatusEffectEntry("passive_freezing_effects",
            List.of(
                    new FrostStatusEffect(0.33, StatusEffects.WEAKNESS, 100, 0),
                    new FrostStatusEffect(0.5, StatusEffects.MINING_FATIGUE, 100, 0),
                    new FrostStatusEffect(0.67, StatusEffects.WEAKNESS, 100, 1),
                    new FrostStatusEffect(0.75, StatusEffects.MINING_FATIGUE, 100, 1),
                    new FrostStatusEffect(0.99, StatusEffects.MINING_FATIGUE, 100, 2)
            )
    );

    public static final Config CONFIG = ConfigFactory.createConfigWithKeys(
            Frostiful.MODID, "freezing_config", FabricLoader.getInstance().getConfigDir(),
            BIOME_TEMPERATURE_MULTIPLIER,
            PASSIVE_FREEZING_START_TEMP,
            WET_FREEZE_RATE_MULTIPLIER,
            CANNOT_FREEZE_WARM_RATE,
            ON_FIRE_THAW_RATE,
            WARMTH_PER_LIGHT_LEVEL,
            MIN_WARMTH_LIGHT_LEVEL_DAY,
            MIN_WARMTH_LIGHT_LEVEL_NIGHT,
            FREEZE_DAMAGE_AMOUNT,
            FREEZE_EXTRA_DAMAGE_AMOUNT,
            POWDER_SNOW_INCREASE_PER_TICK,
            PASSIVE_FREEZING_EFFECTS
    );
}
