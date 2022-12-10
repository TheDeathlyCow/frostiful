package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.google.gson.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.loot.LootGsons;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionManager;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;

public class ConfiguredTemperatureEffect<C> {

    private final TemperatureEffect<C> type;

    private final C config;

    @Nullable
    private final LootCondition predicate;

    public ConfiguredTemperatureEffect(TemperatureEffect<C> type, C config, @Nullable LootCondition predicate) {
        this.type = type;
        this.config = config;
        this.predicate = predicate;
    }

    public static <C> ConfiguredTemperatureEffect<C> fromJson(
            TemperatureEffect<C> type,
            JsonElement configJson,
            @Nullable LootCondition predicate
    ) throws JsonParseException {
        return new ConfiguredTemperatureEffect<>(type, type.configFromJson(configJson), predicate);
    }

    public void applyIfPossible(LivingEntity victim) {

        World world = victim.getWorld();

        if (world.isClient) {
            return;
        }

        boolean shouldApply = this.type.shouldApply(victim, this.config)
                && this.testPredicate(victim, (ServerWorld) world);

        if (shouldApply) {
            this.type.apply(victim, this.config);
        }
    }

    private boolean testPredicate(LivingEntity victim, ServerWorld world) {
        return this.predicate == null
                || this.predicate.test(
                new LootContext.Builder(world)
                        .parameter(LootContextParameters.THIS_ENTITY, victim)
                        .parameter(LootContextParameters.ORIGIN, victim.getPos())
                        .build(LootContextTypes.COMMAND)
        );
    }

    public static class Serializer implements JsonDeserializer<ConfiguredTemperatureEffect<?>> {

        public static final Gson GSON = LootGsons.getConditionGsonBuilder()
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
            LootCondition predicate = JsonHelper.deserialize(json, "entity", null, jsonDeserializationContext, LootCondition.class);

            return ConfiguredTemperatureEffect.fromJson(effectType, json.get("config"), predicate);
        }
    }

}
