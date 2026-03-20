package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.thermoo.api.core.v2.registry.ThermooRegistryKeys;
import com.github.thedeathlycow.thermoo.api.temperature.status.v2.TemperatureStatus;
import net.minecraft.resources.ResourceKey;

public final class FTemperatureStatuses {
    public static final ResourceKey<TemperatureStatus> FREEZE_DAMAGE = key("freeze_damage");

    public static final ResourceKey<TemperatureStatus> PLAYER_MOVEMENT_SPEED = key("player/movement_speed");
    public static final ResourceKey<TemperatureStatus> PLAYER_CHILLY = key("player/chilly");
    public static final ResourceKey<TemperatureStatus> PLAYER_COLD = key("player/cold");
    public static final ResourceKey<TemperatureStatus> PLAYER_FREEZING = key("player/freezing");

    public static final ResourceKey<TemperatureStatus> FROSTOLOGY_CLOAK_MOVEMENT_SPEED = key("player/frostology_cloak/movement_speed");
    public static final ResourceKey<TemperatureStatus> FROSTOLOGY_CLOAK_MELTING = key("player/frostology_cloak/melting");
    public static final ResourceKey<TemperatureStatus> FROSTOLOGY_CLOAK_WARM = key("player/frostology_cloak/warm");
    public static final ResourceKey<TemperatureStatus> FROSTOLOGY_CLOAK_COLD = key("player/frostology_cloak/cold");
    public static final ResourceKey<TemperatureStatus> FROSTOLOGY_CLOAK_FREEZING = key("player/frostology_cloak/freezing");

    public static final ResourceKey<TemperatureStatus> FROSTOLOGER_ATTACK_DAMAGE = key("frostologer/attack_damage");
    public static final ResourceKey<TemperatureStatus> FROSTOLOGER_CHILLY = key("frostologer/chilly");
    public static final ResourceKey<TemperatureStatus> FROSTOLOGER_FREEZING = key("frostologer/freezing");

    private static ResourceKey<TemperatureStatus> key(String name) {
        return ResourceKey.create(ThermooRegistryKeys.TEMPERATURE_STATUS, Frostiful.id(name));
    }

    private FTemperatureStatuses() {

    }
}