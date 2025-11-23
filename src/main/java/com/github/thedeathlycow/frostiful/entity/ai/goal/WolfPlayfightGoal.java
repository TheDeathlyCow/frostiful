package com.github.thedeathlycow.frostiful.entity.ai.goal;

import com.github.thedeathlycow.frostiful.registry.FLootTables;
import net.minecraft.world.entity.animal.Wolf;

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
