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

package com.github.thedeathlycow.frostiful.server.command;

import com.github.thedeathlycow.frostiful.registry.FDataAttachments;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collection;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class RootCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        var rootTarget =
                argument("targets", EntityArgument.entities())
                        .then(
                                argument("duration", IntegerArgumentType.integer(0))
                                        .executes(
                                                (context) -> {
                                                    return runRoot(context.getSource(),
                                                            EntityArgument.getEntities(context, "targets"),
                                                            IntegerArgumentType.getInteger(context, "duration"));
                                                }
                                        )
                        );


        dispatcher.register(
                literal("root").requires(
                                src -> src.permissions()
                                        .hasPermission(Permissions.COMMANDS_GAMEMASTER)
                        )
                        .then(
                                rootTarget
                        )
        );
    }

    private static int runRoot(CommandSourceStack source, Collection<? extends Entity> targets, int duration) throws CommandSyntaxException {

        int sum = 0;
        for (Entity entity : targets) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.setAttached(FDataAttachments.FROST_WAND_ROOT_TICKS, duration);
                sum += duration;
            }
        }

        String key = "commands.frostiful.root.set.success." + (targets.size() == 1 ? "single" : "multiple");
        Component msg;
        if (targets.size() == 1) {
            msg = Component.translatable(key, targets.iterator().next().getDisplayName(), duration);
        } else {
            msg = Component.translatable(key, targets.size(), duration);
        }

        source.sendSuccess(() -> msg, true);

        return sum;
    }
}
