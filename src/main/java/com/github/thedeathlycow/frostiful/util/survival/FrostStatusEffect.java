package com.github.thedeathlycow.frostiful.util.survival;

import com.github.thedeathlycow.frostiful.config.FreezingConfig;
import com.github.thedeathlycow.simple.config.Config;
import com.google.gson.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public record FrostStatusEffect(float progressThreshold, StatusEffect effect, int duration, int amplifier) {

    public static List<FrostStatusEffect> getPassiveFreezingEffects() {
        Config config = FreezingConfig.CONFIG;
        return Collections.unmodifiableList(config.get(FreezingConfig.PASSIVE_FREEZING_EFFECTS));
    }

    public StatusEffectInstance createEffectInstance() {
        return createEffectInstance(true, true);
    }

    public StatusEffectInstance createEffectInstance(boolean ambient, boolean visible) {
        return new StatusEffectInstance(this.effect(), this.duration(), this.amplifier(), ambient, visible);
    }

    public static class Deserializer implements JsonDeserializer<FrostStatusEffect> {

        @Override
        public FrostStatusEffect deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            JsonObject object = json.getAsJsonObject();

            // get numbers
            float progressThreshold = object.get("progress_threshold").getAsFloat();
            int amplifier = object.get("amplifier").getAsInt();

            // duration defaults to 100
            int duration = 100;
            if (object.has("duration")) {
                duration = object.get("duration").getAsInt();
            }

            // get effect
            Identifier effectKey = new Identifier(object.get("effect").getAsString());
            StatusEffect effect = Registry.STATUS_EFFECT.get(effectKey);

            return new FrostStatusEffect(progressThreshold, effect, duration, amplifier);
        }
    }
}
