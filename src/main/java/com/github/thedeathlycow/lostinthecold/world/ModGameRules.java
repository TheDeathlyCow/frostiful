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
public class ModGameRules {

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
     * The base amount of damage to each whenever the freeze damage ticks.
     */
    public static final GameRules.Key<GameRules.IntRule> FREEZE_DAMAGE_AMOUNT =
            GameRuleRegistry.register(
                    "freezeDamageAmount",
                    CATEGORY,
                    GameRuleFactory.createIntRule(1, 0)
            );

    /**
     * The extra damage multiplier that is applied to entities belonging
     * to the tag #minecraft:freeze_hurts_extra_types.
     */
    public static final GameRules.Key<GameRules.IntRule> FREEZE_EXTRA_DAMAGE_AMOUNT =
            GameRuleRegistry.register(
                    "freezeExtraDamageAmount",
                    CATEGORY,
                    GameRuleFactory.createIntRule(5, 0)
            );

}
