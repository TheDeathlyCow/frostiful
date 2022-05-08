package com.github.thedeathlycow.frostiful.config.type;

import com.github.thedeathlycow.frostiful.util.survival.FrostStatusEffect;
import com.github.thedeathlycow.simple.config.entry.collection.ListEntry;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class FrostStatusEffectEntry extends ListEntry<FrostStatusEffect> {
    public FrostStatusEffectEntry(@NotNull String name, @NotNull List<FrostStatusEffect> defaultValue) {
        super(name, defaultValue, FrostStatusEffect.class, LinkedList::new);
    }

    @Override
    protected FrostStatusEffect deserializeElement(JsonElement element) {
        return GSON.fromJson(element, FrostStatusEffect.class);
    }

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(FrostStatusEffect.class, new FrostStatusEffect.Deserializer())
            .create();
}
