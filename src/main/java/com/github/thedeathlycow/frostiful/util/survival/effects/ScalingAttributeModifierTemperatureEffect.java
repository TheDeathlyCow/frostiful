package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

@Deprecated
public class ScalingAttributeModifierTemperatureEffect extends TemperatureEffect<ScalingAttributeModifierTemperatureEffect.Config> {

    @Override
    public void apply(LivingEntity victim, ServerWorld serverWorld, Config config) {
        this.removeModifier(victim, config);
        this.addModifierIfNeeded(victim, config);
    }

    @Override
    public boolean shouldApply(LivingEntity victim, Config config) {
        return true;
    }

    @Override
    public Config configFromJson(JsonElement json, JsonDeserializationContext context) throws JsonParseException {
        return Config.fromJson(json);
    }

    private void removeModifier(LivingEntity victim, Config config) {
        EntityAttributeInstance attrInstance = victim.getAttributeInstance(config.attribute);
        if (attrInstance != null) {
            if (attrInstance.getModifier(config.uuid) != null) {
                attrInstance.removeModifier(config.uuid);
            }
        }
    }

    private void addModifierIfNeeded(LivingEntity victim, Config config) {

        if (victim.getFrozenTicks() <= 0) {
            return;
        }

        EntityAttributeInstance attrInstance = victim.getAttributeInstance(config.attribute);
        if (attrInstance == null) {
            return;
        }

        float amount = config.scale * victim.getFreezingScale();

        attrInstance.addTemporaryModifier(
                new EntityAttributeModifier(
                        config.uuid,
                        config.name,
                        amount,
                        config.operation
                )
        );
    }

    public record Config(
            float scale,
            EntityAttribute attribute,
            UUID uuid,
            String name,
            EntityAttributeModifier.Operation operation
    ) {

        public static Config fromJson(JsonElement jsonElement) throws JsonParseException {

            //// init defaults ////
            float scale = 1.0f;
            String name = "";

            JsonObject json = jsonElement.getAsJsonObject();

            //// overwrite defaults if present ////
            if (json.has("scale")) {
                scale = json.get("scale").getAsFloat();
            }

            if (json.has("name")) {
                name = json.get("name").getAsString();
            }

            //// grab required values ////

            UUID id = UUID.fromString(json.get("modifier_uuid").getAsString());

            Identifier attrID = new Identifier(json.get("attribute_type").getAsString());
            EntityAttribute attribute = Registry.ATTRIBUTE.get(attrID);

            if (attribute == null) {
                throw new JsonParseException("Unknown attribute: " + attrID);
            }

            EntityAttributeModifier.Operation operation = EntityAttributeModifier.Operation.valueOf(
                    json.get("operation").getAsString().toUpperCase()
            );

            return new Config(scale, attribute, id, name, operation);
        }
    }

}
