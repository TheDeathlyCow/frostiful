package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.google.gson.JsonSyntaxException;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SurvivalEffectLoader implements SimpleSynchronousResourceReloadListener {

    public static final SurvivalEffectLoader FROSTIFUL = new SurvivalEffectLoader(
            new Identifier(Frostiful.MODID, "temperature_effects")
    );

    protected static final Logger LOGGER = LoggerFactory.getLogger(SurvivalEffectLoader.class);
    private final List<SurvivalEffectInstance<?>> effects = new ArrayList<>();
    private final Identifier identifier;

    public SurvivalEffectLoader(Identifier identifier) {
        this.identifier = identifier;
    }

    public List<SurvivalEffectInstance<?>> getEffects() {
        return effects;
    }

    @Override
    public Identifier getFabricId() {
        return this.identifier;
    }

    @Override
    public void reload(ResourceManager manager) {

        List<SurvivalEffectInstance<?>> newEffects = new ArrayList<>();

        for (var entry : manager.findResources("frostiful/temperature_effects", id -> id.getPath().endsWith(".json")).entrySet()) {
            try (BufferedReader reader = entry.getValue().getReader()) {

                SurvivalEffectInstance<?> effect = SurvivalEffectInstance.Serializer.GSON.fromJson(
                        reader,
                        SurvivalEffectInstance.class
                );

                newEffects.add(effect);
            } catch (IOException | JsonSyntaxException e) {
                LOGGER.error("An error occurred while loading survival effect {}: {}", entry.getKey(), e);
            }
        }

        this.effects.clear();
        this.effects.addAll(newEffects);

        int numEffects = this.effects.size();
        LOGGER.info("Loaded {} survival effect{}", numEffects, numEffects == 1 ? "" : "s");

    }
}
