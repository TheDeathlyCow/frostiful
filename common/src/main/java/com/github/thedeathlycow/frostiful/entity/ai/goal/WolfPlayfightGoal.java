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

package com.github.thedeathlycow.frostiful.entity.ai.goal;

import com.github.thedeathlycow.frostiful.registry.FLootTables;
import net.minecraft.world.entity.animal.wolf.Wolf;

public class WolfPlayfightGoal extends PlayFightGoal<Wolf> {

    public WolfPlayfightGoal(Wolf wolf, float adultChance, float babyChance) {
        super(wolf, Wolf.class, adultChance, babyChance, FLootTables.WOLF_PLAYFIGHT_GAMEPLAY);
    }

    @Override
    public boolean canUse() {
        boolean foundTarget = super.canUse();

        if (foundTarget) {
            assert this.target != null;
            return !this.mob.isTame() && !this.target.isTame();
        }

        return false;
    }
}
