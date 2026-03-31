package com.github.thedeathlycow.frostiful.config.section;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.Translate;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.*;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;

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

    public boolean doChillagerPatrols() {
        return doChillagerPatrols;
    }

    public boolean straysCarryFrostArrows() {
        return straysCarryFrostArrows;
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

    public int getBiterFrostBiteMaxAmplifier() {
        return Math.max(0, this.biterFrostBiteMaxAmplifier);
    }

    public float getChillagerFireDamageMultiplier() {
        return chillagerFireDamageMultiplier;
    }

    public float getFrostologerFireDamageMultiplier() {
        return frostologerFireDamageMultiplier;
    }
}