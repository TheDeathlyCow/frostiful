package com.github.thedeathlycow.frostiful.config.section;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.Translate;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
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