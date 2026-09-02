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

package com.github.thedeathlycow.frostiful.client.mixin.entity.render.state;

import com.github.thedeathlycow.frostiful.client.render.state.FPolarBearEntityRenderState;
import net.minecraft.client.renderer.entity.state.PolarBearRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PolarBearRenderState.class)
public class PolarBearRenderStateMixin implements FPolarBearEntityRenderState {
    private boolean frostiful$wasSheared = false;

    @Override
    @Unique
    public boolean frostiful$wasSheared() {
        return this.frostiful$wasSheared;
    }

    @Override
    @Unique
    public void frostiful$wasSheared(boolean value) {
        this.frostiful$wasSheared = value;
    }
}