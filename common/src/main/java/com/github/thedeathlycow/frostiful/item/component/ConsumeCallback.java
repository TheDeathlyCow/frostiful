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

package com.github.thedeathlycow.frostiful.item.component;

import com.github.thedeathlycow.frostiful.Frostiful;
import dev.yumi.commons.event.Event;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;

@FunctionalInterface
public interface ConsumeCallback {
    Event<Identifier, ConsumeCallback> EVENT = Frostiful.EVENT_MANAGER.create(
            ConsumeCallback.class,
            listeners -> (level, user, stack, consumable) -> {
                for (ConsumeCallback listener : listeners) {
                    listener.onConsume(level, user, stack, consumable);
                }
            }
    );

    void onConsume(final Level level, final LivingEntity user, final ItemStack stack, final Consumable consumable);
}