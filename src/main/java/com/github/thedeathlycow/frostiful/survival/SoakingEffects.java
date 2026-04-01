package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.section.EnvironmentSettings;
import com.github.thedeathlycow.frostiful.mixins.entity.EntityInvoker;
import com.github.thedeathlycow.thermoo.api.core.v2.event.EnvironmentTickContext;
import com.github.thedeathlycow.thermoo.api.core.v2.event.LivingEntitySoakingTickEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;

public final class SoakingEffects {
    public static void initialize() {
        // scorchful overrides these effects
        if (!FrostifulIntegrations.isModLoaded(FrostifulIntegrations.SCORCHFUL_ID)) {
            LivingEntitySoakingTickEvents.GET_SOAKING_CHANGE.register(SoakingEffects::getSoakingChange);
        } else {
            Frostiful.LOGGER.info("Scorchful has been detected. Frostiful will not affect soaking.");
        }
    }

    private static int getSoakingChange(EnvironmentTickContext<? extends LivingEntity> context) {
        if (context.affected().isSpectator()) {
            return 0;
        }

        EntityInvoker invoker = (EntityInvoker) context.affected();
        EnvironmentSettings settings = FrostifulConfigYACL.environmentSettings();
        int total = 0;

        // increase wetness
        total += getRainChange(invoker, settings);
        total += getTouchingWaterChange(context, settings);
        total += getSubmerged(context, invoker);

        // drying effects
        total -= getLightDrying(context);
        total -= getOnFireDrying(context, settings);

        return total;
    }

    private static int getRainChange(EntityInvoker invoker, EnvironmentSettings settings) {
        return invoker.frostiful$invokeIsBeingRainedOn()
                ? settings.rainWetnessIncrease()
                : 0;
    }

    private static int getTouchingWaterChange(EnvironmentTickContext<? extends LivingEntity> context, EnvironmentSettings settings) {
        LivingEntity entity = context.affected();
        return entity.isInWater() || entity.getInBlockState().is(Blocks.WATER_CAULDRON)
                ? settings.touchingWaterWetnessIncrease()
                : 0;
    }

    private static int getSubmerged(EnvironmentTickContext<? extends LivingEntity> context, EntityInvoker invoker) {
        LivingEntity entity = context.affected();
        return entity.isUnderWater()
                ? entity.thermoo$getMaxWetTicks()
                : 0;
    }

    private static int getLightDrying(EnvironmentTickContext<? extends LivingEntity> context) {
        int blockLightLevel = context.level().getBrightness(LightLayer.BLOCK, context.pos());
        return blockLightLevel / 4;
    }

    private static int getOnFireDrying(EnvironmentTickContext<? extends LivingEntity> context, EnvironmentSettings settings) {
        return context.affected().isOnFire()
                ? settings.onFireDryDate()
                : 0;
    }

    private SoakingEffects() {

    }
}