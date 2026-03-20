package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.thermoo.api.entity.v1.ThermooEntityTypeTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.entity.LivingEntity;

public class SurvivalUtils {

    public static boolean isShivering(LivingEntity entity) {
        if (entity.is(ThermooEntityTypeTags.BENEFITS_FROM_COLD_ENTITY_TYPE)) {
            return false;
        }

        return entity.thermoo$getTemperatureScale() < FrostifulConfigYACL.freezingConfig().getShiverBelow();
    }

    @Environment(EnvType.CLIENT)
    public static boolean isShiveringRender(LivingEntity entity) {
        if (entity.is(ThermooEntityTypeTags.BENEFITS_FROM_COLD_ENTITY_TYPE)) {
            return false;
        }

        // start showing shivering slightly before actually applying it
        return entity.thermoo$getTemperatureScale() <= FrostifulConfigYACL.freezingConfig().getShiverBelow();
    }

    private SurvivalUtils() {
    }
}
