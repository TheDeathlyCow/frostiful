package com.github.thedeathlycow.frostiful.item.attribute;

import com.google.common.collect.Multimap;
import com.google.gson.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ItemAttributeModifier {

    @Nullable
    private final Item item;
    private final EquipmentSlot slot;
    private final List<AttributeHolder> attributeModifiers;


    public ItemAttributeModifier(
            @Nullable Item item,
            EquipmentSlot slot,
            List<AttributeHolder> attributeModifiers
    ) {
        this.item = item;
        this.slot = slot;
        this.attributeModifiers = attributeModifiers;
    }

    public void apply(ItemStack stack, EquipmentSlot slot, Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers) {
        if (this.item != null && stack.isOf(this.item) && slot == this.slot) {
            for (AttributeHolder holder : this.attributeModifiers) {
                attributeModifiers.put(holder.attribute(), holder.modifier());
            }
        }
    }

    public boolean isItemPresent() {
        return this.item != null;
    }

    public static class Serializer implements JsonDeserializer<ItemAttributeModifier> {

        public static final Gson GSON = new GsonBuilder()
                .registerTypeAdapter(ItemAttributeModifier.class, new Serializer())
                .create();

        @Override
        public ItemAttributeModifier deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject json = jsonElement.getAsJsonObject();

            // parse item
            Item item = this.getItem(json.get("item"));

            // parse slot

            EquipmentSlot slot;
            String slotName = json.get("slot").getAsString();
            try {
                slot = EquipmentSlot.byName(slotName);
            } catch (IllegalArgumentException e) {
                throw new JsonSyntaxException("Unknown slot name '" + slotName + "'");
            }

            // parse modifiers
            JsonElement attributesJson = json.get("attribute_modifiers");
            List<AttributeHolder> attributeModifiers;

            if (attributesJson.isJsonObject()) {
                attributeModifiers = Collections.singletonList(AttributeHolder.fromJson(attributesJson));
            } else if (attributesJson.isJsonArray()) {
                attributeModifiers = new ArrayList<>();
                for (JsonElement attributeModifier : attributesJson.getAsJsonArray()) {
                    attributeModifiers.add(AttributeHolder.fromJson(attributeModifier));
                }
            } else {
                throw new JsonSyntaxException("Malformed item attribute modifiers:\n" + attributesJson.toString());
            }

            return new ItemAttributeModifier(item, slot, attributeModifiers);
        }

        @Nullable
        private Item getItem(JsonElement jsonElement) throws JsonSyntaxException {

            String id;
            boolean required = true;

            if (jsonElement.isJsonPrimitive()) {
                id = jsonElement.getAsString();
            } else if (jsonElement.isJsonObject()) {
                JsonObject json = jsonElement.getAsJsonObject();
                id = json.get("item").getAsString();
                required = json.get("required").getAsBoolean();
            } else {
                throw new JsonSyntaxException("Invalid JSON: " + jsonElement);
            }

            Identifier itemID = new Identifier(id);
            Optional<Item> item = Registries.ITEM.getOrEmpty(itemID);

            if (item.isEmpty() && required) {
                throw new JsonSyntaxException("Unknown item '" + itemID + "'");
            }

            return item.orElse(null);
        }
    }
}
