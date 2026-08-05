package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.compat.TrinketsIntegration;
import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.section.EnvironmentSettings;
import com.github.thedeathlycow.frostiful.registry.FGameRules;
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
        ServerPlayer player = context.affected();

        if (player.isSpectator()) {
            return 0;
        }

        TemperatureRecord temperature = context.components()
                .getOrDefault(EnvironmentComponentTypes.TEMPERATURE, TemperatureRecordComponent.DEFAULT);

        EnvironmentSettings settings = FrostifulConfigYACL.environmentSettings();
        int total = envTemperatureToTemperaturePoint(temperature, settings);

        if (total < 0 && player.thermoo$isWet()) {
            total = (int) (total * settings.environmentFreezingSoakedMultiplier());
        }

        if (player.tickCount % 20 == 0 && Frostiful.LOGGER.isDebugEnabled()) {
            Frostiful.LOGGER.debug("Adding {} temperature to {}", total, player.getScoreboardName());
        }

        if (total == 0 && player.thermoo$isCold() && temperature.valueInUnit(TemperatureUnit.CELSIUS) >= 15) {
            total = 1;
        }

        return total;
    }

    private static TriState allowTemperatureChange(EnvironmentTickContext<ServerPlayer> context, int temperatureChange) {
        if (temperatureChange > 0) {
            return TriState.DEFAULT;
        }

        EnvironmentSettings settings = FrostifulConfigYACL.environmentSettings();
        ServerPlayer player = context.affected();

        int tickInterval = settings.environmentFreezingTickInterval();
        if (tickInterval > 1 && player.tickCount % tickInterval != 0) {
            return TriState.FALSE;
        }

        if (player.thermoo$getTemperatureScale() < settings.minEnvironmentalFreezingTemperatureScale()) {
            return TriState.FALSE;
        }

        boolean doPassiveFreezing = settings.enableEnvironmentFreezing()
                && context.level().getGameRules().get(FGameRules.ENABLE_ENVIRONMENT_FREEZING);

        if (TrinketsIntegration.wearingFrostologyCloak(player)) {
            return TriState.TRUE;
        } else if (!doPassiveFreezing) {
            return TriState.FALSE;
        } else {
            return TriState.DEFAULT;
        }
    }

    @VisibleForTesting
    public static int envTemperatureToTemperaturePoint(TemperatureRecord temperature) {
        return envTemperatureToTemperaturePoint(temperature, new EnvironmentSettings());
    }

    public static int envTemperatureToTemperaturePoint(
            TemperatureRecord temperature,
            EnvironmentSettings settings
    ) {
        double temperatureC = temperature
                .valueInUnit(TemperatureUnit.CELSIUS);

        double thresholdC = settings.maxTemperatureForColdC();
        double degreesPerTemperatureDecrease = settings.degreesCPerTemperatureDecrease();

        if (temperatureC > thresholdC) {
            return 0;
        }
        // Graphical proof: https://www.desmos.com/calculator/01nd0aidxh
        double base = (temperatureC - thresholdC - degreesPerTemperatureDecrease) / degreesPerTemperatureDecrease;
        return Mth.ceil(settings.environmentTemperatureMultiplier() * base);
    }

    private ServerPlayerEnvironmentTickListeners() {

    }
}