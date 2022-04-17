package com.github.thedeathlycow.lostinthecold.config;

public class ConfigKeys {
    public static final ConfigKey<Integer> TICKS_PER_FROST_RESISTANCE;
    public static final ConfigKey<Double> BASE_ENTITY_FROST_RESISTANCE;
    public static final ConfigKey<Double> BASE_PLAYER_FROST_RESISTANCE;
    public static final ConfigKey<Integer> CHILLY_BIOME_FREEZE_RATE;
    public static final ConfigKey<Integer> COLD_BIOME_FREEZE_RATE;
    public static final ConfigKey<Integer> FREEZING_BIOME_FREEZE_RATE;
    public static final ConfigKey<Double> WET_FREEZE_RATE_MULTIPLIER;
    public static final ConfigKey<Integer> WARM_BIOME_THAW_RATE;
    public static final ConfigKey<Integer> ON_FIRE_THAW_RATE;
    public static final ConfigKey<Integer> WARMTH_PER_LIGHT_LEVEL;
    public static final ConfigKey<Integer> MIN_WARMTH_LIGHT_LEVEL;
    public static final ConfigKey<Integer> FREEZE_DAMAGE_AMOUNT;
    public static final ConfigKey<Integer> FREEZE_EXTRA_DAMAGE_AMOUNT;
    public static final ConfigKey<Integer> POWDER_SNOW_INCREASE_PER_TICK;
    public static final ConfigKey<Integer> POWDER_SNOW_DECREASE_PER_TICK;
    public static final ConfigKey<Boolean> DO_RANDOM_SNOW_GENERATION;
    public static final ConfigKey<Byte> MAX_SNOW_BUILDUP_STEP;

    public static final ConfigKeyRegistry REGISTRY = new ConfigKeyRegistry();

    static {
        TICKS_PER_FROST_RESISTANCE = REGISTRY.register(new IntegerKey("ticks_per_frost_resistance", 1200, 0));
        BASE_ENTITY_FROST_RESISTANCE = REGISTRY.register(new DoubleKey("base_entity_frost_resistance", 0.11666666666667D, 0.0D));
        BASE_PLAYER_FROST_RESISTANCE = REGISTRY.register( new DoubleKey("base_player_frost_resistance", 3.0D, 0.0D));
        CHILLY_BIOME_FREEZE_RATE = REGISTRY.register(new IntegerKey("chilly_biome_freeze_rate", 1, 0));
        COLD_BIOME_FREEZE_RATE = REGISTRY.register(new IntegerKey("cold_biome_freeze_rate", 2, 0));
        FREEZING_BIOME_FREEZE_RATE = REGISTRY.register(new IntegerKey("freezing_biome_freeze_rate", 5, 0));
        WET_FREEZE_RATE_MULTIPLIER = REGISTRY.register(new DoubleKey("wet_freeze_rate_multiplier", 1.5D, 1.0D));
        WARM_BIOME_THAW_RATE = REGISTRY.register(new IntegerKey("warm_biome_thaw_rate", 10, 0));
        ON_FIRE_THAW_RATE = REGISTRY.register(new IntegerKey("on_fire_thaw_rate", 100, 0));
        WARMTH_PER_LIGHT_LEVEL = REGISTRY.register(new IntegerKey("warmth_per_light_level", 2, 0));
        MIN_WARMTH_LIGHT_LEVEL = REGISTRY.register(new IntegerKey("min_warmth_light_level", 7, 0));
        FREEZE_DAMAGE_AMOUNT = REGISTRY.register(new IntegerKey("freeze_damage_amount", 1, 0));
        FREEZE_EXTRA_DAMAGE_AMOUNT = REGISTRY.register(new IntegerKey("freeze_extra_damage_amount", 5, 0));
        POWDER_SNOW_INCREASE_PER_TICK = REGISTRY.register(new IntegerKey("powder_snow_increase_per_tick", 100, 0));
        POWDER_SNOW_DECREASE_PER_TICK = REGISTRY.register(new IntegerKey("powder_snow_decrease_per_tick", 100, 0));
        DO_RANDOM_SNOW_GENERATION = REGISTRY.register(new BooleanKey("do_random_snow_generation", true));
        MAX_SNOW_BUILDUP_STEP = REGISTRY.register(new ByteKey("max_snow_buildup_step", (byte)2, (byte)1, (byte)8));
    }
}
