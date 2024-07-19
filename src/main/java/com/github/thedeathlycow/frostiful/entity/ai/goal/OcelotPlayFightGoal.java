package com.github.thedeathlycow.frostiful.entity.ai.goal;

import com.github.thedeathlycow.frostiful.registry.FLootTables;
import net.minecraft.entity.passive.OcelotEntity;

public class OcelotPlayFightGoal extends PlayFightGoal<OcelotEntity> {

    public OcelotPlayFightGoal(OcelotEntity ocelot, float adultChance, float babyChance) {
        super(ocelot, OcelotEntity.class, adultChance, babyChance, FLootTables.OCELOT_PLAYFIGHT_GAMEPLAY);
    }

}
