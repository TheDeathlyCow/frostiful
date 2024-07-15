package com.github.thedeathlycow.frostiful.entity.ai.goal;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.util.Identifier;

public class PolarBearPlayFightGoal extends PlayFightGoal<PolarBearEntity> {

    private static final Identifier PLAYFIGHT_LOOT_TABLE = Frostiful.id("gameplay/polar_bear_playfight");

    public PolarBearPlayFightGoal(PolarBearEntity polarBear, float adultChance, float babyChance) {
        super(polarBear, PolarBearEntity.class, adultChance, babyChance, PLAYFIGHT_LOOT_TABLE);
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
