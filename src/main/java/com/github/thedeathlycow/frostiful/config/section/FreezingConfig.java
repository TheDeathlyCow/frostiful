package com.github.thedeathlycow.frostiful.config.section;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.Translate;
import com.github.thedeathlycow.frostiful.survival.wind.WindSpawnStrategies;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.*;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.util.Mth;

import java.nio.file.Path;

public class FreezingConfig {
    public static final Path PATH = Frostiful.getConfigDir().resolve("common").resolve("freezing.json5");

    public static final ConfigClassHandler<FreezingConfig> HANDLER = ConfigClassHandler.createBuilder(FreezingConfig.class)
            .id(Frostiful.id("common/environment"))
            .serializer(
                    config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(PATH)
                            .setJson5(true)
                            .build()
            )
            .build();

    private static final String CATEGORY = FrostifulConfigYACL.MAIN_CATEGORY_NAME;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Enable passive freezing (global)")
    @SerialEntry(comment = "When enabled, players will receive environmental temperature reductions from exposure in cold biomes. This can also be toggled on a per-world basis with the game rule frostiful:do_passive_freezing.")
    @TickBox
    boolean doPassiveFreezing = true;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Enable wind spawning")
    @SerialEntry(comment = "When enabled, freezing winds will appear in the world.")
    @TickBox
    boolean doWindSpawning = true;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Wind spawn method")
    @SerialEntry(comment = "If wind spawning is enabled, then this controls how they spawn. Points create small one-off explosions of wind, entity creates an entity that rolls across the landscape, and none disables the feature entirely.")
    @EnumCycler
    WindSpawnStrategies windSpawnStrategy = WindSpawnStrategies.POINT;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Spawn wind in air")
    @SerialEntry(comment = "Allows freezing winds to appear high up in the air.")
    @TickBox
    boolean spawnWindInAir = true;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Wind destroys torches")
    @SerialEntry(comment = "When enabled, freezing winds will destroy exposed fire blocks.")
    @TickBox
    boolean windDestroysTorches = true;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Enable snow packing")
    @SerialEntry(comment = "When enabled, heavy mobs like Iron Golems and Ravagers will compact the snow they walk on, turning it to Packed Snow.")
    @TickBox
    boolean doSnowPacking = true;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Passive freezing tick interval")
    @SerialEntry(comment = "The interval, in ticks, between applications of passive freezing to players. Must be at least 0.")
    @IntField(min = 0)
    int passiveFreezingTickInterval = 1;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Wind spawn cap (per second)")
    @SerialEntry(comment = "The maximum number of freezing winds that can spawn on a server, per second.")
    @IntField(min = 0)
    int windSpawnCapPerSecond = 15;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Wind spawn rarity")
    @SerialEntry(comment = "Controls how often freezing winds spawn during rain. Bigger numbers = less frequent wind. Must be at least 0.")
    @IntField(min = 0)
    int windSpawnRarity = 750;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Wind spawn rarity in thunder")
    @SerialEntry(comment = "Controls how often freezing winds spawn during thunder. Bigger numbers = less frequent wind. Must be at least 0.")
    @IntField(min = 0)
    int windSpawnRarityThunder = 500;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Max passive freezing temperature scale")
    @SerialEntry(comment = "Does not allow an entity to be frozen if their freezing temperature scale is greater than this number. Must be between 0 and 1 (inclusive)")
    @FloatSlider(min = 0f, max = 1f, step = 0.05f, format = "%.2f")
    float maxPassiveFreezingPercent = 1.0f;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Max soaking from water potion (percent)")
    @SerialEntry(comment = "Soaking percent from Splash Water Potion. Must be between 0 and 1 (inclusive)")
    @FloatSlider(min = 0f, max = 1f, step = 0.05f, format = "%.2f")
    float soakPercentFromWaterPotion = 0.5f;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Sun Lichen heat per level")
    @SerialEntry(comment = "How many temperature points to add to entities that touch Sun Lichen, per level of heat. Must be at least 0.")
    @IntField(min = 0)
    int sunLichenHeatPerLevel = 500;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Sun Lichen burn time")
    @SerialEntry(comment = "How long (in ticks) to burn entities that touch Sun Lichen. Must be at least 0.")
    @IntField(min = 0)
    int sunLichenBurnTime = 3 * 20;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Campfire warmth search radius")
    @SerialEntry(comment = "The radius around a campfire to apply the Warmth effect to when adding a log. Must be at least 0.")
    @DoubleField(min = 0)
    double campfireWarmthSearchRadius = 10;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Campfire warmth time")
    @SerialEntry(comment = "Duration of the Warmth effect, in ticks, to apply to close to a campfire after adding a log. Must be at least 0.")
    @IntField(min = 0)
    int campfireWarmthTime = 1200;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Freezing wind temperature reduction")
    @SerialEntry(comment = "How many temperature points to remove from entities that collide with a Freezing Wind. Must be at least 0.")
    @IntField(min = 0)
    int freezingWindFrost = 160;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Conduit warmth per tick")
    @SerialEntry(comment = "How many temperature points to add each tick to underwater entities with the Conduit Power effect. Must be at least 0.")
    @IntField(min = 0)
    int conduitPowerWarmthPerTick = 12;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Heat from hot floor")
    @SerialEntry(comment = "How many temperature points to add each tick to entities that are standing on hot floor blocks like Magma. Must be at least 0.")
    @IntField(min = 0)
    int heatFromHotFloor = 12;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Shiver below")
    @SerialEntry(comment = "The temperature scale below which entities will begin to shiver. Must be between -1 and 0 (inclusive).")
    @FloatSlider(min = -1f, max = 0f, step = 0.05f, format = "%.2f")
    float shiverBelow = -0.51f;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Shivering warmth")
    @SerialEntry(comment = "How many temperature points to add each tick to entities that are shivering. Must be at least 0.")
    @IntField(min = 0)
    // TODO: this option needs synchronization for rendering
    int shiverWarmth = 1;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Stop shivering below food level")
    @SerialEntry(comment = "When a player is below this food level, the player will stop receiving warmth from shivering to prevent starvation. Must be between 0 and 20 (inclusive).")
    @IntSlider(min = 0, max = 20, step = 1)
    int stopShiverWarmingBelowFoodLevel = 10;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Warm food warmth time")
    @SerialEntry(comment = "Duration, in ticks, of the warmth effect to apply to entities after consuming food items that belong to the tag #frostiful:warm_foods")
    @IntSlider(min = 0, max = 20, step = 1)
    int warmFoodWarmthTime = 60 * 20;

    public boolean doPassiveFreezing() {
        return doPassiveFreezing;
    }

    public WindSpawnStrategies getWindSpawnStrategy() {
        if (!doWindSpawning) {
            return WindSpawnStrategies.NONE;
        }

        return windSpawnStrategy;
    }

    public boolean spawnWindInAir() {
        return spawnWindInAir;
    }

    public boolean isWindDestroysTorches() {
        return windDestroysTorches;
    }

    public boolean doSnowPacking() {
        return doSnowPacking;
    }

    public int getPassiveFreezingTickInterval() {
        return passiveFreezingTickInterval;
    }

    public int getWindSpawnCapPerSecond() {
        return windSpawnCapPerSecond;
    }

    public int getWindSpawnRarity() {
        return windSpawnRarity;
    }

    public int getWindSpawnRarityThunder() {
        return windSpawnRarityThunder;
    }

    public float getMaxPassiveFreezingPercent() {
        return maxPassiveFreezingPercent;
    }

    public float getSoakPercentFromWaterPotion() {
        return Mth.clamp(soakPercentFromWaterPotion, 0.0f, 1.0f);
    }

    public int getSunLichenHeatPerLevel() {
        return sunLichenHeatPerLevel;
    }

    public int getSunLichenBurnTime() {
        return sunLichenBurnTime;
    }

    public double getCampfireWarmthSearchRadius() {
        return campfireWarmthSearchRadius;
    }

    public int getCampfireWarmthTime() {
        return campfireWarmthTime;
    }

    public int getFreezingWindFrost() {
        return freezingWindFrost;
    }

    public int getConduitWarmthPerTick() {
        return conduitPowerWarmthPerTick;
    }

    public int getHeatFromHotFloor() {
        return heatFromHotFloor;
    }

    public float getShiverBelow() {
        return shiverBelow;
    }

    public int getShiverWarmth() {
        return shiverWarmth;
    }

    public int getStopShiverWarmingBelowFoodLevel() {
        return stopShiverWarmingBelowFoodLevel;
    }

    public int getWarmFoodWarmthTime() {
        return warmFoodWarmthTime;
    }
}