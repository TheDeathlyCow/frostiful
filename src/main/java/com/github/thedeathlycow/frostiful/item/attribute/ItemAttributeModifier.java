package com.github.thedeathlycow.frostiful.item.attribute;

import com.google.common.collect.Multimap;
import com.google.gson.*;
import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemAttributeModifier {
    private final Item item;
    private final EquipmentSlot slot;
    private final List<AttributeHolder> attributeModifiers;

    private boolean isEnabled = true;

    public ItemAttributeModifier(
            Item item,
            EquipmentSlot slot,
            List<AttributeHolder> attributeModifiers
    ) {
        this.item = item;
        this.slot = slot;
        this.attributeModifiers = attributeModifiers;

        ModifyItemAttributeModifiersCallback.EVENT.register(this::apply);
    }

    public void apply(ItemStack stack, EquipmentSlot slot, Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers) {
        if (this.isEnabled && stack.isOf(this.item) && slot == this.slot) {
            for (AttributeHolder holder : this.attributeModifiers) {
                attributeModifiers.put(holder.attribute(), holder.modifier());
            }
        }
    }

    public void disable() {
        this.isEnabled = false;
    }

    public static class Serializer implements JsonDeserializer<ItemAttributeModifier> {

        public static final Gson GSON = new GsonBuilder()
                .registerTypeAdapter(ItemAttributeModifier.class, new Serializer())
                .create();

        @Override
        public ItemAttributeModifier deserialize(JsonElement jsonElement, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject json = jsonElement.getAsJsonObject();

            // parse item
            Identifier itemID = new Identifier(json.get("item").getAsString());
            if (!Registry.ITEM.containsId(itemID)) {
                throw new JsonSyntaxException("Unknown item '" + itemID + "'");
            }
            Item item = Registry.ITEM.get(itemID);

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
                for (JsonElement attributeModifier :  attributesJson.getAsJsonArray()) {
                    attributeModifiers.add(AttributeHolder.fromJson(attributeModifier));
                }
            } else {
                throw new JsonSyntaxException("Malformed item attribute modifiers:\n" + attributesJson.toString());
            }

            return new ItemAttributeModifier(item, slot, attributeModifiers);
        }
    }
}
