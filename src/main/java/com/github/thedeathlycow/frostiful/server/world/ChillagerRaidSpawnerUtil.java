package com.github.thedeathlycow.frostiful.server.world;

import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public final class ChillagerRaidSpawnerUtil {
    public static EntityType<? extends Entity> replaceRaidersInColdBiomes(EntityType<?> base, boolean isBiomeCold) {
        if (!isBiomeCold) {
            return base;
        } else if (base == EntityType.PILLAGER) {
            return FEntityTypes.CHILLAGER;
        } else if (base == EntityType.EVOKER) {
            return FEntityTypes.FROSTOLOGER;
        } else {
            return base;
        }
    }

    private ChillagerRaidSpawnerUtil() {
    }
}