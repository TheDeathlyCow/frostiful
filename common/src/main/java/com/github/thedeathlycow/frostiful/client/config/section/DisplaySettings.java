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

package com.github.thedeathlycow.frostiful.client.config.section;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.Translate;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.FloatSlider;
import dev.isxander.yacl3.config.v2.api.autogen.TickBox;

public class DisplaySettings {
    private static final String CATEGORY = FrostifulConfigYACL.MAIN_CATEGORY_NAME;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Enable frosty heart overlay")
    @SerialEntry(comment = "Toggle the frosty heart temperature display on the health bar.")
    @TickBox
    boolean enableFrostyHeartOverlay = true;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Frosty camera overlay temperature scale start")
    @SerialEntry(comment = "The temperature scale below which the frosty camera overlay will begin to appear. Must be between -1 and 0 (inclusive).")
    @FloatSlider(min = -1f, max = 0f, step = 0.1f)
    float renderFrostyCameraOverlayBelow = -0.5f;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Enable drip particles")
    @SerialEntry(comment = "Toggles the water drip particles when wet. Has no effect if Scorchful is installed.")
    @TickBox
    boolean enableDripParticles = true;

    @AutoGen(category = CATEGORY)
    @Translate.Name("Disable hurt Polar Bear skin")
    @SerialEntry(comment = "When Polar Bears have been recently brushed, they show a hurt skin. Disable this feature if the texture is not compatible with your resource pack.")
    @TickBox
    boolean disableHurtPolarBearSkin = false;

    public boolean enableFrostyHeartOverlay() {
        return enableFrostyHeartOverlay;
    }

    public float frostOverlayStart() {
        return renderFrostyCameraOverlayBelow;
    }

    public boolean enableDripParticles() {
        return enableDripParticles;
    }

    public boolean disableHurtPolarBearSkin() {
        return disableHurtPolarBearSkin;
    }
}