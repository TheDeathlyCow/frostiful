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
    public static final String COOLING_GROUP = "cooling";
    public static final String HEATING_GROUP = "heating";

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
    @SerialEntry(comment = "Multiplies the temperature point reduction from environmental freezing.")
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

    @AutoGen(category = TEMPERATURE_SOURCES_CATEGORY, group = COOLING_GROUP)
    @Translate.Name("Powder Snow temperature change multiplier")
    @SerialEntry(comment = "Multiplies the cooling applied to entities in powder snow each tick.")
    @FloatField(format = "%.2f")
    float powderSnowTemperatureChangeMultiplier = 1.0f;

    @AutoGen(category = TEMPERATURE_SOURCES_CATEGORY, group = COOLING_GROUP)
    @Translate.Name("Frostologer Heat Drain temperature change multiplier")
    @SerialEntry(comment = "Multiplies the number of temperature points the Frostologer removes from nearby entities each tick when casting their Blizzard spell.")
    @FloatField(format = "%.2f")
    float frostologerBlizzardPlayerTemperatureChangeMultiplier = 1.0f;

    @AutoGen(category = TEMPERATURE_SOURCES_CATEGORY, group = COOLING_GROUP)
    @Translate.Name("Packed Snowball temperature change multiplier")
    @SerialEntry(comment = "Multiplies cooling applied by a Packed Snowball to a target when hit.")
    @FloatField(format = "%.2f")
    float packedSnowballTemperatureChangeMultiplier = 1.0f;

    @AutoGen(category = TEMPERATURE_SOURCES_CATEGORY, group = COOLING_GROUP)
    @Translate.Name("Glacial Arrow temperature change multiplier")
    @SerialEntry(comment = "Multiplies the cooling applied to a target struck by a Glacial Arrow.")
    @FloatField(format = "%.2f")
    float glacialArrowTemperatureChangeMultiplier = 1.0f;

    @AutoGen(category = TEMPERATURE_SOURCES_CATEGORY, group = COOLING_GROUP)
    @Translate.Name("Thrown Icicle temperature change multiplier")
    @SerialEntry(comment = "Multiplies the cooling applied to a target struck by a Thrown Icicle.")
    @FloatField(format = "%.2f")
    float thrownIcicleTemperatureChangeMultiplier = 1.0f;

    @AutoGen(category = TEMPERATURE_SOURCES_CATEGORY, group = COOLING_GROUP)
    @Translate.Name("Icicle collision temperature change multiplier")
    @SerialEntry(comment = "Multiplies the cooling applied to a target that falls on an icicle or is struck by a falling icicle.")
    @FloatField(format = "%.2f")
    float icicleCollisionTemperatureChangeMultiplier = 1.0f;

    @AutoGen(category = TEMPERATURE_SOURCES_CATEGORY, group = COOLING_GROUP)
    @Translate.Name("Freezing wind temperature change multiplier")
    @SerialEntry(comment = "Multiplies the cooling applied to entities that collide with Freezing Wind.")
    @FloatField(format = "%.2f")
    float freezingWindTemperatureChangeMultiplier = 1.0f;

    @AutoGen(category = TEMPERATURE_SOURCES_CATEGORY, group = HEATING_GROUP)
    @Translate.Name("Sun Lichen temperature change multiplier")
    @SerialEntry(comment = "Multiplies the heating applied to entities that touch Sun Lichen.")
    @FloatField(format = "%.2f")
    float sunLichenTemperatureChangeMultiplier = 1.0f;

    @AutoGen(category = TEMPERATURE_SOURCES_CATEGORY, group = HEATING_GROUP)
    @Translate.Name("Conduit temperature change multiplier")
    @SerialEntry(comment = "Multiplies the heating applied each tick to underwater entities with the Conduit Power effect.")
    @FloatField(format = "%.2f")
    float conduitPowerTemperatureChangeMultiplier = 1.0f;

    @AutoGen(category = TEMPERATURE_SOURCES_CATEGORY, group = HEATING_GROUP)
    @Translate.Name("Hot floor temperature change multiplier")
    @SerialEntry(comment = "Multiplies the heating applied each tick to entities that are standing on hot floor blocks like Magma.")
    @FloatField(format = "%.2f")
    float hotFloorTemperatureChangeMultiplier = 1.0f;

    @AutoGen(category = TEMPERATURE_SOURCES_CATEGORY, group = HEATING_GROUP)
    @Translate.Name("On fire temperature change multiplier")
    @SerialEntry(comment = "Multiplies the heating applied each tick to entities that are on fire.")
    @FloatField(format = "%.2f")
    float onFireTemperatureChangeMultiplier = 1.0f;

    @AutoGen(category = TEMPERATURE_SOURCES_CATEGORY, group = HEATING_GROUP)
    @Translate.Name("Light level temperature change multiplier")
    @SerialEntry(comment = "Multiplies the heating applied each tick to entities standing in an area with high artificial/block light.")
    @FloatField(format = "%.2f")
    float lightTemperatureChangeMultiplier = 1.0f;

    public int powderSnowTemperatureChange() {
        return Mth.floor(-30 * this.powderSnowTemperatureChangeMultiplier * this.coolingMultiplier());
    }

    public int frostologerBlizzardPlayerTemperatureChange() {
        // multiply by 2 as goals run at only half the rate of normal
        return 2 * Mth.floor(-30 * this.frostologerBlizzardPlayerTemperatureChangeMultiplier * this.coolingMultiplier());
    }

    public int packedSnowballTemperatureChange() {
        return Mth.floor(-500 * this.packedSnowballTemperatureChangeMultiplier * this.coolingMultiplier());
    }

    public int glacialArrowTemperatureChange() {
        return Mth.floor(-1000 * this.glacialArrowTemperatureChangeMultiplier * this.coolingMultiplier());
    }

    public int thrownIcicleTemperatureChange() {
        return Mth.floor(-1500 * this.thrownIcicleTemperatureChangeMultiplier * this.coolingMultiplier());
    }

    public int icicleCollisionTemperatureChange() {
        return Mth.floor(-3000 * this.icicleCollisionTemperatureChangeMultiplier * this.coolingMultiplier());
    }

    public int freezingWindTemperatureChange() {
        return Mth.floor(-160 * this.freezingWindTemperatureChangeMultiplier * this.coolingMultiplier());
    }

    public int sunLichenTemperatureChangeForLevel(int level) {
        return level * Mth.floor(500 * this.sunLichenTemperatureChangeMultiplier * this.heatingMultiplier());
    }

    public int conduitPowerTemperatureChange() {
        return Mth.floor(12 * this.conduitPowerTemperatureChangeMultiplier * this.heatingMultiplier());
    }

    public int hotFloorTemperatureChange() {
        return Mth.floor(12 * this.hotFloorTemperatureChangeMultiplier * this.heatingMultiplier());
    }

    public int onFireTemperatureChange() {
        return Mth.floor(50 * this.onFireTemperatureChangeMultiplier * this.heatingMultiplier());
    }

    public int lightTemperatureChangeForLevel(int lightLevel) {
        return lightLevel * Mth.floor(2 * this.lightTemperatureChangeMultiplier * this.heatingMultiplier());
    }
}