package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;

@Deprecated
public abstract class TemperatureEffect<C> {

    public TemperatureEffect() {

    }

    public abstract void apply(LivingEntity victim, ServerWorld serverWorld, C config);

    public abstract boolean shouldApply(LivingEntity victim, C config);

    public abstract C configFromJson(JsonElement json, JsonDeserializationContext context) throws JsonParseException;

}
