package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.config.group.FreezingConfigGroup;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentController;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentControllerDecorator;
import com.github.thedeathlycow.thermoo.api.temperature.Soakable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class EntityTemperatureController extends EnvironmentControllerDecorator {

    private static final double BASE_MIN_TEMPERATURE = 40;

    public EntityTemperatureController(EnvironmentController controller) {
        super(controller);
    }

    @Override
    public double getBaseValueForAttribute(EntityAttribute attribute, LivingEntity entity) {
        double base = controller.getBaseValueForAttribute(attribute, entity);

        if (base != 0) {
            return base;
        }

        if (attribute == ThermooAttributes.MIN_TEMPERATURE) {
            base = BASE_MIN_TEMPERATURE;
        }

        return base;
    }

    @Override
    public int getTemperatureEffectsChange(LivingEntity entity) {
        if (entity.thermoo$isWarm()) {
            return controller.getTemperatureEffectsChange(entity);
        }

        int warmth = 0;
        FrostifulConfig config = Frostiful.getConfig();

        if (entity.isOnFire()) {
            int onFireRate = config.environmentConfig.getOnFireWarmRate();

            if (entity.getType() == FEntityTypes.FROSTOLOGER) {
                onFireRate /= 2;
            }

            warmth += onFireRate;
        }

        if (entity.wasInPowderSnow) {
            warmth -= config.environmentConfig.getPowderSnowFreezeRate();
        }

        boolean applyConduitPowerWarmth = entity.isSubmergedInWater()
                && entity.hasStatusEffect(StatusEffects.CONDUIT_POWER);

        if (applyConduitPowerWarmth) {
            warmth += config.freezingConfig.getConduitWarmthPerTick();
        }

        return warmth;
    }

    @Override
    public int getEnvironmentTemperatureForPlayer(PlayerEntity player, int localTemperature) {
        if (player.thermoo$isWarm() && localTemperature > 0) {
            return controller.getEnvironmentTemperatureForPlayer(player, localTemperature);
        }

        float modifier = 0f;
        if (localTemperature < 0) {
            modifier = getWetnessFreezeModifier(player);
        }
        return MathHelper.ceil(localTemperature * (1 + modifier));
    }

    private static float getWetnessFreezeModifier(Soakable soakable) {
        if (soakable.thermoo$ignoresFrigidWater()) {
            return 0.0f;
        }
        FreezingConfigGroup config = Frostiful.getConfig().freezingConfig;
        return config.getPassiveFreezingWetnessScaleMultiplier() * soakable.thermoo$getSoakedScale();
    }
}
