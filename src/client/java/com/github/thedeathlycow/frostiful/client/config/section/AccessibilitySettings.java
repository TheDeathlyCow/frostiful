package com.github.thedeathlycow.frostiful.client.config.section;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.Translate;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.FloatField;
import dev.isxander.yacl3.config.v2.api.autogen.TickBox;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

import java.nio.file.Path;

public class AccessibilitySettings {
    public static final Path PATH = Frostiful.getConfigDir().resolve("client/accessibility.json5");

    public static final ConfigClassHandler<AccessibilitySettings> HANDLER = ConfigClassHandler.createBuilder(AccessibilitySettings.class)
            .id(Frostiful.id("client/accessibility"))
            .serializer(
                    config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(PATH)
                            .setJson5(true)
                            .build()
            )
            .build();

    private static final String CATEGORY = FrostifulConfigYACL.MAIN_CATEGORY_NAME;

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

    public boolean shakeHandWhenShivering() {
        return shakeHandWhenShivering;
    }

    public float handShakeIntensity() {
        return handShakeIntensity;
    }
}