package com.github.thedeathlycow.frostiful.survival;

import com.github.thedeathlycow.frostiful.config.group.FreezingConfigGroup;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.world.FGameRules;
import com.github.thedeathlycow.thermoo.api.temperature.EnvironmentController;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import com.github.thedeathlycow.thermoo.api.temperature.Soakable;
import com.github.thedeathlycow.thermoo.api.temperature.event.EnvironmentChangeResult;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public final class PlayerEventThermooListeners {

    public void applyPassiveFreezing(
            EnvironmentController controller,
            PlayerEntity player,
            Biome biome,
            EnvironmentChangeResult result
    ) {

        if (result.getInitialTemperatureChange() > 0 || result.isInitialChangeApplied()) {
            return;
        }

        World world = player.world;
        FreezingConfigGroup config = Frostiful.getConfig().freezingConfig;
        final boolean doPassiveFreezing = config.doPassiveFreezing()
                && world.getGameRules().getBoolean(FGameRules.DO_PASSIVE_FREEZING)
                && player.thermoo$canFreeze()
                && player.thermoo$getTemperatureScale() > -config.getMaxPassiveFreezingPercent();

        if (doPassiveFreezing) {
            float modifier = getWetnessFreezeModifier(player);
            int temperatureChange = MathHelper.ceil(result.getInitialTemperatureChange() * (1 + modifier));

            player.thermoo$addTemperature(temperatureChange, HeatingModes.PASSIVE);
            result.setAppliedInitialChange();
        }
    }

    private static float getWetnessFreezeModifier(Soakable soakable) {
        if (soakable.thermoo$ignoresFrigidWater()) {
            return 0.0f;
        }
        FreezingConfigGroup config = Frostiful.getConfig().freezingConfig;
        return config.getPassiveFreezingWetnessScaleMultiplier() * soakable.thermoo$getSoakedScale();
    }

}
