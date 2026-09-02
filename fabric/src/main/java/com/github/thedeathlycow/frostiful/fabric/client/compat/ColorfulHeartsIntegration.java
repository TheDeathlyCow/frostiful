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

package com.github.thedeathlycow.frostiful.fabric.client.compat;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import terrails.colorfulhearts.api.fabric.ColorfulHeartsApi;
import terrails.colorfulhearts.api.fabric.event.FabHeartEvents;
import terrails.colorfulhearts.api.heart.Hearts;
import terrails.colorfulhearts.api.heart.drawing.OverlayHeart;

public class ColorfulHeartsIntegration implements ColorfulHeartsApi {
    public ColorfulHeartsIntegration() {
        OverlayHeart frozenHearts = Hearts.OVERLAY_HEARTS.get(Identifier.withDefaultNamespace("frozen"));
        if (frozenHearts != null) {
            FabHeartEvents.PRE_RENDER.register(event -> {
                LocalPlayer player = Minecraft.getInstance().player;
                if (player != null && player.thermoo$getTemperatureScale() <= -0.99) {
                    event.setOverlayHeart(frozenHearts);
                }
            });
        }
    }
}
