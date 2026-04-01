package com.github.thedeathlycow.frostiful.config.section;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.Translate;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.FloatField;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.util.Mth;

import java.nio.file.Path;

public class TemperatureSourceSettings {
    public static final Path PATH = Frostiful.getConfigDir().resolve("common").resolve("temperature_source.json5");

    public static final ConfigClassHandler<TemperatureSourceSettings> HANDLER = ConfigClassHandler.createBuilder(TemperatureSourceSettings.class)
            .id(Frostiful.id("common/temperature_source"))
            .serializer(
                    config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(PATH)
                            .setJson5(true)
                            .build()
            )
            .build();

    public static final String GENERAL_CATEGORY = "general";
    public static final String TEMPERATURE_SOURCES_CATEGORY = "temperature_sources";


    @AutoGen(category = GENERAL_CATEGORY)
    @Translate.Name("Heating multiplier")
    @SerialEntry(comment = "Multiplies the final temperature point change of all non-environment heating sources like fire and sun lichen.")
    @FloatField(min = 0.0f, format = "%.2f")
    float heatingMultiplier = 1.0f;

    @AutoGen(category = GENERAL_CATEGORY)
    @Translate.Name("Cooling multiplier")
    @SerialEntry(comment = "Multiplies the final temperature point change of all non-environment cooling sources like powder snow and glacial arrows.")
    @FloatField(min = 0.0f, format = "%.2f")
    float coolingMultiplier = 1.0f;

    @AutoGen(category = GENERAL_CATEGORY)
    @Translate.Name("Environment temperature multiplier")
    @SerialEntry(comment = "Multiplies the final temperature point change of an environment temperature change.")
    @FloatField(min = 0.0f, format = "%.2f")
    float environmentTemperatureMultiplier = 1.0f;

    public float heatingMultiplier() {
        return heatingMultiplier;
    }

    public float coolingMultiplier() {
        return coolingMultiplier;
    }

    public float environmentTemperatureMultiplier() {
        return environmentTemperatureMultiplier;
    }

    @AutoGen(category = TEMPERATURE_SOURCES_CATEGORY)
    @Translate.Name("Frostologer Heat Drain multiplier multiplier")
    @SerialEntry(comment = "Multiplies the number of temperature points the Frostologer removes from nearby entities each tick when casting their Blizzard spell.")
    @FloatField(format = "%.2f")
    float frostologerHeatDrainMultiplier = 1.0f;

    @AutoGen(category = TEMPERATURE_SOURCES_CATEGORY)
    @Translate.Name("Frostologer cooling from Frost Wand hit multiplier")
    @SerialEntry(comment = "Multiplies the number of temperature points the Frostologer removes from a themselves after hitting a target with a Frost Spell.")
    @FloatField(format = "%.2f")
    float frostologerCoolingFromFrostWandHitMultiplier = 1.0f;

    @AutoGen(category = TEMPERATURE_SOURCES_CATEGORY)
    @Translate.Name("Packed Snowball freeze amount multiplier")
    @SerialEntry(comment = "Multiplies the number of temperature points a Packed Snowball removes from a target when hit.")
    @FloatField(format = "%.2f")
    float packedSnowballFreezeAmount = 1.0f;

    public int frostologerHeatDrain() {
        // multiply by 2 as goals run at only half the rate of normal
        return 2 * Mth.floor(30 * this.frostologerHeatDrainMultiplier * this.coolingMultiplier());
    }

    public int frostologerCoolingFromFrostWandHit() {
        return Mth.floor(-6300f / 6f * this.frostologerCoolingFromFrostWandHitMultiplier * this.coolingMultiplier());
    }

    public int packedSnowballFreezeAmount() {
        return Mth.floor(-500 * this.packedSnowballFreezeAmount * this.coolingMultiplier());
    }
}