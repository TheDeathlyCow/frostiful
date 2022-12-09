package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.google.gson.JsonElement;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public abstract class SurvivalEffectType<C> {

    public SurvivalEffectType() {

    }

    public abstract void apply(LivingEntity victim, C config);

    public abstract boolean shouldApply(LivingEntity victim, C config);

    public abstract C configFromJson(JsonElement json);

}
