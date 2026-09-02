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

package com.github.thedeathlycow.frostiful.mixins.datafix;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.GameRuleRegistryFix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRuleRegistryFix.class)
public class GameRuleRegistryFixMixin {
    @Invoker("convertBoolean")
    private static Dynamic<?> frostiful$invokeConvertBoolean(Dynamic<?> dynamic) {
        throw new IllegalStateException("Method not invoked");
    }

    @ModifyReturnValue(
            method = "lambda$makeRule$2",
            at = @At("TAIL")
    )
    private static Dynamic<?> fixDoPassiveFreezingGameRule(Dynamic<?> original) {
        return original.renameAndFixField(
                "frostiful.doPassiveFreezing",
                "frostiful:do_passive_freezing",
                GameRuleRegistryFixMixin::frostiful$invokeConvertBoolean
        );
    }
}