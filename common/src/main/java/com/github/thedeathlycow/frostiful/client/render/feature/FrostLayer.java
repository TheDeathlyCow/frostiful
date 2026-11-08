/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.github.thedeathlycow.frostiful.client.render.feature;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.frostologer.Frostologer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.stream.Stream;

public enum FrostLayer {
    NONE(0.0f, null),
    LOW(-0.25f, Frostiful.id("textures/entity/illager/frostologer/low_frost.png")),
    MEDIUM(-0.5f, Frostiful.id("textures/entity/illager/frostologer/medium_frost.png")),
    HIGH(Frostologer.MAX_POWER_SCALE_START, Frostiful.id("textures/entity/illager/frostologer/high_frost.png"));

    public static final FrostLayer[] LAYERS_WITHOUT_NONE = Stream.of(FrostLayer.values())
            .filter(layer -> layer != NONE)
            .sorted(Comparator.comparingDouble(layer -> layer.maximumTemperatureScale))
            .toArray(FrostLayer[]::new);

    private final float maximumTemperatureScale;
    private final Identifier texture;

    FrostLayer(float maximumTemperatureScale, Identifier texture) {
        this.maximumTemperatureScale = maximumTemperatureScale;
        this.texture = texture;
    }

    public static FrostLayer fromFrostologer(Frostologer frostologer) {
        float scale = frostologer.thermoo$getTemperatureScale();
        for (var layer : LAYERS_WITHOUT_NONE) {
            if (scale < layer.maximumTemperatureScale) {
                return layer;
            }
        }
        return NONE;
    }

    @Nullable
    public Identifier getTexture() {
        return texture;
    }
}
