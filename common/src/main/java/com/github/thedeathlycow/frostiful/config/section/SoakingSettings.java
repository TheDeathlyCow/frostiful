package com.github.thedeathlycow.frostiful.config.section;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.Translate;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.FloatField;
import dev.isxander.yacl3.config.v2.api.autogen.IntField;
import dev.isxander.yacl3.config.v2.api.autogen.TickBox;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.util.Mth;

import java.nio.file.Path;

public class SoakingSettings {
    public static final Path PATH = Frostiful.getConfigDir().resolve("common").resolve("soaking.json5");

    public static final ConfigClassHandler<SoakingSettings> HANDLER = ConfigClassHandler.createBuilder(SoakingSettings.class)
            .id(Frostiful.id("common/soaking"))
            .serializer(
                    config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(PATH)
                            .setJson5(true)
                            .build()
            )
            .build();

    public static final String CATEGORY = FrostifulConfigYACL.MAIN_CATEGORY_NAME;
    public static final String SOAKING_GROUP = "soaking";
    public static final String DRYING_GROUP = "drying";

    @AutoGen(category = CATEGORY)
    @Translate.Name("Remove environment frost resistance when wet")
    @SerialEntry(comment = "When enabled, environment frost resistance will be set to 0 for players that are wet.")
    @TickBox
    boolean removeEnvironmentFrostResistanceWhenWet = true;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Maximum snow accumulation ticks")
    @SerialEntry(comment = "The maximum number of ticks to record players standing in snow for the purposes of soaking them after it melts. Must be at least 0.")
    @IntField(min = 0)
    int maxSnowAccumulationTicks = 100;

    public boolean removeEnvironmentFrostResistanceWhenWet() {
        return removeEnvironmentFrostResistanceWhenWet;
    }

    public int maxSnowAccumulationTicks() {
        return maxSnowAccumulationTicks;
    }

    @AutoGen(category = CATEGORY, group = SOAKING_GROUP)
    @Translate.Name("Rain soaking change multiplier")
    @SerialEntry(comment = "Multiplies the soaking change per tick applied to entities standing in the rain. Must be at least 0. Has no effect if Scorchful is loaded.")
    @FloatField(min = 0, format = "%.2f")
    float rainSoakingChangeMultiplier = 1.0f;

    @AutoGen(category = CATEGORY, group = SOAKING_GROUP)
    @Translate.Name("Standing in water soaking change multiplier")
    @SerialEntry(comment = "Multiplies the soaking change per tick applied to entities standing in water. Must be at least 0. Has no effect if Scorchful is loaded.")
    @FloatField(min = 0, format = "%.2f")
    float touchingWaterSoakingChangeMultiplier = 1.0f;

    @AutoGen(category = CATEGORY, group = SOAKING_GROUP)
    @Translate.Name("Splash potion soaking change multiplier")
    @SerialEntry(comment = "Multiplies the soaking change applied to entities struck by a splash potion. The base soaking change will be 50% of the affected player's maximum wet ticks. Must be at least 0. Has no effect if Scorchful is loaded.")
    @FloatField(min = 0, format = "%.2f")
    float splashPotionSoakingChangeMultiplier = 1.0f;

    public int rainWetnessIncrease() {
        return Mth.floor(rainSoakingChangeMultiplier);
    }

    public int touchingWaterWetnessIncrease() {
        return Mth.floor(5 * this.touchingWaterSoakingChangeMultiplier);
    }

    public int soakingFromSplashPotion(int maxSoakingTicks) {
        return Mth.floor(0.5f * maxSoakingTicks * this.splashPotionSoakingChangeMultiplier);
    }

    @AutoGen(category = CATEGORY, group = DRYING_GROUP)
    @Translate.Name("Enable light drying")
    @SerialEntry(comment = "When enabled, entities will be able to be dried from being the presence of block light. The strength of the drying increases linearly with the block light level. Has no effect if Scorchful is loaded.")
    @TickBox
    private boolean enableLightDrying = true;

    @AutoGen(category = CATEGORY, group = DRYING_GROUP)
    @Translate.Name("On fire dry rate")
    @SerialEntry(comment = "Rate at which to decrease soaking points per tick to entities on fire. Must be at least 0. Has no effect if Scorchful is loaded.")
    @FloatField(min = 0, format = "%.2f")
    float onFireSoakingChangeMultiplier = 1.0f;

    public boolean enableLightDrying() {
        return enableLightDrying;
    }

    public int onFireDryDate() {
        return Mth.floor(50f * this.onFireSoakingChangeMultiplier);
    }
}