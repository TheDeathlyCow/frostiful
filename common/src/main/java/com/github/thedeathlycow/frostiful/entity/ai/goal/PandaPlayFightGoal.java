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
import net.minecraft.world.entity.animal.panda.Panda;

public class PandaPlayFightGoal extends PlayFightGoal<Panda> {
    public PandaPlayFightGoal(Panda panda, float adultChance, float babyChance) {
        super(panda, Panda.class, adultChance, babyChance, FLootTables.PANDA_PLAYFIGHT_GAMEPLAY);
    }

    @Override
    public boolean canUse() {
        if (!this.mob.canPerformAction()) {
            return false;
        }

        boolean foundTarget = super.canUse();

        if (foundTarget) {
            assert this.target != null;
            return canVariantPlay(this.mob) && canVariantPlay(this.target);
        }

        return false;
    }

    private static boolean canVariantPlay(Panda panda) {
        Panda.Gene gene = panda.getVariant();
        return panda.isBaby() ? canBabyVariantPlay(gene) : canAdultVariantPlay(gene);
    }

    private static boolean canBabyVariantPlay(Panda.Gene gene) {
        return gene != Panda.Gene.WEAK && gene != Panda.Gene.WORRIED;
    }

    private static boolean canAdultVariantPlay(Panda.Gene gene) {
        return gene == Panda.Gene.AGGRESSIVE || gene == Panda.Gene.PLAYFUL;
    }
}
