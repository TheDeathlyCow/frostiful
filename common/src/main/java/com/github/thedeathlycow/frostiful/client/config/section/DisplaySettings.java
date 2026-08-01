package com.github.thedeathlycow.frostiful.client.config.section;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.Translate;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.FloatField;
import dev.isxander.yacl3.config.v2.api.autogen.FloatSlider;
import dev.isxander.yacl3.config.v2.api.autogen.TickBox;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

import java.nio.file.Path;

public class DisplaySettings {
    public static final Path PATH = Frostiful.getConfigDir().resolve("client/display.json5");

    public static final ConfigClassHandler<DisplaySettings> HANDLER = ConfigClassHandler.createBuilder(DisplaySettings.class)
            .id(Frostiful.id("client/display"))
            .serializer(
                    config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(PATH)
                            .setJson5(true)
                            .build()
            )
            .build();

    private static final String CATEGORY = FrostifulConfigYACL.MAIN_CATEGORY_NAME;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Enable frosty heart overlay")
    @SerialEntry(comment = "Toggle the frosty heart temperature display on the health bar.")
    @TickBox
    boolean enableFrostyHeartOverlay = true;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Frosty camera overlay temperature scale start")
    @SerialEntry(comment = "The temperature scale below which the frosty camera overlay will begin to appear. Must be between -1 and 0 (inclusive).")
    @FloatSlider(min = -1f, max = 0f, step = 0.1f)
    float renderFrostyCameraOverlayBelow = -0.5f;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Enable drip particles")
    @SerialEntry(comment = "Toggles the water drip particles when wet. Has no effect if Scorchful is installed.")
    @TickBox
    boolean enableDripParticles = true;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Disable hurt Polar Bear skin")
    @SerialEntry(comment = "When Polar Bears have been recently brushed, they show a hurt skin. Disable this feature if the texture is not compatible with your resource pack.")
    @TickBox
    boolean disableHurtPolarBearSkin = false;

    public boolean enableFrostyHeartOverlay() {
        return enableFrostyHeartOverlay;
    }

    public float frostOverlayStart() {
        return renderFrostyCameraOverlayBelow;
    }

    public boolean enableDripParticles() {
        return enableDripParticles;
    }

    public boolean disableHurtPolarBearSkin() {
        return disableHurtPolarBearSkin;
    }
}