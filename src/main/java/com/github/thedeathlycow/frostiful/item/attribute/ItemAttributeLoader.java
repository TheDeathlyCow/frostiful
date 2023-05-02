package com.github.thedeathlycow.frostiful.item.attribute;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.google.common.collect.Multimap;
import com.google.gson.JsonParseException;
import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ItemAttributeLoader implements SimpleSynchronousResourceReloadListener, ModifyItemAttributeModifiersCallback {

    public static final ItemAttributeLoader INSTANCE = new ItemAttributeLoader(Frostiful.id("item_attribute_modifiers"));

    private final Identifier identifier;

    private final Map<Identifier, ItemAttributeModifier> values = new HashMap<>();

    public ItemAttributeLoader(Identifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public Identifier getFabricId() {
        return this.identifier;
    }

    @Override
    public void reload(ResourceManager manager) {
        Map<Identifier, ItemAttributeModifier> newValues = new HashMap<>();

        for (var entry : manager.findResources("frostiful/item_attribute_modifiers", id -> id.getPath().endsWith(".json")).entrySet()) {
            try (BufferedReader reader = entry.getValue().getReader()) {

                ItemAttributeModifier modifier = ItemAttributeModifier.Serializer.GSON.fromJson(
                        reader,
                        ItemAttributeModifier.class
                );

                if (modifier.isItemPresent()) {
                    newValues.put(entry.getKey(), modifier);
                } else {
                    Frostiful.LOGGER.trace("Skipping item attribute modifier {}, as item is not present", entry.getKey());
                }
            } catch (IOException | JsonParseException e) {
                Frostiful.LOGGER.error("An error occurred while loading item attribute modifier {}: {}", entry.getKey(), e);
            }
        }

        this.values.clear();
        this.values.putAll(newValues);

        int numModifiers = this.values.size();
        Frostiful.LOGGER.info("Loaded {} item attribute modifier{}", numModifiers, numModifiers == 1 ? "" : "s");

    }

    @Override
    public void modifyAttributeModifiers(ItemStack stack, EquipmentSlot slot, Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers) {
        for (var value : this.values.values()) {
            value.apply(stack, slot, attributeModifiers);
        }
    }
}
