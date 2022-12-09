package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class StatusEffectSurvivalEffectType extends SurvivalEffectType<StatusEffectSurvivalEffectType.Config> {

    @Override
    public void apply(LivingEntity victim, Config config) {
        victim.addStatusEffect(createEffectInstance(config), null);
    }

    @Override
    public boolean shouldApply(LivingEntity victim, Config config) {
        StatusEffectInstance currentEffectInstance = victim.getStatusEffect(config.effect());
        return victim.getFreezingScale() >= config.progressThreshold()
                    && (currentEffectInstance == null || currentEffectInstance.getAmplifier() < config.amplifier());
    }

    @Override
    public Config configFromJson(JsonElement json) {
        return Config.fromJson(json);
    }

    private static StatusEffectInstance createEffectInstance(Config config) {
        return createEffectInstance(config, true, true);
    }

    private static StatusEffectInstance createEffectInstance(Config config, boolean ambient, boolean visible) {
        return new StatusEffectInstance(config.effect(), config.duration(), config.amplifier(), ambient, visible);
    }

    public record Config(
            float progressThreshold,
            StatusEffect effect,
            int duration,
            int amplifier
    ) {
        public static Config fromJson(JsonElement json) {
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

            return new Config(progressThreshold, effect, duration, amplifier);
        }
    }

}
