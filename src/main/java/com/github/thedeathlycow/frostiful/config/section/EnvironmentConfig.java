package com.github.thedeathlycow.frostiful.config.section;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.Translate;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.*;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

import java.nio.file.Path;

public class EnvironmentConfig {
    public static final Path PATH = Frostiful.getConfigDir().resolve("common").resolve("environment.json5");

    public static final ConfigClassHandler<EnvironmentConfig> HANDLER = ConfigClassHandler.createBuilder(EnvironmentConfig.class)
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
    @Translate.Name("Max temperature for code (Celsius)")
    @SerialEntry(comment = "The maximum temperature that can be cold, in Celsius. May not exceed 15°C.")
    @DoubleField(max = 15)
    double maxTemperatureForColdC = 10.0;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Degrees Celsius per temperature level decrease")
    @SerialEntry(comment = "How many degrees Celsius per temperature level decrease. Smaller numbers makes colder areas freeze players faster. Must be positive.")
    @DoubleField(min = 0.01)
    double degreesCPerTemperatureDecrease = 10.0;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Environment temperature multiplier")
    @SerialEntry(comment = "Multiplies the temperature point reduction from environmental freezing.")
    @DoubleField
    double environmentTemperatureMultiplier = 1.0;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Apply environment penalty when wet")
    @SerialEntry(comment = "When enabled, increases the rate of environmental freezing on wet players.")
    @TickBox
    boolean applyEnvironmentPenaltyWhenWet = true;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Rain wetness increase")
    @SerialEntry(comment = "Rate at which to increase soaking points per tick to entities standing in rain. Must be at least 0. Has no effect if Scorchful is loaded.")
    @IntField(min = 0)
    int rainWetnessIncrease = 1;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Standing in water wetness increase")
    @SerialEntry(comment = "Rate at which to increase soaking points per tick to entities standing in water. Must be at least 0. Has no effect if Scorchful is loaded.")
    @IntField(min = 0)
    int touchingWaterWetnessIncrease = 5;

    @AutoGen(category = CATEGORY)
    @Translate.Name("On fire dry rate")
    @SerialEntry(comment = "Rate at which to decrease soaking points per tick to entities on fire. Must be at least 0. Has no effect if Scorchful is loaded.")
    @IntField(min = 0)
    int onFireDryDate = 50;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Minimum light level for warmth")
    @SerialEntry(comment = "The minimum block light level that is considered to be well-lit and warm. Must be at least 0.")
    @IntField(min = 0)
    int minLightForWarmth = 5;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Maximum snow accumulation ticks")
    @SerialEntry(comment = "The maximum number of ticks to record players standing in snow for the purposes of soaking them after it melts. Must be at least 0.")
    @IntField(min = 0)
    int maxSnowAccumulationTicks = 100;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Environment freezing soaked multiplier")
    @SerialEntry(comment = "Multiples the temperature reduction per tick of entities that are wet. Must be at least 0.")
    @FloatField(min = 0)
    float environmentFreezingSoakedMultiplier = 2.0f;

    public double getMaxTemperatureForColdC() {
        return maxTemperatureForColdC;
    }

    public double getDegreesCPerTemperatureDecrease() {
        return degreesCPerTemperatureDecrease;
    }

    public double getEnvironmentTemperatureMultiplier() {
        return environmentTemperatureMultiplier;
    }

    public boolean applyEnvironmentPenaltyWhenWet() {
        return applyEnvironmentPenaltyWhenWet;
    }

    public int getRainWetnessIncrease() {
        return rainWetnessIncrease;
    }

    public int getTouchingWaterWetnessIncrease() {
        return touchingWaterWetnessIncrease;
    }

    public int getOnFireDryDate() {
        return onFireDryDate;
    }

    public int getMinLightForWarmth() {
        return minLightForWarmth;
    }

    public int getMaxSnowAccumulationTicks() {
        return maxSnowAccumulationTicks;
    }

    public float getEnvironmentFreezingSoakedMultiplier() {
        return environmentFreezingSoakedMultiplier;
    }
}