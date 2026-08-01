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

public class ItemSettings {
    public static final Path PATH = Frostiful.getConfigDir().resolve("common").resolve("item.json5");

    public static final ConfigClassHandler<ItemSettings> HANDLER = ConfigClassHandler.createBuilder(ItemSettings.class)
            .id(Frostiful.id("common/item"))
            .serializer(
                    config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(PATH)
                            .setJson5(true)
                            .build()
            )
            .build();

    public static final String CATEGORY = FrostifulConfigYACL.MAIN_CATEGORY_NAME;
    public static final String FROST_WAND_GROUP = "frost_wand";
    public static final String PACKED_SNOWBALL_GROUP = "packed_snowball";
    public static final String ICICLE_GROUP = "icicle_snowball";
    public static final String MISC_GROUP = "misc";

    @AutoGen(category = CATEGORY, group = FROST_WAND_GROUP)
    @Translate.Name("Frost Wand cooldown multiplier")
    @SerialEntry(comment = "Multiplies the cooldown time of the Frost Wand after casting a spell. Must be at least 0.")
    @FloatField(min = 0, format = "%.2f")
    float frostWandCooldownMultiplier = 1.0f;

    @AutoGen(category = CATEGORY, group = FROST_WAND_GROUP)
    @Translate.Name("Frost Wand root time multiplier")
    @SerialEntry(comment = "Multiplies the time that an entity struck by a Frost Wand is rooted in place. Must be at least 0.")
    @FloatField(min = 0, format = "%.2f")
    float frostWandRootTimeMultiplier = 1.0f;

    @AutoGen(category = CATEGORY, group = FROST_WAND_GROUP)
    @Translate.Name("Maximum Frost Spell distance multiplier")
    @SerialEntry(comment = "Multiplies the maximum distance (in blocks) that a spell fired from a Frost Wand can travel before exploding. Must be at least 0.")
    @DoubleField(min = 0)
    double maxFrostSpellDistanceMultiplier = 1.0;

    public int frostWandCooldown() {
        return Mth.floor(120 * this.frostWandCooldownMultiplier);
    }

    public int frostWandRootTime() {
        return Mth.floor(100 * this.frostWandRootTimeMultiplier);
    }

    public double maxFrostSpellDistance() {
        return Mth.floor(25 * this.maxFrostSpellDistanceMultiplier);
    }

    @AutoGen(category = CATEGORY, group = PACKED_SNOWBALL_GROUP)
    @Translate.Name("Packed Snowball Damage")
    @SerialEntry(comment = "How much damage a Packed Snowball applies to targets. Must be at least 0.")
    @FloatField(min = 0)
    float packedSnowballDamage = 2.0f;

    @AutoGen(category = CATEGORY, group = PACKED_SNOWBALL_GROUP)
    @Translate.Name("Packed Snowball vulnerable types damage")
    @SerialEntry(comment = "How much damage a Packed Snowball applies to a target that is a vulnerable type (Strider, Blaze, Magma Cube) when hit. Must be at least 0.")
    @FloatField(min = 0)
    float packedSnowballVulnerableTypesDamage = 5.0f;

    public float packedSnowballDamage() {
        return packedSnowballDamage;
    }

    public float packedSnowballVulnerableTypesDamage() {
        return packedSnowballVulnerableTypesDamage;
    }

    @AutoGen(category = CATEGORY, group = ICICLE_GROUP)
    @Translate.Name("Enable Icicle throwing")
    @SerialEntry(comment = "When enabled, icicles will be able to be thrown.")
    @TickBox
    boolean enableIcicleThrowing = true;

    @AutoGen(category = CATEGORY, group = ICICLE_GROUP)
    @Translate.Name("Thrown Icicle damage")
    @SerialEntry(comment = "The damage a thrown Icicle applies to targets. Must be at least 0.")
    @FloatField(min = 0f)
    float thrownIcicleDamage = 1.0f;

    @AutoGen(category = CATEGORY, group = ICICLE_GROUP)
    @Translate.Name("Thrown Icicle vulnerable types damage")
    @SerialEntry(comment = "How much damage a thrown Icicle applies to a target that is a vulnerable type (Strider, Blaze, Magma Cube) when hit. Must be at least 0.")
    @FloatField(min = 0f)
    float thrownIcicleVulnerableTypesDamage = 3.0f;

    public boolean enableIcicleThrowing() {
        return enableIcicleThrowing;
    }

    public float thrownIcicleDamage() {
        return thrownIcicleDamage;
    }

    public float thrownIcicleVulnerableTypesDamage() {
        return thrownIcicleVulnerableTypesDamage;
    }

    @AutoGen(category = CATEGORY, group = MISC_GROUP)
    @Translate.Name("Ice Skate Upgrade Template generation chance in Igloos")
    @SerialEntry(comment = "The chance of an Ice Skate Upgrade Template generating in an Igloo chest. Must be between 0 and 1 (inclusive). Requires a restart after changing!")
    @FloatSlider(format = "%.2f", min = 0f, max = 1f, step = 0.05f)
    float skateUpgradeTemplateIglooGenerateChance = 0.75f;

    @AutoGen(category = CATEGORY, group = MISC_GROUP)
    @Translate.Name("Warming Food duration multiplier")
    @SerialEntry(comment = "Multiplies the duration of the Warmth mob effect applied to entities after consuming food items that belong to the item tag #frostiful:warm_foods")
    @FloatField(min = 0)
    float warmingFoodDurationMultiplier = 1.0f;

    public float skateUpgradeTemplateIglooGenerateChance() {
        return skateUpgradeTemplateIglooGenerateChance;
    }

    public int warmingFoodDuration() {
        return Mth.floor(60 * 20 * warmingFoodDurationMultiplier);
    }
}