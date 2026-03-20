package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.compat.TrinketsIntegration;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.section.EnvironmentConfig;
import com.github.thedeathlycow.frostiful.config.section.FreezingConfig;
import com.github.thedeathlycow.frostiful.registry.FGameRules;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import com.github.thedeathlycow.thermoo.api.core.v2.TemperatureRecord;
import com.github.thedeathlycow.thermoo.api.core.v2.TemperatureUnit;
import com.github.thedeathlycow.thermoo.api.core.v2.event.EnvironmentTickContext;
import com.github.thedeathlycow.thermoo.api.environment.v2.component.EnvironmentComponentTypes;
import com.github.thedeathlycow.thermoo.api.environment.v2.component.TemperatureRecordComponent;
import com.github.thedeathlycow.thermoo.api.environment.v2.event.ServerPlayerEnvironmentTickEvents;
import dev.yumi.commons.TriState;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.VisibleForTesting;

public final class ServerPlayerEnvironmentTickListeners {

    public static void initialize() {
        ServerPlayerEnvironmentTickEvents.GET_TEMPERATURE_CHANGE.register(ServerPlayerEnvironmentTickListeners::getTemperatureChange);
        ServerPlayerEnvironmentTickEvents.ALLOW_TEMPERATURE_CHANGE.register(ServerPlayerEnvironmentTickListeners::allowTemperatureChange);
    }

    private static int getTemperatureChange(EnvironmentTickContext<ServerPlayer> context) {
        if (context.affected().isSpectator()) {
            return 0;
        }

        TemperatureRecord temperature = context.components()
                .getOrDefault(EnvironmentComponentTypes.TEMPERATURE, TemperatureRecordComponent.DEFAULT);

        EnvironmentConfig config = FrostifulConfigYACL.environmentConfig();
        int total = envTemperatureToTemperaturePoint(temperature, config);

        if (total < 0 && context.affected().thermoo$isWet()) {
            total = (int) (total * config.getEnvironmentFreezingSoakedMultiplier());
        }

        if (context.affected().tickCount % 20 == 0 && Frostiful.LOGGER.isDebugEnabled()) {
            Frostiful.LOGGER.debug("Adding {} temperature to {}", total, context.affected().getScoreboardName());
        }

        return total;
    }

    private static TriState allowTemperatureChange(EnvironmentTickContext<ServerPlayer> context, int temperatureChange) {
        if (temperatureChange > 0) {
            return TriState.DEFAULT;
        }

        FreezingConfig config = FrostifulConfigYACL.freezingConfig();
        ServerPlayer player = context.affected();

        int tickInterval = config.getPassiveFreezingTickInterval();
        if (tickInterval > 1 && player.tickCount % tickInterval != 0) {
            return TriState.FALSE;
        }

        if (player.thermoo$getTemperatureScale() < -config.getMaxPassiveFreezingPercent()) {
            return TriState.FALSE;
        }

        boolean doPassiveFreezing = config.doPassiveFreezing()
                && context.level().getGameRules().get(FGameRules.ENABLE_ENVIRONMENT_FREEZING);

        if (TrinketsIntegration.hasAnyEquipped(player, stack -> stack.is(FItemTags.CHILLAGER_LORD_CLOAK))) {
            return TriState.TRUE;
        } else if (!doPassiveFreezing) {
            return TriState.FALSE;
        } else {
            return TriState.DEFAULT;
        }
    }

    @VisibleForTesting
    public static int envTemperatureToTemperaturePoint(TemperatureRecord temperature) {
        return envTemperatureToTemperaturePoint(temperature, new EnvironmentConfig());
    }

    public static int envTemperatureToTemperaturePoint(TemperatureRecord temperature, EnvironmentConfig config) {
        double temperatureC = temperature
                .valueInUnit(TemperatureUnit.CELSIUS);

        double thresholdC = config.getMaxTemperatureForColdC();
        double degreesPerTemperatureDecrease = config.getDegreesCPerTemperatureDecrease();

        if (temperatureC > thresholdC) {
            return 0;
        }
        // Graphical proof: https://www.desmos.com/calculator/01nd0aidxh
        double base = (temperatureC - thresholdC - degreesPerTemperatureDecrease) / degreesPerTemperatureDecrease;
        return Mth.ceil(config.getEnvironmentTemperatureMultiplier() * base);
    }

    private ServerPlayerEnvironmentTickListeners() {

    }
}