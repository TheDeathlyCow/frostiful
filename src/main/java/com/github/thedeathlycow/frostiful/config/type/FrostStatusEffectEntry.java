package com.github.thedeathlycow.frostiful.config.type;

import com.github.thedeathlycow.frostiful.util.survival.FrostStatusEffect;
import com.github.thedeathlycow.simple.config.key.ConfigEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FrostStatusEffectEntry extends ConfigEntry<List<FrostStatusEffect>> {
    public FrostStatusEffectEntry(@NotNull String name, @NotNull List<FrostStatusEffect> defaultValue) {
        super(name, defaultValue, (Class) List.class);
    }

    @Override
    public List<FrostStatusEffect> deserialize(JsonElement jsonElement) {

        Iterable<JsonElement> elements;
        if (jsonElement.isJsonArray()) {
            elements = jsonElement.getAsJsonArray();
        } else {
            elements = Collections.singleton(jsonElement);
        }
        return deserialize(elements);
    }

    private List<FrostStatusEffect> deserialize(Iterable<JsonElement> list) {
        List<FrostStatusEffect> effects = new ArrayList<>();
        for (JsonElement elem : list) {
            effects.add(GSON.fromJson(elem, FrostStatusEffect.class));
        }
        return effects;
    }

    @Override
    public boolean isValid(List<FrostStatusEffect> frostStatusEffect) {
        return true;
    }

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(FrostStatusEffect.class, new FrostStatusEffect.Deserializer())
            .create();
}
