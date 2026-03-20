package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

/**
 * Custom game rules for Frostiful
 */
public class FGameRules {
    /**
     * Mod's dedicated category for game rules
     */
    public static final GameRuleCategory SURVIVAL_CATEGORY = GameRuleCategory.register(Frostiful.id("survival"));

    /**
     * Whether to apply passive freezing. When false, player freezing will revert
     * to vanilla, except for the increased frost resistance provided by their
     * frost resistance attribute.
     */
    public static final GameRule<Boolean> ENABLE_ENVIRONMENT_FREEZING =
            GameRuleBuilder.forBoolean(true)
                    .category(SURVIVAL_CATEGORY)
                    .buildAndRegister(Frostiful.id("enable_environment_freezing"));

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful game rules");
        BuiltInRegistries.GAME_RULE.addAlias(
                Frostiful.id("do_passive_freezing"),
                Frostiful.id("enable_environment_freezing")
        );
    }
    
    private FGameRules() {

    }
}
