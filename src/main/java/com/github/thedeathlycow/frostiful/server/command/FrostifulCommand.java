package com.github.thedeathlycow.frostiful.server.command;

import com.github.thedeathlycow.frostiful.util.survival.PassiveFreezingHelper;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class FrostifulCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        var getTemperature = literal("gettemperature")
                .then(
                        argument("pos", BlockPosArgumentType.blockPos())
                                .executes(
                                        ctx -> {
                                            return execute(
                                                    ctx.getSource(),
                                                    BlockPosArgumentType.getBlockPos(ctx, "pos"),
                                                    ctx.getSource().getWorld()
                                            );
                                        }
                                )
                )
                .executes(
                        ctx -> {
                            return execute(
                                    ctx.getSource(),
                                    new BlockPos(ctx.getSource().getPosition()),
                                    ctx.getSource().getWorld()
                            );
                        }
                );

        dispatcher.register(
                (literal("frostiful").requires(src -> src.hasPermissionLevel(2)))
                        .then(getTemperature)
        );
    }

    private static int execute(ServerCommandSource source, BlockPos pos, World world) {
        Biome biome = world.getBiome(pos).value();
        float temperature = biome.getTemperature();
        int freezeRate = PassiveFreezingHelper.getPerTickFreezing(temperature);
        String msg = String.format("This biome's temperature is %.4f with a per-tick freeze rate of %d", temperature, freezeRate);
        source.sendFeedback(new LiteralText(msg), false);
        return freezeRate;
    }

}
