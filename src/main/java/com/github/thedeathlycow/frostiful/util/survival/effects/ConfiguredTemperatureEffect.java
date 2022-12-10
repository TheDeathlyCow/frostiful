package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.google.gson.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;

public class ConfiguredTemperatureEffect<C> {

    private final TemperatureEffect<C> type;

    private final C config;

    private final EntityPredicate predicate;

    public ConfiguredTemperatureEffect(TemperatureEffect<C> type, C config, EntityPredicate predicate) {
        this.type = type;
        this.config = config;
        this.predicate = predicate;
    }

    public static <C> ConfiguredTemperatureEffect<C> fromJson(
            TemperatureEffect<C> type,
            JsonElement configJson,
            EntityPredicate predicate
    ) {
        return new ConfiguredTemperatureEffect<>(type, type.configFromJson(configJson), predicate);
    }

    public void applyIfPossible(LivingEntity victim) {

        boolean shouldApply = this.type.shouldApply(victim, this.config)
                && this.predicate.test((ServerWorld) victim.getWorld(), victim.getPos(), victim);

        if (shouldApply) {
            this.type.apply(victim, this.config);
        }
    }

    public static class Serializer implements JsonDeserializer<ConfiguredTemperatureEffect<?>> {

        public static final Gson GSON = new GsonBuilder()
                .registerTypeAdapter(ConfiguredTemperatureEffect.class, new Serializer())
                .create();

        @Override
        public ConfiguredTemperatureEffect<?> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject json = jsonElement.getAsJsonObject();

            // set required values
            Identifier typeID = new Identifier(json.get("type").getAsString());

            if (!TemperatureEffects.VALUES.containsKey(typeID)) {
                throw new JsonParseException("Unknown survival effect type: " + typeID);
            }

            TemperatureEffect<?> effectType = TemperatureEffects.VALUES.get(typeID);

            // set optional values
            EntityPredicate predicate = EntityPredicate.ANY;
            if (json.has("entity")) {
                predicate = EntityPredicate.fromJson(json.get("entity"));
            }

            return ConfiguredTemperatureEffect.fromJson(effectType, json.get("config"), predicate);
        }
    }

}
