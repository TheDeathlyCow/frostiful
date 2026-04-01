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

public class FreezingConfig {
    public static final Path PATH = Frostiful.getConfigDir().resolve("common").resolve("freezing.json5");

    public static final ConfigClassHandler<FreezingConfig> HANDLER = ConfigClassHandler.createBuilder(FreezingConfig.class)
            .id(Frostiful.id("common/freezing"))
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
    @Translate.Name("Passive freezing tick interval")
    @SerialEntry(comment = "The interval, in ticks, between applications of passive freezing to players. Must be at least 0.")
    @IntField(min = 0)
    int passiveFreezingTickInterval = 1;

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
    @Translate.Name("Shiver below")
    @SerialEntry(comment = "The temperature scale below which entities will begin to shiver. Must be between -1 and 0 (inclusive).")
    @FloatSlider(min = -1f, max = 0f, step = 0.05f, format = "%.2f")
    // TODO: this option needs synchronization for rendering
    float shiverBelow = -0.51f;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Shivering warmth")
    @SerialEntry(comment = "How many temperature points to add each tick to entities that are shivering. Must be at least 0.")
    @IntField(min = 0)
    int shiverWarmth = 1;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Stop shivering below food level")
    @SerialEntry(comment = "When a player is below this food level, the player will stop receiving warmth from shivering to prevent starvation. Must be between 0 and 20 (inclusive).")
    @IntSlider(min = 0, max = 20, step = 1)
    int stopShiverWarmingBelowFoodLevel = 10;

    public boolean doPassiveFreezing() {
        return doPassiveFreezing;
    }

    public int getPassiveFreezingTickInterval() {
        return passiveFreezingTickInterval;
    }

    public float getMaxPassiveFreezingPercent() {
        return maxPassiveFreezingPercent;
    }

    public float getSoakPercentFromWaterPotion() {
        return Mth.clamp(soakPercentFromWaterPotion, 0.0f, 1.0f);
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
}