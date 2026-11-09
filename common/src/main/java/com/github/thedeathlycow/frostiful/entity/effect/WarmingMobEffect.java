package com.github.thedeathlycow.frostiful.entity.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public final class WarmingMobEffect extends SimpleMobEffect {
    private static final double PERCENT_PER_TICK = 0.05 / 20.0;

    public WarmingMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    public boolean applyEffectTick(final ServerLevel level, final LivingEntity mob, final int amplification) {
        if (mob.thermoo$getTemperatureScale() < 0.01f) {
            int minTemp = -mob.thermoo$getMinTemperature();
            int tempChange = Mth.ceil(minTemp * PERCENT_PER_TICK * amplification);

            mob.thermoo$addTemperature(tempChange, level.thermoo$temperatureSources().active());
        }

        return true;
    }

    public boolean shouldApplyEffectTickThisTick(final int tickCount, final int amplification) {
        return true;
    }
}