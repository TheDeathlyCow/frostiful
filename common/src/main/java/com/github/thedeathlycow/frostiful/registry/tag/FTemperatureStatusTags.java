package com.github.thedeathlycow.frostiful.registry.tag;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.thermoo.api.core.v2.registry.ThermooRegistries;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.TemperatureStatus;
import net.minecraft.tags.TagKey;

public final class FTemperatureStatusTags {
    public static final TagKey<TemperatureStatus> FROSTOLOGER_STATUSES = create("frostologer_statuses");

    public static final TagKey<TemperatureStatus> PLAYER_STATUSES = create("player_statuses");
    public static final TagKey<TemperatureStatus> NORMAL_PLAYER_STATUSES = create("normal_player_statuses");
    public static final TagKey<TemperatureStatus> FROSTOLOGY_CLOAK_PLAYER_STATUSES = create("frostology_cloak_player_statuses");


    private static TagKey<TemperatureStatus> create(String id) {
        return TagKey.create(ThermooRegistries.TEMPERATURE_STATUS, Frostiful.id(id));
    }

    private FTemperatureStatusTags() {

    }
}