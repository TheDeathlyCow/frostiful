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

package com.github.thedeathlycow.frostiful.client.gui;

import com.github.thedeathlycow.frostiful.registry.FDataAttachments;
import com.github.thedeathlycow.frostiful.survival.system.FrostRootSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;

public class RootedOverlayRenderer {
    private static final Identifier FROSTIFUL_ROOTED_OVERLAY = Identifier.withDefaultNamespace("textures/block/ice.png");

    public static void render(
            LivingEntity entity,
            GuiGraphicsExtractor extractor,
            DeltaTracker tickCounter,
            OverlayRenderCallback callback
    ) {
        if (entity.hasAttached(FDataAttachments.FROST_WAND_ROOT_TICKS)) {
            int rootTicksRemaining = entity.getAttachedOrThrow(FDataAttachments.FROST_WAND_ROOT_TICKS);

            if (rootTicksRemaining > 0) {
                callback.renderOverlay(extractor, FROSTIFUL_ROOTED_OVERLAY, FrostRootSystem.getProgress(rootTicksRemaining));
            }
        }
    }

    private RootedOverlayRenderer() {
    }

}
