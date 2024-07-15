package com.github.thedeathlycow.frostiful.entity.ai.goal;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;

public class WolfPlayfightGoal extends PlayFightGoal<WolfEntity> {

    private static final Identifier PLAYFIGHT_LOOT_TABLE = Frostiful.id("gameplay/wolf_playfight");

    public WolfPlayfightGoal(WolfEntity wolf, float adultChance, float babyChance) {
        super(wolf, WolfEntity.class, adultChance, babyChance, PLAYFIGHT_LOOT_TABLE);
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
