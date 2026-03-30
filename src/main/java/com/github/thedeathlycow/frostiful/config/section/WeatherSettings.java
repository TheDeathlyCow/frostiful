package com.github.thedeathlycow.frostiful.config.section;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.Translate;
import com.github.thedeathlycow.frostiful.survival.wind.WindSpawnMethod;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.EnumCycler;
import dev.isxander.yacl3.config.v2.api.autogen.TickBox;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

import java.nio.file.Path;

public class WeatherSettings {
    public static final Path PATH = Frostiful.getConfigDir().resolve("common").resolve("weather.json5");

    public static final ConfigClassHandler<WeatherSettings> HANDLER = ConfigClassHandler.createBuilder(WeatherSettings.class)
            .id(Frostiful.id("common/weather"))
            .serializer(
                    config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(PATH)
                            .setJson5(true)
                            .build()
            )
            .build();

    public static final String ICICLE_CATEGORY = "icicle";
    public static final String WIND_CATEGORY = "wind";

    @AutoGen(category = WIND_CATEGORY)
    @Translate.Name("Freezing Wind spawning method")
    @SerialEntry(comment = "Sets how freezing winds should spawn. Points create small one-off explosions of wind, entity creates an entity that rolls across the landscape, and none disables the feature entirely.")
    @EnumCycler
    WindSpawnMethod freezingWindSpawningMethod = WindSpawnMethod.POINT;

    @AutoGen(category = WIND_CATEGORY)
    @Translate.Name("Enable freezing wind in the air")
    @SerialEntry(comment = "When enabled, allows freezing winds to appear high up in the air.")
    @TickBox
    boolean enableWindInTheAir = true;

    @AutoGen(category = WIND_CATEGORY)
    @Translate.Name("Wind destroys torches")
    @SerialEntry(comment = "When enabled, freezing winds will destroy exposed fire blocks (includes torches!).")
    @TickBox
    boolean freezingWindDestroysExposuedFire = true;

    public WindSpawnMethod freezingWindSpawningMethod() {
        return freezingWindSpawningMethod;
    }

    public boolean enableWindInTheAir() {
        return enableWindInTheAir;
    }

    public boolean freezingWindDestroysExposuedFire() {
        return freezingWindDestroysExposuedFire;
    }

    @AutoGen(category = ICICLE_CATEGORY)
    @Translate.Name("Enable icicle formation in weather")
    @SerialEntry(comment = "When enabled, icicles will form on the underside of full-face blocks near the surface of the world during weather.")
    @TickBox
    boolean iciclesFormInWeather = true;

    @AutoGen(category = ICICLE_CATEGORY)
    @Translate.Name("Enable icicle instability")
    @SerialEntry(comment = "When enabled, icicles hanging from a ceiling will have a chance to become unstable and fall.")
    @TickBox
    boolean iciclesBecomeUnable = true;

    public boolean iciclesFormInWeather() {
        return iciclesFormInWeather;
    }

    public boolean iciclesBecomeUnable() {
        return iciclesBecomeUnable;
    }
}