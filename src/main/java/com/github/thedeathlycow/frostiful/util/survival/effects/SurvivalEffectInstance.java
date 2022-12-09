package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.google.gson.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;

public class SurvivalEffectInstance<C> {

    private final SurvivalEffectType<C> type;

    private final C config;

    private final EntityPredicate predicate;

    public SurvivalEffectInstance(SurvivalEffectType<C> type, JsonElement config, EntityPredicate predicate) {
        this.type = type;
        this.config = type.configFromJson(config);
        this.predicate = predicate;
    }

    public void applyIfPossible(LivingEntity victim) {

        boolean shouldApply = this.type.shouldApply(victim, this.config)
                && this.predicate.test((ServerWorld) victim.getWorld(), victim.getPos(), victim);

        if (shouldApply) {
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

            // set required values
            Identifier typeID = new Identifier(json.get("type").getAsString());

            if (!SurvivalEffectTypes.VALUES.containsKey(typeID)) {
                throw new JsonParseException("Unknown survival effect type: " + typeID);
            }

            SurvivalEffectType<?> effectType = SurvivalEffectTypes.VALUES.get(typeID);

            // set optional values
            EntityPredicate predicate = EntityPredicate.ANY;
            if (json.has("entity")) {
                predicate = EntityPredicate.fromJson(json.get("entity"));
            }

            return new SurvivalEffectInstance<>(effectType, json.get("config"), predicate);
        }
    }

}
