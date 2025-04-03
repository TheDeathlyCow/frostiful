package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.config.group.EnvironmentConfigGroup;
import com.github.thedeathlycow.frostiful.item.component.IceLikeComponent;
import com.github.thedeathlycow.frostiful.registry.FGameRules;
import com.github.thedeathlycow.thermoo.api.environment.component.EnvironmentComponentTypes;
import com.github.thedeathlycow.thermoo.api.environment.component.TemperatureRecordComponent;
import com.github.thedeathlycow.thermoo.api.environment.event.ServerPlayerEnvironmentTickEvents;
import com.github.thedeathlycow.thermoo.api.temperature.event.EnvironmentTickContext;
import com.github.thedeathlycow.thermoo.api.util.TemperatureRecord;
import com.github.thedeathlycow.thermoo.api.util.TemperatureUnit;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.MathHelper;

public final class ServerPlayerEnvironmentTickListeners {

    public static void initialize() {
        ServerPlayerEnvironmentTickEvents.GET_TEMPERATURE_CHANGE.register(ServerPlayerEnvironmentTickListeners::getTemperatureChange);
        ServerPlayerEnvironmentTickEvents.ALLOW_TEMPERATURE_CHANGE.register(ServerPlayerEnvironmentTickListeners::allowTemperatureChange);
    }

    private static int getTemperatureChange(EnvironmentTickContext<ServerPlayerEntity> context) {
        if (context.affected().isSpectator()) {
            return 0;
        }

        TemperatureRecord temperature = context.components()
                .getOrDefault(EnvironmentComponentTypes.TEMPERATURE, TemperatureRecordComponent.DEFAULT);

        EnvironmentConfigGroup config = Frostiful.getConfig().environmentConfig;
        int total = envTemperatureToTemperaturePoint(temperature, config);

        if (total < 0 && context.affected().thermoo$isWet()) {
            total = (int) (total * Frostiful.getConfig().environmentConfig.getEnvironmentFreezingSoakedMultiplier());
        }

        if (context.affected().age % 20 == 0 && Frostiful.LOGGER.isDebugEnabled()) {
            Frostiful.LOGGER.debug("Adding {} temperature to {}", total, context.affected().getNameForScoreboard());
        }

        return total;
    }

    private static TriState allowTemperatureChange(EnvironmentTickContext<ServerPlayerEntity> context, int temperatureChange) {
        if (temperatureChange > 0) {
            return TriState.DEFAULT;
        }

        FrostifulConfig config = Frostiful.getConfig();
        ServerPlayerEntity player = context.affected();

        int tickInterval = config.freezingConfig.getPassiveFreezingTickInterval();
        if (tickInterval > 1 && player.age % tickInterval != 0) {
            return TriState.FALSE;
        }

        if (player.thermoo$getTemperatureScale() < -config.freezingConfig.getMaxPassiveFreezingPercent()) {
            return TriState.FALSE;
        }

        boolean doPassiveFreezing = config.freezingConfig.doPassiveFreezing()
                && context.world().getGameRules().getBoolean(FGameRules.DO_PASSIVE_FREEZING);

        if (IceLikeComponent.isWearing(player)) {
            return TriState.TRUE;
        } else if (!doPassiveFreezing) {
            return TriState.FALSE;
        } else {
            return TriState.DEFAULT;
        }
    }

    public static int envTemperatureToTemperaturePoint(TemperatureRecord temperature) {
        return envTemperatureToTemperaturePoint(temperature, new EnvironmentConfigGroup());
    }

    public static int envTemperatureToTemperaturePoint(TemperatureRecord temperature, EnvironmentConfigGroup configGroup) {
        double temperatureC = temperature
                .valueInUnit(TemperatureUnit.CELSIUS);

        double thresholdC = configGroup.getMaxTemperatureForColdC();
        double degreesPerTemperatureDecrease = configGroup.getDegreesCPerTemperatureDecrease();

        if (temperatureC > thresholdC) {
            return 0;
        }
        // Graphical proof: https://www.desmos.com/calculator/01nd0aidxh
        double base = (temperatureC - thresholdC - degreesPerTemperatureDecrease) / degreesPerTemperatureDecrease;
        return MathHelper.ceil(configGroup.getEnvironmentTemperatureMultiplier() * base);
    }

    private ServerPlayerEnvironmentTickListeners() {

    }
}