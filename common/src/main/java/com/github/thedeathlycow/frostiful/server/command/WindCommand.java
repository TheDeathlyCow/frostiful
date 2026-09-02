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

import com.github.thedeathlycow.frostiful.survival.wind.WindSpawnMethod;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.permissions.Permissions;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class WindCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        var blow =
                argument("pos", BlockPosArgument.blockPos())
                        .then(
                                argument("inAir", BoolArgumentType.bool())
                                        .executes(
                                                context -> {
                                                    return run(
                                                            context.getSource().getLevel(),
                                                            BlockPosArgument.getBlockPos(context, "pos"),
                                                            BoolArgumentType.getBool(context, "inAir")
                                                    );
                                                }
                                        )
                        )
                        .executes(
                                context -> {
                                    return run(
                                            context.getSource().getLevel(),
                                            BlockPosArgument.getBlockPos(context, "pos"),
                                            false
                                    );
                                }
                        );


        dispatcher.register(
                literal("blow").requires(src -> src.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER))
                        .then(
                                blow
                        )
        );
    }

    private static int run(ServerLevel world, BlockPos pos, boolean isInAir) {
        WindSpawnMethod.POINT.getStrategy().spawn(
                world, pos, isInAir
        );
        return 0;
    }

    private WindCommand() {

    }
}
