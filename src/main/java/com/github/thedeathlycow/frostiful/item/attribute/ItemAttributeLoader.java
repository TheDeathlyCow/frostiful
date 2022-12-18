package com.github.thedeathlycow.frostiful.item.attribute;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.google.gson.JsonParseException;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ItemAttributeLoader implements SimpleSynchronousResourceReloadListener {

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

                newValues.put(entry.getKey(), modifier);
            } catch (IOException | JsonParseException e) {
                Frostiful.LOGGER.error("An error occurred while loading item attribute modifier {}: {}", entry.getKey(), e);
            }
        }

        this.clearValues();
        this.values.putAll(newValues);

        int numModifiers = this.values.size();
        Frostiful.LOGGER.info("Loaded {} item attribute modifier{}", numModifiers, numModifiers == 1 ? "" : "s");

    }

    private void clearValues() {

        this.values.values().forEach(ItemAttributeModifier::disable);

        this.values.clear();
    }

    @Nullable
    public ItemAttributeModifier get(Identifier id) {
        return this.values.getOrDefault(id, null);
    }
}
