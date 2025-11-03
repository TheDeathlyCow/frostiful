package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.frostologer.FrostologerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.stream.Stream;

@Environment(EnvType.CLIENT)
public enum FrostLayer {
    NONE(0.0f, null),
    LOW(-0.25f, Frostiful.location("textures/entity/illager/frostologer/low_frost.png")),
    MEDIUM(-0.5f, Frostiful.location("textures/entity/illager/frostologer/medium_frost.png")),
    HIGH(FrostologerEntity.MAX_POWER_SCALE_START, Frostiful.location("textures/entity/illager/frostologer/high_frost.png"));

    public static final FrostLayer[] LAYERS_WITHOUT_NONE = Stream.of(FrostLayer.values())
            .filter(layer -> layer != NONE)
            .sorted(Comparator.comparingDouble(layer -> layer.maximumTemperatureScale))
            .toArray(FrostLayer[]::new);

    private final float maximumTemperatureScale;
    private final ResourceLocation texture;

    FrostLayer(float maximumTemperatureScale, ResourceLocation texture) {
        this.maximumTemperatureScale = maximumTemperatureScale;
        this.texture = texture;
    }

    public static FrostLayer fromFrostologer(FrostologerEntity frostologer) {
        float scale = frostologer.thermoo$getTemperatureScale();
        for (var layer : LAYERS_WITHOUT_NONE) {
            if (scale < layer.maximumTemperatureScale) {
                return layer;
            }
        }
        return NONE;
    }

    @Nullable
    public ResourceLocation getTexture() {
        return texture;
    }
}
