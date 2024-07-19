package com.github.thedeathlycow.frostiful.entity.ai.goal;

import com.github.thedeathlycow.frostiful.registry.FLootTables;
import net.minecraft.entity.passive.PolarBearEntity;

public class PolarBearPlayFightGoal extends PlayFightGoal<PolarBearEntity> {

    public PolarBearPlayFightGoal(PolarBearEntity polarBear, float adultChance, float babyChance) {
        super(polarBear, PolarBearEntity.class, adultChance, babyChance, FLootTables.POLAR_BEAR_PLAYFIGHT_GAMEPLAY);
    }

    @Override
    public void stop() {
        this.mob.setWarning(false);
        if (this.target != null) {
            this.target.setWarning(false);
        }
        super.stop();
    }

    @Override
    protected void playFight() {
        this.mob.setWarning(true);
        if (this.target != null) {
            this.target.setWarning(true);
        }
        super.playFight();
    }
}
