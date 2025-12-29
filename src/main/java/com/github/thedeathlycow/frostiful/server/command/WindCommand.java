package com.github.thedeathlycow.frostiful.server.command;

import com.github.thedeathlycow.frostiful.survival.wind.WindSpawnStrategies;
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
        WindSpawnStrategies.POINT.getStrategy().spawn(
                world, pos, isInAir
        );
        return 0;
    }

    private WindCommand() {

    }
}
