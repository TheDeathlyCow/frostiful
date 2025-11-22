package com.github.thedeathlycow.frostiful.entity.ai.goal;

import com.github.thedeathlycow.frostiful.registry.FLootTables;
import net.minecraft.world.entity.animal.Ocelot;

public class OcelotPlayFightGoal extends PlayFightGoal<Ocelot> {

    public OcelotPlayFightGoal(Ocelot ocelot, float adultChance, float babyChance) {
        super(ocelot, Ocelot.class, adultChance, babyChance, FLootTables.OCELOT_PLAYFIGHT_GAMEPLAY);
    }

}
