package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.google.gson.JsonElement;
import net.minecraft.entity.LivingEntity;

public abstract class TemperatureEffect<C> {

    public TemperatureEffect() {

    }

    public abstract void apply(LivingEntity victim, C config);

    public abstract boolean shouldApply(LivingEntity victim, C config);

    public abstract C configFromJson(JsonElement json);

}
