package com.github.thedeathlycow.lostinthecold.config;

import com.github.thedeathlycow.datapack.config.config.Config;
import com.github.thedeathlycow.datapack.config.config.ConfigFactory;
import com.github.thedeathlycow.datapack.config.config.key.*;
import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;

public class ConfigKeys {
    public static final ConfigKey<Integer> TICKS_PER_FROST_RESISTANCE = new IntegerKey("ticks_per_frost_resistance", 1200, 0);
    public static final ConfigKey<Double> BASE_ENTITY_FROST_RESISTANCE = new DoubleKey("base_entity_frost_resistance", 0.11666666666667D, 0.0D);
    public static final ConfigKey<Double> BASE_PLAYER_FROST_RESISTANCE = new DoubleKey("base_player_frost_resistance", 3.0D, 0.0D);
    public static final ConfigKey<Integer> CHILLY_BIOME_FREEZE_RATE = new IntegerKey("chilly_biome_freeze_rate", 1, 0);
    public static final ConfigKey<Integer> COLD_BIOME_FREEZE_RATE = new IntegerKey("cold_biome_freeze_rate", 2, 0);
    public static final ConfigKey<Integer> FREEZING_BIOME_FREEZE_RATE = new IntegerKey("freezing_biome_freeze_rate", 5, 0);
    public static final ConfigKey<Double> WET_FREEZE_RATE_MULTIPLIER = new DoubleKey("wet_freeze_rate_multiplier", 1.5D, 1.0D);
    public static final ConfigKey<Integer> WARM_BIOME_THAW_RATE = new IntegerKey("warm_biome_thaw_rate", 10, 0);
    public static final ConfigKey<Integer> ON_FIRE_THAW_RATE = new IntegerKey("on_fire_thaw_rate", 100, 0);
    public static final ConfigKey<Integer> WARMTH_PER_LIGHT_LEVEL = new IntegerKey("warmth_per_light_level", 2, 0);
    public static final ConfigKey<Integer> MIN_WARMTH_LIGHT_LEVEL = new IntegerKey("min_warmth_light_level", 7, 0);
    public static final ConfigKey<Integer> FREEZE_DAMAGE_AMOUNT = new IntegerKey("freeze_damage_amount", 1, 0);
    public static final ConfigKey<Integer> FREEZE_EXTRA_DAMAGE_AMOUNT = new IntegerKey("freeze_extra_damage_amount", 5, 0);
    public static final ConfigKey<Integer> POWDER_SNOW_INCREASE_PER_TICK = new IntegerKey("powder_snow_increase_per_tick", 100, 0);
    public static final ConfigKey<Byte> FREEZE_TOP_LAYER_MAX_ACCUMULATION = new ByteKey("freeze_top_layer_max_accumulation", (byte) 2, (byte) 0, (byte) 8);
    public static final ConfigKey<Byte> MAX_SNOW_BUILDUP_STEP = new ByteKey("max_snow_buildup_step", (byte) 2, (byte) 1, (byte) 8);

    public static Config createConfig() {
        return ConfigFactory.createConfigWithKeys(
                LostInTheCold.MODID, "config",
                TICKS_PER_FROST_RESISTANCE,
                BASE_ENTITY_FROST_RESISTANCE,
                BASE_PLAYER_FROST_RESISTANCE,
                CHILLY_BIOME_FREEZE_RATE,
                COLD_BIOME_FREEZE_RATE,
                FREEZING_BIOME_FREEZE_RATE,
                WET_FREEZE_RATE_MULTIPLIER,
                WARM_BIOME_THAW_RATE,
                ON_FIRE_THAW_RATE,
                WARMTH_PER_LIGHT_LEVEL,
                MIN_WARMTH_LIGHT_LEVEL,
                FREEZE_DAMAGE_AMOUNT,
                FREEZE_EXTRA_DAMAGE_AMOUNT,
                POWDER_SNOW_INCREASE_PER_TICK,
                FREEZE_TOP_LAYER_MAX_ACCUMULATION,
                MAX_SNOW_BUILDUP_STEP
        );
    }
}
