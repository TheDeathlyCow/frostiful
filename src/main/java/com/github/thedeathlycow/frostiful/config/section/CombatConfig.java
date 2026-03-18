package com.github.thedeathlycow.frostiful.config.section;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.Translate;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.*;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.minecraft.util.Mth;

import java.nio.file.Path;

public class CombatConfig {
    public static final Path PATH = Frostiful.getConfigDir().resolve("common").resolve("combat.json5");

    public static final ConfigClassHandler<CombatConfig> HANDLER = ConfigClassHandler.createBuilder(CombatConfig.class)
            .id(Frostiful.id("common/combat"))
            .serializer(
                    config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(PATH)
                            .setJson5(true)
                            .build()
            )
            .build();

    private static final String CATEGORY = FrostifulConfigYACL.MAIN_CATEGORY_NAME;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Enable Chillager patrols")
    @SerialEntry(comment = "When enabled, Pillagers are replaced by Chillagers when Patrols spawn in snowy areas.")
    @TickBox
    boolean doChillagerPatrols = true;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Strays Carry Glacial Arrows")
    @SerialEntry(comment = "When enabled, Strays will fire Glacial Arrows instead of Slowness arrows.")
    @TickBox
    boolean straysCarryFrostArrows = true;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Maximum Frost Spell distance")
    @SerialEntry(comment = "The maximum distance (in blocks) that a spell fired from a Frost Wand can travel before exploding. Must be at least 1.")
    @DoubleField(min = 1)
    double maxFrostSpellDistance = 25;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Maximum Frost Spell distance")
    @SerialEntry(comment = "The cooldown time (in ticks) of the Frost Wand after casting a spell. Must be at least 0.")
    @IntField(min = 0)
    int frostWandCooldown = 120;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Maximum Frost Spell distance")
    @SerialEntry(comment = "The time (in ticks) that an entity struct by a Frost Wand is rooted. Must be at least 1.")
    @IntField(min = 1)
    int frostWandRootTime = 100;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Frostologer Heat Drain per tick")
    @SerialEntry(comment = "How many temperature points the Frostologer removes from nearby entities each tick when casting their Blizzard spell.")
    @IntField
    int frostologerHeatDrainPerTick = 30;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Frostologer cooling from Frost Wand hit")
    @SerialEntry(comment = "How many temperature points the Frostologer removes from a themselves after hitting a target with a Frost Spell.")
    @IntField
    int frostologerCoolingFromFrostWandHit = 6300 / 6;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Packed Snowball freeze amount")
    @SerialEntry(comment = "How many temperature points a Packed Snowball removes from a target when hit.")
    @IntField
    int packedSnowballFreezeAmount = 500;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Packed Snowball Damage")
    @SerialEntry(comment = "How much damage a Packed Snowball applies to a target when hit. Must be at least 0.")
    @FloatField(min = 0)
    float packedSnowballDamage = 2.0f;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Packed Snowball Damage")
    @SerialEntry(comment = "How much damage a Packed Snowball applies to a target that is a vulnerable type (Strider, Blaze, Magma Cube) when hit. Must be at least 0.")
    @FloatField(min = 0)
    float packedSnowballVulnerableTypesDamage = 5.0f;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Biter Frost Bite Max amplifier")
    @SerialEntry(comment = "The maximum possible amplifier of the Frost Bite effect that is applied to frozen/rooted targets when a Biter attacks. Must be at least 0.")
    @IntField(min = 0)
    int biterFrostBiteMaxAmplifier = 2;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Chillager Fire Damage multiplier")
    @SerialEntry(comment = "Multiplies all incoming Fire-based damage applied to Chillagers.")
    @FloatField
    float chillagerFireDamageMultiplier = 1.5f;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Frostologer Fire Damage multiplier")
    @SerialEntry(comment = "Multiplies all incoming Fire-based damage applied to Frostologers.")
    @FloatField
    float frostologerFireDamageMultiplier = 2.0f;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Ice Skate Upgrade Template generation chance in Igloos")
    @SerialEntry(comment = "The chance of an Ice Skate Upgrade Template generating in an Igloo chest. Requires a restart after changing!")
    @FloatField(format = "%.2f")
    float skateUpgradeTemplateIglooGenerateChance = 0.75f;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Ice Break fallback damage")
    @SerialEntry(comment = "The fallback damage amount to use when breaking a Frost Wand spell if the attacker does not have the Ice Break damage attribute.")
    @DoubleField(format = "%.2f")
    double iceBreakFallbackDamage = 3.0;


    public boolean doChillagerPatrols() {
        return doChillagerPatrols;
    }

    public boolean straysCarryFrostArrows() {
        return straysCarryFrostArrows;
    }

    public double getMaxFrostSpellDistance() {
        return maxFrostSpellDistance;
    }

    public int getFrostWandCooldown() {
        return frostWandCooldown;
    }

    public int getFrostWandRootTime() {
        return frostWandRootTime;
    }

    public int getFrostologerHeatDrainPerTick() {
        // multiply by 2 as goals only twice at half the rate of normal
        return 2 * frostologerHeatDrainPerTick;
    }

    public int getFrostologerCoolingFromFrostWandHit() {
        return frostologerCoolingFromFrostWandHit;
    }

    public int getPackedSnowballFreezeAmount() {
        return packedSnowballFreezeAmount;
    }

    public float getPackedSnowballDamage() {
        return packedSnowballDamage;
    }

    public float getPackedSnowballVulnerableTypesDamage() {
        return packedSnowballVulnerableTypesDamage;
    }

    public int getBiterFrostBiteMaxAmplifier() {
        return Math.max(0, this.biterFrostBiteMaxAmplifier);
    }

    public float getChillagerFireDamageMultiplier() {
        return chillagerFireDamageMultiplier;
    }

    public float getFrostologerFireDamageMultiplier() {
        return frostologerFireDamageMultiplier;
    }

    public float getSkateUpgradeTemplateIglooGenerateChance() {
        return Mth.clamp(skateUpgradeTemplateIglooGenerateChance, 0f, 1f);
    }

    public double getIceBreakFallbackDamage() {
        return iceBreakFallbackDamage;
    }
}