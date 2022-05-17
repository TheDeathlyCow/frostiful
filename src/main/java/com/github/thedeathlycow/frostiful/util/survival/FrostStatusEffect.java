package com.github.thedeathlycow.frostiful.util.survival;

import com.google.common.collect.ImmutableList;
import com.google.gson.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public record FrostStatusEffect(float progressThreshold, StatusEffect effect, int duration, int amplifier) {

    private static final List<FrostStatusEffect> EFFECTS = ImmutableList.of(
            new FrostStatusEffect(0.5f, StatusEffects.WEAKNESS, 100, 0),
            new FrostStatusEffect(0.75f, StatusEffects.MINING_FATIGUE, 100, 0),
            new FrostStatusEffect(0.99f, StatusEffects.WEAKNESS, 100, 1),
            new FrostStatusEffect(0.99f, StatusEffects.MINING_FATIGUE, 100, 1)
    );

    public static List<FrostStatusEffect> getPassiveFreezingEffects() {
        return EFFECTS;
    }

    public StatusEffectInstance createEffectInstance() {
        return createEffectInstance(true, true);
    }

    public StatusEffectInstance createEffectInstance(boolean ambient, boolean visible) {
        return new StatusEffectInstance(this.effect(), this.duration(), this.amplifier(), ambient, visible);
    }

    public static class Deserializer implements JsonDeserializer<FrostStatusEffect>, JsonSerializer<FrostStatusEffect> {

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

        @Override
        public JsonElement serialize(FrostStatusEffect src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject json = new JsonObject();
            json.add("progress_threshold", new JsonPrimitive(src.progressThreshold()));
            json.add("amplifier", new JsonPrimitive(src.amplifier()));
            json.add("duration", new JsonPrimitive(src.duration));
            json.add("effect", new JsonPrimitive(Objects.requireNonNull(Registry.STATUS_EFFECT.getId(src.effect())).toString()));
            return json;
        }
    }
}
