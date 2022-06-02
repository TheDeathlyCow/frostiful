package com.github.thedeathlycow.frostiful.entity.ai.goal;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.Identifier;

public class WolfPlayfightGoal extends PlayFightGoal {

    private static final Identifier PLAYFIGHT_LOOT_TABLE = new Identifier(Frostiful.MODID, "gameplay/wolf_playfight");

    public WolfPlayfightGoal(WolfEntity mob, float adultChance, float babyChance) {
        super(mob, adultChance, babyChance, PLAYFIGHT_LOOT_TABLE);
    }

    @Override
    public boolean canStart() {
        boolean foundTarget = super.canStart();

        if (foundTarget) {
            assert this.target != null;
            WolfEntity wolfMob = ((WolfEntity) this.mob);
            WolfEntity targetWolf = ((WolfEntity) this.target);
            return !wolfMob.isTamed() && !targetWolf.isTamed();
        }

        return false;
    }
}
