package com.github.thedeathlycow.frostiful.config.section;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.DifficultySetting;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.Translate;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.EnumCycler;
import dev.isxander.yacl3.config.v2.api.autogen.FloatField;
import dev.isxander.yacl3.config.v2.api.autogen.TickBox;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.world.level.Level;

import java.nio.file.Path;

public class EntitySettings {
    public static final Path PATH = Frostiful.getConfigDir().resolve("common").resolve("entity.json5");

    public static final ConfigClassHandler<EntitySettings> HANDLER = ConfigClassHandler.createBuilder(EntitySettings.class)
            .id(Frostiful.id("common/entity"))
            .serializer(
                    config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(PATH)
                            .setJson5(true)
                            .build()
            )
            .build();

    public static final String CATEGORY = FrostifulConfigYACL.MAIN_CATEGORY_NAME;
    public static final String CHILLAGER_GROUP = "chillager";
    public static final String MISC_GROUP = "misc";

    @AutoGen(category = CATEGORY, group = CHILLAGER_GROUP)
    @Translate.Name("Enable Chillager patrols")
    @SerialEntry(comment = "When enabled, Pillagers are replaced by Chillagers when Patrols spawn in snowy areas.")
    @TickBox
    boolean enableChillagerPatrols = true;

    @AutoGen(category = CATEGORY, group = CHILLAGER_GROUP)
    @Translate.Name("Chillager Fire Damage multiplier")
    @SerialEntry(comment = "Multiplies all incoming Fire-based damage applied to Chillagers.")
    @FloatField
    float chillagerFireDamageMultiplier = 1.5f;

    @AutoGen(category = CATEGORY, group = CHILLAGER_GROUP)
    @Translate.Name("Frostologer Fire Damage multiplier")
    @SerialEntry(comment = "Multiplies all incoming Fire-based damage applied to Frostologers.")
    @FloatField
    float frostologerFireDamageMultiplier = 2.0f;

    public boolean enableChillagerPatrols() {
        return enableChillagerPatrols;
    }

    public float chillagerFireDamageMultiplier() {
        return chillagerFireDamageMultiplier;
    }

    public float frostologerFireDamageMultiplier() {
        return frostologerFireDamageMultiplier;
    }

    @AutoGen(category = CATEGORY, group = MISC_GROUP)
    @Translate.Name("Frost Bite amplifier difficulty")
    @SerialEntry(comment = "Controls the amplifier of the Frost Bite effect applied by Biters. When set to automatic, the amplifier scales with world difficulty (0 in easy, 1 in normal, 3 in hard). Otherwise, locks the amplifier to the chosen difficulty's value regardless of world difficulty. Note that peaceful is not allowed, as it would have no effect.")
    @EnumCycler/*(allowedOrdinals = {0, 2, 3, 4})*/ // FIXME: bugged behavior in YACL, uncomment when YACL fixes this https://github.com/isXander/YetAnotherConfigLib/issues/332
    DifficultySetting frostBiteAmplifierDifficulty = DifficultySetting.AUTOMATIC;

    @AutoGen(category = CATEGORY, group = MISC_GROUP)
    @Translate.Name("Strays Carry Glacial Arrows")
    @SerialEntry(comment = "When enabled, Strays will fire Glacial Arrows instead of Slowness arrows. Strays will drop both Slowness and Glacial arrows regardless of what this option is set to.")
    @TickBox
    boolean straysCarryGlacialArrows = true;

    @AutoGen(category = CATEGORY, group = MISC_GROUP)
    @Translate.Name("Enable heavy mob snow packing")
    @SerialEntry(comment = "When enabled, heavy mobs like Iron Golems and Ravagers will compact the snow they walk on, turning it to Packed Snow.")
    @TickBox
    boolean enableHeavyMobSnowPacking = true;

    public int getFrostBiteAmplifier(Level level) {
        DifficultySetting setting = this.frostBiteAmplifierDifficulty.getDifficultySetting(level);

        return switch (setting) {
            case NORMAL -> 1;
            case HARD -> 3;
            default -> 0;
        };
    }

    public boolean straysCarryGlacialArrows() {
        return straysCarryGlacialArrows;
    }

    public boolean enableHeavyMobSnowPacking() {
        return enableHeavyMobSnowPacking;
    }
}