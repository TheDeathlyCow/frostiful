package com.github.thedeathlycow.frostiful.entity.ai.goal;

import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.WolfEntity;

public class WolfPlayfightGoal extends PlayFightGoal {

    public WolfPlayfightGoal(WolfEntity mob, float adultChance, float babyChance) {
        super(mob, adultChance, babyChance, null);
    }

    @Override
    public boolean canStart() {
        boolean foundTarget = super.canStart();

        if (foundTarget) {
            assert this.target != null;
            WolfEntity wolfMob = ((WolfEntity) this.mob);
            WolfEntity targetWolf = ((WolfEntity) this.target);
            return !wolfMob.isTamed() && wolfMob.isTamed() == targetWolf.isTamed();
        }

        return false;
    }
}
