package com.github.thedeathlycow.frostiful.config.section;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.Translate;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.*;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

import java.nio.file.Path;

public class IcicleConfig {
    public static final Path PATH = Frostiful.getConfigDir().resolve("common").resolve("icicle.json5");

    public static final ConfigClassHandler<IcicleConfig> HANDLER = ConfigClassHandler.createBuilder(IcicleConfig.class)
            .id(Frostiful.id("common/icicle"))
            .serializer(
                    config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(PATH)
                            .setJson5(true)
                            .build()
            )
            .build();

    private static final String CATEGORY = FrostifulConfigYACL.MAIN_CATEGORY_NAME;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Icicles form in weather")
    @SerialEntry(comment = "When enabled, icicles will form on the underside of full-face blocks near the surface of the world during weather.")
    @TickBox
    boolean iciclesFormInWeather = true;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Become unstable chance")
    @SerialEntry(comment = "The chance that any part of an icicle will become unstable and fall. Applies only during a random tick. Must be between 0 and 1 (inclusive).")
    @DoubleSlider(min = 0.0f, max = 1.0f, step = 0.05f)
    double becomeUnstableChance = 0.05;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Grow chance")
    @SerialEntry(comment = "The chance that any part of an icicle will attempt to grow the icicle during clear weather. Applies only during a random tick. Must be between 0 and 1 (inclusive).")
    @DoubleSlider(min = 0.0f, max = 1.0f, step = 0.05f)
    double growChance = 0.02;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Grow chance during rain")
    @SerialEntry(comment = "The chance that any part of an icicle will attempt to grow the icicle during rainy, but not thundering weather. Applies only during a random tick. Must be between 0 and 1 (inclusive).")
    @DoubleSlider(min = 0.0f, max = 1.0f, step = 0.05f)
    double growChanceDuringRain = 0.09;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Grow chance during thunder")
    @SerialEntry(comment = "The chance that any part of an icicle will attempt to grow the icicle during thundering weather. Applies only during a random tick. Must be between 0 and 1 (inclusive).")
    @DoubleSlider(min = 0.0f, max = 1.0f, step = 0.05f)
    double growChanceDuringThunder = 0.15;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Glacial Arrow freezing")
    @SerialEntry(comment = "The temperature point reduction applied to a target struck by a Glacial Arrow. Must be at least 0.")
    @IntField(min = 0)
    int frostArrowFreezeAmount = 1000;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Thrown Icicle freezing")
    @SerialEntry(comment = "The temperature point reduction applied to a target struck by a Thrown Icicle. Must be at least 0.")
    @IntField(min = 0)
    int thrownIcicleFreezeAmount = 1500;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Icicle Collision freezing")
    @SerialEntry(comment = "The temperature point reduction applied to a target that falls on an icicle or is struck by a falling icicle. Must be at least 0.")
    @IntField(min = 0)
    int icicleCollisionFreezeAmount = 3000;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Max block light level to form")
    @SerialEntry(comment = "The maximum block light level that icicles are allowed to form at. Must be between 0 and 15 (inclusive).")
    @IntSlider(min = 0, max = 15, step = 1)
    int maxLightLevelToForm = 8;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Min sky light level to form")
    @SerialEntry(comment = "The minimum sky light level that icicles are required to form at. Must be between 0 and 15 (inclusive).")
    @IntSlider(min = 0, max = 15, step = 1)
    int minSkylightLevelToForm = 11;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Thrown Icicle damage")
    @SerialEntry(comment = "The damage a Thrown Icicle applies to targets. Must be at least 0.")
    @FloatField(min = 0f)
    float thrownIcicleDamage = 1.0f;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Thrown Icicle extra damage")
    @SerialEntry(comment = "The damage a Thrown Icicle applies to entity types that are vulnerable to freezing damage (Magma Cube, Blaze, Striders). Must be at least 0.")
    @FloatField(min = 0f)
    float thrownIcicleExtraDamage = 3.0f;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Thrown Icicle cooldown")
    @SerialEntry(comment = "The cooldown, in ticks, of an icicle after throwing it. Must be at least 0.")
    @IntField(min = 0)
    int thrownIcicleCooldown = 10;

    public boolean iciclesFormInWeather() {
        return iciclesFormInWeather;
    }

    public double getBecomeUnstableChance() {
        return becomeUnstableChance;
    }

    public double getGrowChance() {
        return growChance;
    }

    public double getGrowChanceDuringRain() {
        return growChanceDuringRain;
    }

    public double getGrowChanceDuringThunder() {
        return growChanceDuringThunder;
    }

    public int getFrostArrowFreezeAmount() {
        return frostArrowFreezeAmount;
    }

    public int getThrownIcicleFreezeAmount() {
        return thrownIcicleFreezeAmount;
    }

    public int getIcicleCollisionFreezeAmount() {
        return icicleCollisionFreezeAmount;
    }

    public int getMaxLightLevelToForm() {
        return maxLightLevelToForm;
    }

    public int getMinSkylightLevelToForm() {
        return minSkylightLevelToForm;
    }

    public float getThrownIcicleDamage() {
        return thrownIcicleDamage;
    }

    public float getThrownIcicleExtraDamage() {
        return thrownIcicleExtraDamage;
    }

    public int getThrownIcicleCooldown() {
        return thrownIcicleCooldown;
    }
}