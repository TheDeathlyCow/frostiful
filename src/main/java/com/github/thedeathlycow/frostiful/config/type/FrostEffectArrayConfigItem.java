package com.github.thedeathlycow.frostiful.config.type;

import com.github.thedeathlycow.frostiful.util.survival.FrostStatusEffect;
import com.google.gson.*;
import com.oroarmor.config.ArrayConfigItem;
import com.oroarmor.config.ConfigItem;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Consumer;

public class FrostEffectArrayConfigItem extends ArrayConfigItem<FrostStatusEffect> {
    public FrostEffectArrayConfigItem(String name, FrostStatusEffect[] defaultValue, String details) {
        super(name, defaultValue, details);
    }

    public FrostEffectArrayConfigItem(String name, FrostStatusEffect[] defaultValue, String details, @Nullable Consumer<ConfigItem<FrostStatusEffect[]>> onChange) {
        super(name, defaultValue, details, onChange);
    }

    public void fromJson(JsonElement element) {
        for (int i = 0; i < element.getAsJsonArray().size(); i++) {
            FrostStatusEffect newValue;
            JsonElement arrayElement = element.getAsJsonArray().get(i);
            newValue = GSON.fromJson(arrayElement, FrostStatusEffect.class);
            value[i] = newValue;
        }
        if (value != null) {
            setValue(value);
        }
    }

    @Override
    public void toJson(JsonObject object) {
        JsonArray array = new JsonArray();
        for (FrostStatusEffect effect : value) {
            JsonElement element = GSON.toJsonTree(effect);
            array.add(element);
        }

        object.add(this.name, array);
    }

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(FrostStatusEffect.class, new FrostStatusEffect.Deserializer())
            .create();
}
