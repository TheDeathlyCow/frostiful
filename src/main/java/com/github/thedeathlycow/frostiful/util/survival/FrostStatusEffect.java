package com.github.thedeathlycow.frostiful.util.survival;

import com.github.thedeathlycow.frostiful.config.GlobalConfig;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.simple.config.Config;
import com.google.gson.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public record FrostStatusEffect(double progressThreshold, StatusEffect effect, int duration, int amplifier) {

    public static List<FrostStatusEffect> getPassiveFreezingEffects() {
        Config config = Frostiful.getConfig();
        return Collections.unmodifiableList(config.get(GlobalConfig.PASSIVE_FREEZING_EFFECTS));
    }

    public static class Deserializer implements JsonDeserializer<FrostStatusEffect> {

        @Override
        public FrostStatusEffect deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            JsonObject object = json.getAsJsonObject();

            // get numbers
            double progressThreshold = object.get("progress_threshold").getAsDouble();
            int duration = object.get("duration").getAsInt();
            int amplifier = object.get("amplifier").getAsInt();

            // get effect
            Identifier effectKey = new Identifier(object.get("effect").getAsString());
            StatusEffect effect = Registry.STATUS_EFFECT.get(effectKey);

            return new FrostStatusEffect(progressThreshold, effect, duration, amplifier);
        }
    }
}
