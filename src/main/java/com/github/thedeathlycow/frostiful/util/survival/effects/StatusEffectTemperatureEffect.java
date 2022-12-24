package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.predicate.NumberRange;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class StatusEffectTemperatureEffect extends TemperatureEffect<StatusEffectTemperatureEffect.Config> {

    @Override
    public void apply(LivingEntity victim, Config config) {
        victim.addStatusEffect(createEffectInstance(config), null);
    }

    @Override
    public boolean shouldApply(LivingEntity victim, Config config) {

        if (config.progressThreshold.test(victim.getFreezingScale())) {
            StatusEffectInstance currentEffectInstance = victim.getStatusEffect(config.effect());

            if (currentEffectInstance == null) {
                return true;
            }

            if (currentEffectInstance.getAmplifier() < config.amplifier()) {
                return true;
            }

            return currentEffectInstance.getDuration() < (config.duration >> 1); // fast divide by 2
        }

        return false;
    }

    @Override
    public Config configFromJson(JsonElement json) throws JsonParseException {
        return Config.fromJson(json);
    }

    private static StatusEffectInstance createEffectInstance(Config config) {
        return createEffectInstance(config, true, true);
    }

    private static StatusEffectInstance createEffectInstance(Config config, boolean ambient, boolean visible) {
        return new StatusEffectInstance(config.effect(), config.duration(), config.amplifier(), ambient, visible);
    }

    public record Config(
            NumberRange.FloatRange progressThreshold,
            StatusEffect effect,
            int duration,
            int amplifier
    ) {
        public static Config fromJson(JsonElement json) throws JsonParseException {
            JsonObject object = json.getAsJsonObject();

            // get numbers
            NumberRange.FloatRange progressThreshold = NumberRange.FloatRange.fromJson(object.get("progress_threshold"));
            int amplifier = object.get("amplifier").getAsInt();

            // duration defaults to 100
            int duration = 100;
            if (object.has("duration")) {
                duration = object.get("duration").getAsInt();
            }

            // get effect
            Identifier effectID = new Identifier(object.get("effect").getAsString());
            StatusEffect effect = Registry.STATUS_EFFECT.get(effectID);
            if (effect == null) {
                throw new JsonParseException("Unknown status effect: " + effectID);
            }

            return new Config(progressThreshold, effect, duration, amplifier);
        }
    }

}
