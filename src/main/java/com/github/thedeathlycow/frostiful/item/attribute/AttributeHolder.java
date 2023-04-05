package com.github.thedeathlycow.frostiful.item.attribute;

import com.github.thedeathlycow.frostiful.util.FJsonHelper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public record AttributeHolder(
        EntityAttribute attribute,
        EntityAttributeModifier modifier
) {

    public static AttributeHolder fromJson(JsonElement jsonElement) throws JsonSyntaxException {
        JsonObject json = jsonElement.getAsJsonObject();

        Identifier attributeID = new Identifier(json.get("attribute").getAsString());

        if (!Registry.ATTRIBUTE.containsId(attributeID)) {
            throw new JsonSyntaxException("Unknown entity attribute: '" + attributeID + "'");
        }

        EntityAttribute attribute = Registry.ATTRIBUTE.get(attributeID);

        EntityAttributeModifier modifier = FJsonHelper.parseAttributeModifier(json.get("modifier"));

        return new AttributeHolder(attribute, modifier);
    }

}
