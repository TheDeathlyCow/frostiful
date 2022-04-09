package com.github.thedeathlycow.lostinthecold.world;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;

public class ModGameRules {

    public static final GameRules.Key<GameRules.BooleanRule> DO_PASSIVE_FREEZING =
            GameRuleRegistry.register(
                    "doPassiveFreezing",
                    GameRules.Category.MOBS,
                    GameRuleFactory.createBooleanRule(true)
            );

}
