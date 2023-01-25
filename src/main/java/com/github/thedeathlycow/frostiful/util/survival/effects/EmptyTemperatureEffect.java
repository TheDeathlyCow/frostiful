package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;

public class EmptyTemperatureEffect extends TemperatureEffect<EmptyTemperatureEffect.Config> {

    @Override
    public void apply(LivingEntity victim, ServerWorld serverWorld, Config config) {
        // does nothing
    }

    @Override
    public boolean shouldApply(LivingEntity victim, Config config) {
        return false;
    }

    @Override
    public Config configFromJson(JsonElement json, JsonDeserializationContext context) throws JsonParseException {
        return Config.INSTANCE;
    }

    public static final class Config {

        public static final Config INSTANCE = new Config();

        private Config() {

        }
    }

}
