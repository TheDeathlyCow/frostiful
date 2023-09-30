package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.thermoo.api.ThermooTags;
import com.github.thedeathlycow.thermoo.api.temperature.TemperatureAware;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;

public class SurvivalUtils {

    public static boolean isShivering(LivingEntity entity) {
        if (entity.getType().isIn(ThermooTags.BENEFITS_FROM_COLD_ENTITY_TYPE)) {
            return false;
        }

        FrostifulConfig config = Frostiful.getConfig();
        return entity.thermoo$getTemperatureScale() < config.freezingConfig.getShiverBelow();
    }

    @Environment(EnvType.CLIENT)
    public static boolean isShiveringRender(LivingEntity entity) {
        if (entity.getType().isIn(ThermooTags.BENEFITS_FROM_COLD_ENTITY_TYPE)) {
            return false;
        }

        FrostifulConfig config = Frostiful.getConfig();
        // start showing shivering slightly before actually applying it
        return entity.thermoo$getTemperatureScale() <= config.freezingConfig.getShiverBelow();
    }

    private SurvivalUtils() {
    }
}
