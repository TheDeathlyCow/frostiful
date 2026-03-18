package com.github.thedeathlycow.frostiful.config.section;

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

public class ClientConfig {
    public static final Path PATH = Frostiful.getConfigDir().resolve("client.json5");

    public static final ConfigClassHandler<ClientConfig> HANDLER = ConfigClassHandler.createBuilder(ClientConfig.class)
            .id(Frostiful.id("client"))
            .serializer(
                    config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(PATH)
                            .setJson5(true)
                            .build()
            )
            .build();

    private static final String CATEGORY = FrostifulConfigYACL.MAIN_CATEGORY_NAME;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Frost overlay start")
    @SerialEntry(comment = "The temperature scale below which the frosty overlay will begin to appear. Must be between -1 and 0 (inclusive).")
    @FloatSlider(min = -1f, max = 0f, step = 0.1f)
    float frostOverlayStart = -0.5f;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Render cold heart overlay")
    @SerialEntry(comment = "Toggles the temperature display over the player health.")
    @TickBox
    boolean doColdHeartOverlay = true;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Render water drip particles when wet")
    @SerialEntry(comment = "Toggles the water drip particles when wet. Has no effect if Scorchful is installed.")
    @TickBox
    boolean renderDripParticles = true;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Disable frost overlay when wearing Cloak of Frostology")
    @SerialEntry
    @Translate.NoComment
    @TickBox
    boolean disableFrostOverlayWhenWearingFrostologyCloak = true;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Shake hand when shivering")
    @SerialEntry(comment = "Whether to shake the player's hand in first person when cold.")
    @TickBox
    boolean shakeHandWhenShivering = true;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Hand shake intensity")
    @SerialEntry(comment = "How intense the hand shaking should be when shivering.")
    @FloatField
    float handShakeIntensity = 1.0f;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Disable hurt Polar Bear skin")
    @SerialEntry(comment = "When Polar Bears have been recently brushed, they show a hurt skin. Disable this feature if the texture is not compatible with your resource pack.")
    boolean disableHurtPolarBearSkin = false;

    public float getFrostOverlayStart() {
        return frostOverlayStart;
    }

    public boolean doColdHeartOverlay() {
        return doColdHeartOverlay;
    }

    public boolean renderDripParticles() {
        return renderDripParticles;
    }

    public boolean isDisableFrostOverlayWhenWearingFrostologyCloak() {
        return disableFrostOverlayWhenWearingFrostologyCloak;
    }

    public boolean isShakeCameraWhenShiveringEnabled() {
        return shakeHandWhenShivering;
    }

    public float getHandShakeIntensity() {
        return handShakeIntensity;
    }

    public boolean isDisableHurtPolarBearSkin() {
        return disableHurtPolarBearSkin;
    }
}