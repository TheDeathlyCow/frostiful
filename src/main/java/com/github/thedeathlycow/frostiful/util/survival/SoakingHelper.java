package com.github.thedeathlycow.frostiful.util.survival;

import com.github.thedeathlycow.frostiful.config.group.FreezingConfigGroup;
import com.github.thedeathlycow.frostiful.entity.SoakableEntity;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.mixins.entity.EntityInvoker;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.LightType;

public class SoakingHelper {

    public static int getWetnessChange(PlayerEntity player) {
        FreezingConfigGroup freezingConfig = Frostiful.getConfig().freezingConfig;
        SoakableEntity soakableEntity = (SoakableEntity) player;

        boolean isDry = true;
        int wetness = 0;

        //* set wetness from rain
        if (((EntityInvoker) player).frostiful$invokeIsBeingRainedOn()) {
            wetness = freezingConfig.getRainWetnessIncrease();
            isDry = false;
        }

        //* immediately soak players submerged in water
        if (player.isSubmergedInWater() || player.isInsideWaterOrBubbleColumn()) {
            wetness = soakableEntity.frostiful$getMaxWetTicks() - soakableEntity.frostiful$getWetTicks();
            isDry = false;
        }

        //* dry off players not water
        if (isDry) {
            wetness = -freezingConfig.getDryRate();
        }

        //* increase dryness with block light
        int blockLightLevel = player.getWorld().getLightLevel(LightType.BLOCK, player.getBlockPos());
        if (blockLightLevel > 0) {
            wetness -= blockLightLevel >> 2; // fast divide by 4
        }

        return wetness;
    }

    public static float getWetnessFreezeModifier(PlayerEntity player) {
        if (hasWaterFreezingImmuneEffect(player)) {
            return 0.0f;
        }
        SoakableEntity soakable = (SoakableEntity) player;
        return soakable.frostiful$getWetnessScale();
    }

    private static boolean hasWaterFreezingImmuneEffect(PlayerEntity player) {
        return player.hasStatusEffect(StatusEffects.WATER_BREATHING)
                || player.hasStatusEffect(StatusEffects.CONDUIT_POWER);
    }
}
