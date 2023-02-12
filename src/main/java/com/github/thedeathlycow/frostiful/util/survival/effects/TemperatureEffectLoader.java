package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.google.gson.JsonParseException;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

@Deprecated
public final class TemperatureEffectLoader implements SimpleSynchronousResourceReloadListener {

    public static final TemperatureEffectLoader INSTANCE = new TemperatureEffectLoader(
            new Identifier(Frostiful.MODID, "temperature_effects")
    );

    private final Map<Identifier, ConfiguredTemperatureEffect<?>> effects = new HashMap<>();
    private final Identifier identifier;

    private TemperatureEffectLoader(Identifier identifier) {
        this.identifier = identifier;
    }

    public Collection<ConfiguredTemperatureEffect<?>> getEffects() {
        return effects.values();
    }

    @Override
    public Identifier getFabricId() {
        return this.identifier;
    }

    @Override
    public void reload(ResourceManager manager) {

        Map<Identifier, ConfiguredTemperatureEffect<?>> newEffects = new HashMap<>();

        for (var entry : manager.findResources("frostiful/temperature_effects", id -> id.getPath().endsWith(".json")).entrySet()) {
            try (BufferedReader reader = entry.getValue().getReader()) {

                ConfiguredTemperatureEffect<?> effect = ConfiguredTemperatureEffect.Serializer.GSON.fromJson(
                        reader,
                        ConfiguredTemperatureEffect.class
                );

                newEffects.put(entry.getKey(), effect);
            } catch (IOException | JsonParseException e) {
                Frostiful.LOGGER.error("An error occurred while loading temperature effect {}: {}", entry.getKey(), e);
            }
        }

        this.effects.clear();
        this.effects.putAll(newEffects);

        int numEffects = this.effects.size();
        Frostiful.LOGGER.info("Loaded {} survival effect{}", numEffects, numEffects == 1 ? "" : "s");

    }
}
