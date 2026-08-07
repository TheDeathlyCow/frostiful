/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.github.thedeathlycow.frostiful.config.section;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.Translate;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.DoubleField;
import dev.isxander.yacl3.config.v2.api.autogen.FloatField;
import dev.isxander.yacl3.config.v2.api.autogen.TickBox;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.minecraft.util.Mth;

import java.nio.file.Path;

public class BlockSettings {
    public static final Path PATH = Frostiful.getConfigDir().resolve("common").resolve("block.json5");

    public static final ConfigClassHandler<BlockSettings> HANDLER = ConfigClassHandler.createBuilder(BlockSettings.class)
            .id(Frostiful.id("common/block"))
            .serializer(
                    config -> GsonConfigSerializerBuilder.create(config)
                            .setPath(PATH)
                            .setJson5(true)
                            .build()
            )
            .build();

    public static final String CATEGORY = FrostifulConfigYACL.MAIN_CATEGORY_NAME;
    public static final String ICICLE_GROUP = "icicle";
    public static final String SUN_LICHEN_GROUP = "sun_lichen";
    public static final String CAMPFIRE_GROUP = "campfire";

    @AutoGen(category = CATEGORY, group = ICICLE_GROUP)
    @Translate.Name("Enable icicle instability")
    @SerialEntry(comment = "When enabled, icicles hanging from a ceiling will have a chance to become unstable and fall.")
    @TickBox
    boolean enableIcicleInstability = true;

    @AutoGen(category = CATEGORY, group = ICICLE_GROUP)
    @Translate.Name("Icicle instability chance multiplier")
    @SerialEntry(comment = "If icicle instability is enabled, multiplies the chance that an icicle hanging from a ceiling will become unstable and fall. Must be at least 0.")
    @FloatField(min = 0, format = "%.2f")
    float icicleInstabilityChanceMultiplier = 1.0f;

    @AutoGen(category = CATEGORY, group = ICICLE_GROUP)
    @Translate.Name("Icicle growth chance multiplier")
    @SerialEntry(comment = "Multiplies the chance that an icicle hanging from a ceiling will attempt to grow. Must be at least 0.")
    @FloatField(min = 0, format = "%.2f")
    float icicleGrowthChanceMultiplier = 1.0f;

    public boolean enableIcicleInstability() {
        return enableIcicleInstability;
    }

    public float icicleInstabilityChanceMultiplier() {
        return icicleInstabilityChanceMultiplier;
    }

    public float icicleGrowthChanceMultiplier() {
        return icicleGrowthChanceMultiplier;
    }

    @AutoGen(category = CATEGORY, group = SUN_LICHEN_GROUP)
    @Translate.Name("Sun Lichen burn time multiplier")
    @SerialEntry(comment = "Multiplies the duration that a Hot Sun Lichen will burn a warm entity that touches it. Must be at least 0.")
    @FloatField(min = 0, format = "%.2f")
    float sunLichenBurnTimeMultiplier = 1.0f;

    public int sunLichenBurnTime() {
        return Mth.floor(3 * 20 * this.sunLichenBurnTimeMultiplier);
    }

    @AutoGen(category = CATEGORY, group = CAMPFIRE_GROUP)
    @Translate.Name("Campfire warmth range multiplier")
    @SerialEntry(comment = "Multiplies the range of the Warmth effect applied to entities around a Campfire, after adding a log. The base range is 10 blocks. Must be at least 0.")
    @DoubleField(min = 0)
    double campfireWarmthSearchRadiusMultiplier = 1.0;

    @AutoGen(category = CATEGORY, group = CAMPFIRE_GROUP)
    @Translate.Name("Campfire warmth time multiplier")
    @SerialEntry(comment = "Multiplies the duration of the Warmth effect that will be applied to entities near a campfire when a log is added to it. Must be at least 0.")
    @FloatField(min = 0)
    float campfireWarmthTimeMultiplier = 1.0f;

    public double campfireWarmthSearchRadius() {
        return 10.0 * this.campfireWarmthSearchRadiusMultiplier;
    }

    public int campfireWarmthTime() {
        return Mth.floor(1200 * campfireWarmthTimeMultiplier);
    }
}