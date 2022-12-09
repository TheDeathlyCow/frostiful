package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.google.gson.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;

public class SurvivalEffectInstance<C> {

    private final SurvivalEffectType<C> type;

    private final C config;

    public SurvivalEffectInstance(SurvivalEffectType<C> type, JsonElement json) {
        this.type = type;
        this.config = type.configFromJson(json);
    }

    public void applyIfPossible(LivingEntity victim) {
        if (this.type.shouldApply(victim, this.config)) {
            this.type.apply(victim, this.config);
        }
    }

    public static class Serializer implements JsonDeserializer<SurvivalEffectInstance<?>> {

        public static final Gson GSON = new GsonBuilder()
                .registerTypeAdapter(SurvivalEffectInstance.class, new Serializer())
                .create();

        @Override
        public SurvivalEffectInstance<?> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject json = jsonElement.getAsJsonObject();

            Identifier typeID = new Identifier(json.get("type").getAsString());
            SurvivalEffectType<?> effectType = SurvivalEffectTypes.VALUES.get(typeID);

            return new SurvivalEffectInstance<>(effectType, json.get("config"));
        }
    }

}
