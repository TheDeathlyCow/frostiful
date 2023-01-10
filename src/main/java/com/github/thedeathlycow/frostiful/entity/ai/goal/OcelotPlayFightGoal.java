package com.github.thedeathlycow.frostiful.entity.ai.goal;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.entity.passive.OcelotEntity;
import net.minecraft.util.Identifier;

public class OcelotPlayFightGoal extends PlayFightGoal<OcelotEntity> {

    private static final Identifier PLAYFIGHT_LOOT_TABLE = Frostiful.id("gameplay/ocelot_playfight");


    public OcelotPlayFightGoal(OcelotEntity ocelot, float adultChance, float babyChance) {
        super(ocelot, OcelotEntity.class, adultChance, babyChance, PLAYFIGHT_LOOT_TABLE);
    }
}
