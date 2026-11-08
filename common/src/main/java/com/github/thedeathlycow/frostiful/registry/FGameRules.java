/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;

public class FGameRules {
    public static final GameRuleCategory SURVIVAL_CATEGORY = GameRuleCategory.register(Frostiful.id("survival"));

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
