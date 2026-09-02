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

package com.github.thedeathlycow.frostiful.entity.frostologer;

import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;

class FrostWandCastGoal extends RangedAttackGoal {
    private static final float HIT_TEMPERATURE_REDUCTION_PERCENT = 1.0f / 6.0f;

    private final Frostologer frostologer;

    public FrostWandCastGoal(Frostologer frostologer, double mobSpeed, int intervalTicks, float maxShootRange) {
        super(frostologer, mobSpeed, intervalTicks, maxShootRange);
        this.frostologer = frostologer;
    }

    @Override
    public boolean canUse() {
        return super.canUse()
                && frostologer.hasTarget()
                && !frostologer.isTargetRooted()
                && frostologer.getMainHandItem().is(FItems.FROST_WAND);
    }

    @Override
    public void start() {
        super.start();
        frostologer.setAggressive(true);
        frostologer.startUsingItem(InteractionHand.MAIN_HAND);
        this.startUsingFrostWand();
    }

    @Override
    public void stop() {
        super.stop();
        frostologer.setAggressive(false);
        frostologer.stopUsingItem();
        this.stopUsingFrostWand();
        if (frostologer.isTargetRooted()) {
            int cooling = Mth.floor(-frostologer.thermoo$getMinTemperature() * HIT_TEMPERATURE_REDUCTION_PERCENT);
            frostologer.thermoo$addTemperature(cooling);
        }
    }

    private void startUsingFrostWand() {
        frostologer.playSound(
                FSoundEvents.ITEM_FROST_WAND_PREPARE_CAST,
                1.0f, 1.0f
        );
        frostologer.getEntityData().set(Frostologer.IS_USING_FROST_WAND, true);
    }

    private void stopUsingFrostWand() {
        frostologer.getEntityData().set(Frostologer.IS_USING_FROST_WAND, false);
    }
}