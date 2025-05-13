package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.mixins.entity.EntityInvoker;
import com.github.thedeathlycow.thermoo.api.temperature.event.EnvironmentTickContext;
import com.github.thedeathlycow.thermoo.api.temperature.event.LivingEntitySoakingTickEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.LightType;

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
        FrostifulConfig config = Frostiful.getConfig();
        int total = 0;

        // increase wetness
        total += getRainChange(invoker, config);
        total += getTouchingWaterChange(context, config);
        total += getSubmerged(context, invoker);

        // drying effects
        total -= getLightDrying(context);
        total -= getOnFireDrying(context, config);

        return total;
    }

    private static int getRainChange(EntityInvoker invoker, FrostifulConfig config) {
        return invoker.frostiful$invokeIsBeingRainedOn()
                ? config.environmentConfig.getRainWetnessIncrease()
                : 0;
    }

    private static int getTouchingWaterChange(EnvironmentTickContext<? extends LivingEntity> context, FrostifulConfig config) {
        LivingEntity entity = context.affected();
        return entity.isTouchingWater() || entity.getBlockStateAtPos().isOf(Blocks.WATER_CAULDRON)
                ? config.environmentConfig.getTouchingWaterWetnessIncrease()
                : 0;
    }

    private static int getSubmerged(EnvironmentTickContext<? extends LivingEntity> context, EntityInvoker invoker) {
        LivingEntity entity = context.affected();
        return entity.isSubmergedInWater()
                ? entity.thermoo$getMaxWetTicks()
                : 0;
    }

    private static int getLightDrying(EnvironmentTickContext<? extends LivingEntity> context) {
        int blockLightLevel = context.world().getLightLevel(LightType.BLOCK, context.pos());
        return blockLightLevel / 4;
    }

    private static int getOnFireDrying(EnvironmentTickContext<? extends LivingEntity> context, FrostifulConfig config) {
        return context.affected().isOnFire()
                ? config.environmentConfig.getOnFireDryDate()
                : 0;
    }

    private SoakingEffects() {

    }
}