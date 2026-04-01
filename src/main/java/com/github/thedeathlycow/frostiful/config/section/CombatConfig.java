package com.github.thedeathlycow.frostiful.config.section;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.Translate;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.IntField;
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
}