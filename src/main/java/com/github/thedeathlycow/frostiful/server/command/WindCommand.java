package com.github.thedeathlycow.frostiful.server.command;

import com.github.thedeathlycow.frostiful.survival.wind.WindSpawnStrategies;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class WindCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

        var blow =
                argument("pos", BlockPosArgumentType.blockPos())
                        .then(
                                argument("inAir", BoolArgumentType.bool())
                                        .executes(
                                                context -> {
                                                    return run(
                                                            context.getSource().getWorld(),
                                                            BlockPosArgumentType.getBlockPos(context, "pos"),
                                                            BoolArgumentType.getBool(context, "inAir")
                                                    );
                                                }
                                        )
                        )
                        .executes(
                                context -> {
                                    return run(
                                            context.getSource().getWorld(),
                                            BlockPosArgumentType.getBlockPos(context, "pos"),
                                            false
                                    );
                                }
                        );


        dispatcher.register(
                literal("blow").requires(src -> src.hasPermissionLevel(2))
                        .then(
                                blow
                        )
        );
    }

    private static int run(ServerWorld world, BlockPos pos, boolean isInAir) {
        WindSpawnStrategies.POINT.getStrategy().spawn(
                world, pos, isInAir
        );
        return 0;
    }

    private WindCommand() {

    }
}
