package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.predicate.NumberRange;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.JsonHelper;
import org.jetbrains.annotations.Nullable;

public class DamageTemperatureEffect extends TemperatureEffect<DamageTemperatureEffect.Config> {


    private final DamageSource damageSource;

    public DamageTemperatureEffect(DamageSource damageSource) {
        this.damageSource = damageSource;
    }

    @Override
    public void apply(LivingEntity victim, ServerWorld serverWorld, Config config) {
        if (!victim.world.isClient) {
            victim.damage(this.damageSource, config.amount);
        }
    }

    @Override
    public boolean shouldApply(LivingEntity victim, Config config) {
        return victim.age % config.damagePeriod == 0 && config.progressThreshold.test(victim.getFreezingScale());
    }

    @Override
    public Config configFromJson(JsonElement json, JsonDeserializationContext context) throws JsonParseException {
        return Config.fromJson(json, context);
    }

    public record Config(
            NumberRange.FloatRange progressThreshold,
            float amount,
            int damagePeriod
    ) {

        public static Config fromJson(JsonElement json, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();

            // get progress range
            NumberRange.FloatRange progressThreshold = NumberRange.FloatRange.fromJson(object.get("progress_threshold"));

            float amount = object.get("amount").getAsFloat();

            int damagePeriod = object.get("damage_period").getAsInt();

            return new Config(progressThreshold, amount, damagePeriod);
        }
    }

}
