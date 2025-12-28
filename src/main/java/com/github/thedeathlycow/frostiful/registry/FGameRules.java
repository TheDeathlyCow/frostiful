package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.util.TextStyles;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;

/**
 * Custom game rules for Lost in the Cold
 */
public class FGameRules {

    /**
     * Mod's dedicated category for game rules
     */
    public static final CustomGameRuleCategory CATEGORY = new CustomGameRuleCategory(
            Frostiful.id("gamerule_category"),
            Component.translatable("gamerule.category." + Frostiful.MODID)
                    .setStyle(TextStyles.GAME_RULE_TITLE)
    );

    /**
     * Whether to apply passive freezing. When false, player freezing will revert
     * to vanilla, except for the increased frost resistance provided by their
     * frost resistance attribute.
     */
    public static final GameRule<Boolean> DO_PASSIVE_FREEZING =
            GameRuleBuilder.forBoolean(true)
                    .category(CATEGORY)
                    .buildAndRegister(Frostiful.id("do_passive_freezing"));

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful game rules");
    }

    private FGameRules() {

    }
}
