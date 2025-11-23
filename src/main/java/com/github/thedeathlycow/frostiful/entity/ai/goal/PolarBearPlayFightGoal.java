package com.github.thedeathlycow.frostiful.entity.ai.goal;

import com.github.thedeathlycow.frostiful.registry.FLootTables;
import net.minecraft.world.entity.animal.PolarBear;

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
