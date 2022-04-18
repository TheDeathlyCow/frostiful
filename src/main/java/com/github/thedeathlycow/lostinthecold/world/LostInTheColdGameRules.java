package com.github.thedeathlycow.lostinthecold.world;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.github.thedeathlycow.lostinthecold.util.TextStyles;
import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;

/**
 * Custom game rules for Lost in the Cold
 */
public class LostInTheColdGameRules {

    /**
     * Mod's dedicated category for game rules
     */
    public static CustomGameRuleCategory CATEGORY = new CustomGameRuleCategory(
            new Identifier(LostInTheCold.MODID, "gamerule_category"),
            new TranslatableText("gamerule.category.lost-in-the-cold")
                    .setStyle(TextStyles.GAME_RULE_TITLE)
    );

    /**
     * Whether to apply passive freezing. When false, player freezing will revert
     * to vanilla, except for the increased frost resistance provided by their
     * frost resistance attribute.
     */
    public static final GameRules.Key<GameRules.BooleanRule> DO_PASSIVE_FREEZING =
            GameRuleRegistry.register(
                    "doPassiveFreezing",
                    CATEGORY,
                    GameRuleFactory.createBooleanRule(true)
            );

    /**
     * Determines the maximum number of layers that snow can accumulate. Default is 8.
     * Set to 1.
     */
    public static final GameRules.Key<GameRules.IntRule> MAX_SNOW_ACCUMULATION =
            GameRuleRegistry.register(
                    "maxSnowAccumulation",
                    CATEGORY,
                    GameRuleFactory.createIntRule(8, 0, 8)
            );
}
