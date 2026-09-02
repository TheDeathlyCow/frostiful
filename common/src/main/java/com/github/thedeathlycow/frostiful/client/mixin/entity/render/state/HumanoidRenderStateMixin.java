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

import com.github.thedeathlycow.frostiful.client.render.state.FHumanoidRenderState;
import com.github.thedeathlycow.frostiful.item.component.CapeComponent;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(HumanoidRenderState.class)
public class HumanoidRenderStateMixin implements FHumanoidRenderState {
    @Unique
    private boolean frostiful$wearingIceSkates = false;

    @Unique
    private CapeComponent frostiful$cape = null;

    @Override
    @Unique
    public boolean frostiful$wearingIceSkates() {
        return this.frostiful$wearingIceSkates;
    }

    @Override
    public void frostiful$wearingIceSkates(boolean value) {
        this.frostiful$wearingIceSkates = value;
    }

    @Override
    public CapeComponent frostiful$cape() {
        return this.frostiful$cape;
    }

    @Override
    public void frostiful$cape(CapeComponent cape) {
        this.frostiful$cape = cape;
    }
}