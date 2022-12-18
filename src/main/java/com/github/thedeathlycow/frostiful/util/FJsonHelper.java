package com.github.thedeathlycow.frostiful.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import net.minecraft.entity.attribute.EntityAttributeModifier;

import java.util.UUID;

public final class FJsonHelper {


    public static EntityAttributeModifier parseAttributeModifier(JsonElement jsonElement) throws JsonParseException {
        JsonObject json = jsonElement.getAsJsonObject();

        double value = json.get("value").getAsDouble();
        UUID uuid = UUID.fromString(json.get("uuid").getAsString());

        EntityAttributeModifier.Operation operation;
        String operationName = json.get("operation").getAsString();
        try {
            operation = EntityAttributeModifier.Operation.valueOf(operationName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new JsonSyntaxException("Unknown attribute operation: '" + operationName + "'");
        }

        String name = "";
        if (json.has("name")) {
            name = json.get("name").getAsString();
        }

        return new EntityAttributeModifier(uuid, name, value, operation);
    }


}
