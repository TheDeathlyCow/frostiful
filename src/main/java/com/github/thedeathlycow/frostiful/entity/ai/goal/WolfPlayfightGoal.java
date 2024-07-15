package com.github.thedeathlycow.frostiful.entity.ai.goal;

import com.github.thedeathlycow.frostiful.registry.FLootTables;
import net.minecraft.entity.passive.WolfEntity;

public class WolfPlayfightGoal extends PlayFightGoal<WolfEntity> {

    public WolfPlayfightGoal(WolfEntity wolf, float adultChance, float babyChance) {
        super(wolf, WolfEntity.class, adultChance, babyChance, FLootTables.WOLF_PLAYFIGHT_GAMEPLAY);
    }

    @Override
    public boolean canStart() {
        boolean foundTarget = super.canStart();

        if (foundTarget) {
            assert this.target != null;
            return !this.mob.isTamed() && !this.target.isTamed();
        }

        return false;
    }
}
