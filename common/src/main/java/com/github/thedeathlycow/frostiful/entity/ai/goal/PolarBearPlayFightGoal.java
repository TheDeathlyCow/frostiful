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
import net.minecraft.world.entity.animal.polarbear.PolarBear;

public class PolarBearPlayFightGoal extends PlayFightGoal<PolarBear> {

    public PolarBearPlayFightGoal(PolarBear polarBear, float adultChance, float babyChance) {
        super(polarBear, PolarBear.class, adultChance, babyChance, FLootTables.POLAR_BEAR_PLAYFIGHT_GAMEPLAY);
    }

    @Override
    public void stop() {
        this.mob.setStanding(false);
        if (this.target != null) {
            this.target.setStanding(false);
        }
        super.stop();
    }

    @Override
    protected void playFight() {
        this.mob.setStanding(true);
        if (this.target != null) {
            this.target.setStanding(true);
        }
        super.playFight();
    }
}
