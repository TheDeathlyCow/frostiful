package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.google.gson.JsonParseException;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class TemperatureEffectLoader implements SimpleSynchronousResourceReloadListener {

    public static final TemperatureEffectLoader INSTANCE = new TemperatureEffectLoader(
            new Identifier(Frostiful.MODID, "temperature_effects")
    );

    private final List<ConfiguredTemperatureEffect<?>> effects = new ArrayList<>();
    private final Identifier identifier;

    private TemperatureEffectLoader(Identifier identifier) {
        this.identifier = identifier;
    }

    public List<ConfiguredTemperatureEffect<?>> getEffects() {
        return effects;
    }

    @Override
    public Identifier getFabricId() {
        return this.identifier;
    }

    @Override
    public void reload(ResourceManager manager) {

        List<ConfiguredTemperatureEffect<?>> newEffects = new ArrayList<>();

        for (var entry : manager.findResources("frostiful/temperature_effects", id -> id.getPath().endsWith(".json")).entrySet()) {
            try (BufferedReader reader = entry.getValue().getReader()) {

                ConfiguredTemperatureEffect<?> effect = ConfiguredTemperatureEffect.Serializer.GSON.fromJson(
                        reader,
                        ConfiguredTemperatureEffect.class
                );

                newEffects.add(effect);
            } catch (IOException | JsonParseException e) {
                Frostiful.LOGGER.error("An error occurred while loading temperature effect {}: {}", entry.getKey(), e);
            }
        }

        this.effects.clear();
        this.effects.addAll(newEffects);

        int numEffects = this.effects.size();
        Frostiful.LOGGER.info("Loaded {} survival effect{}", numEffects, numEffects == 1 ? "" : "s");

    }
}
