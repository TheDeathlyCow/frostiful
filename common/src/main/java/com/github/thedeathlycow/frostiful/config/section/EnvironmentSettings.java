package com.github.thedeathlycow.frostiful.config.section;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.Translate;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.*;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.util.Mth;

import java.nio.file.Path;

public class EnvironmentSettings {
    public static final Path PATH = Frostiful.getConfigDir().resolve("common").resolve("environment.json5");

    public static final ConfigClassHandler<EnvironmentSettings> HANDLER = ConfigClassHandler.createBuilder(EnvironmentSettings.class)
            .id(Frostiful.id("common/environment"))
            .serializer(
                    config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(PATH)
                            .setJson5(true)
                            .build()
            )
            .build();

    public static final String CATEGORY = FrostifulConfigYACL.MAIN_CATEGORY_NAME;
    public static final String TEMPERATURE_GROUP = "temperature";
    public static final String SHIVERING_GROUP = "shivering";

    @AutoGen(category = CATEGORY)
    @Translate.Name("Enable environment freezing (global)")
    @SerialEntry(comment = "When enabled, players will receive environmental temperature reductions from exposure in cold biomes. This can also be toggled on a per-world basis with the game rule frostiful:enable_environment_freezing.")
    @TickBox
    boolean enableEnvironmentFreezing = true;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Environment freezing tick interval")
    @SerialEntry(comment = "The interval, in ticks, between applications of environmental freezing to players. Must be at least 0.")
    @IntField(min = 0)
    int environmentFreezingTickInterval = 1;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Minimum environmental freezing temperature scale")
    @SerialEntry(comment = "The minimum temperature scale to which environmental freezing will cool a player. Must be between -1 and 0 (inclusive)")
    @FloatSlider(min = -1f, max = 0f, step = 0.05f, format = "%.2f")
    float minEnvironmentalFreezingTemperatureScale = -1.0f;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Environment temperature multiplier")
    @SerialEntry(comment = "Multiplies the temperature point reduction from environmental freezing.")
    @FloatField(min = 0.0f, format = "%.2f")
    float environmentTemperatureMultiplier = 1.0f;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Minimum light level for warmth")
    @SerialEntry(comment = "The minimum block light level that is considered to be well-lit and warm. Must be at least 0.")
    @IntField(min = 0)
    int minLightForWarmth = 5;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Environment freezing soaked multiplier")
    @SerialEntry(comment = "Multiples the temperature reduction per tick of entities that are wet. Must be at least 0.")
    @FloatField(min = 0)
    float environmentFreezingSoakedMultiplier = 2.0f;

    public boolean enableEnvironmentFreezing() {
        return enableEnvironmentFreezing;
    }

    public int environmentFreezingTickInterval() {
        return environmentFreezingTickInterval;
    }

    public float minEnvironmentalFreezingTemperatureScale() {
        return minEnvironmentalFreezingTemperatureScale;
    }

    public float environmentTemperatureMultiplier() {
        return environmentTemperatureMultiplier;
    }

    public int minLightForWarmth() {
        return minLightForWarmth;
    }

    public float environmentFreezingSoakedMultiplier() {
        return environmentFreezingSoakedMultiplier;
    }

    @AutoGen(category = CATEGORY, group = TEMPERATURE_GROUP)
    @Translate.Name("Max temperature for cold (Celsius)")
    @SerialEntry(comment = "The maximum temperature that can be cold, in Celsius. May not exceed 15°C.")
    @DoubleField(max = 15)
    double maxTemperatureForColdC = 10.0;

    @AutoGen(category = CATEGORY, group = TEMPERATURE_GROUP)
    @Translate.Name("Degrees Celsius per temperature level decrease")
    @SerialEntry(comment = "How many degrees Celsius per temperature level decrease. Smaller numbers makes colder areas freeze players faster. Must be positive.")
    @DoubleField(min = 0.01)
    double degreesCPerTemperatureDecrease = 10.0;

    public double maxTemperatureForColdC() {
        return maxTemperatureForColdC;
    }

    public double degreesCPerTemperatureDecrease() {
        return degreesCPerTemperatureDecrease;
    }

    @AutoGen(category = CATEGORY, group = SHIVERING_GROUP)
    @Translate.Name("Shiver below temperature change")
    @SerialEntry(comment = "The temperature scale below which entities will begin to shiver. Must be between -1 and 0 (inclusive).")
    @FloatSlider(min = -1f, max = 0f, step = 0.05f, format = "%.2f")
    // TODO: this option needs synchronization for rendering
    float shiverBelowTemperatureScale = -0.51f;

    @AutoGen(category = CATEGORY, group = SHIVERING_GROUP)
    @Translate.Name("Shivering warmth multiplier")
    @SerialEntry(comment = "Multiplies the heating applied each tick to entities that are shivering. Note that the hunger cost of shivering also scales with this multiplier. Must be at least 0.")
    @FloatField(format = "%.2f")
    float shiveringWarmthMultiplier = 1.0f;

    @AutoGen(category = CATEGORY, group = SHIVERING_GROUP)
    @Translate.Name("Shivering warming minimum food level")
    @SerialEntry(comment = "When a player is below this food level, the player will stop consuming hunger and stop being warmed by shivering to prevent starvation. Must be between 0 and 20 (inclusive).")
    @IntSlider(min = 0, max = 20, step = 1)
    int shiverWarmingMinFoodLevel = 10;

    public float shiverBelowTemperatureScale() {
        return shiverBelowTemperatureScale;
    }

    public int shiveringTemperatureChange(TemperatureSourceSettings temperatureSourceSettings) {
        return Mth.floor(shiveringWarmthMultiplier * temperatureSourceSettings.heatingMultiplier());
    }

    public int stopShiverWarmingBelowFoodLevel() {
        return shiverWarmingMinFoodLevel;
    }
}