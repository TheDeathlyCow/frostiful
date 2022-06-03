package com.github.thedeathlycow.frostiful.server.command;

import com.github.thedeathlycow.frostiful.util.survival.PassiveFreezingHelper;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;


public class GetTemperatureCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        var getTemperature = literal("gettemperature")
                .then(
                        argument("target", EntityArgumentType.entity())
                                .executes(
                                        ctx -> {
                                            return execute(ctx.getSource(), EntityArgumentType.getEntity(ctx, "target"));
                                        }
                                )
                )
                .executes(
                        ctx -> {
                            return execute(ctx.getSource(), ctx.getSource().getEntityOrThrow());
                        }
                );

        dispatcher.register(
                (literal("frostiful").requires(src -> src.hasPermissionLevel(2)))
                        .then(getTemperature)
        );
    }

    private static int execute(ServerCommandSource source, Entity target) {
        World world = target.getEntityWorld();
        Biome biome = world.getBiome(target.getBlockPos()).value();
        float temperature = biome.getTemperature();
        int freezeRate = PassiveFreezingHelper.getPerTickFreezing(temperature);
        String msg = String.format("This biome's temperature is %.4f with a per-tick freeze rate of %d", temperature, PassiveFreezingHelper.getPerTickFreezing(temperature));
        source.sendFeedback(new LiteralText(msg), false);
        return freezeRate;
    }

}
