package com.github.thedeathlycow.lostinthecold.server.command;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.Collection;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class FreezeCommand {


    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                (literal("freeze").requires((src) -> src.hasPermissionLevel(2)))
                        .then(
                                argument("targets", EntityArgumentType.entities())
                                        .then(
                                                literal("remove")
                                                        .then(
                                                                argument("amount", IntegerArgumentType.integer())
                                                                        .executes(context -> {
                                                                            return runAdd(context.getSource(),
                                                                                    EntityArgumentType.getEntities(context, "targets"),
                                                                                    -IntegerArgumentType.getInteger(context, "amount"));
                                                                        })
                                                        )
                                        )
                                        .then(
                                                literal("set")
                                                        .then(
                                                                argument("amount", IntegerArgumentType.integer())
                                                                        .executes(context -> {
                                                                            return runSet(context.getSource(),
                                                                                    EntityArgumentType.getEntities(context, "targets"),
                                                                                    IntegerArgumentType.getInteger(context, "amount"));
                                                                        })
                                                        ))
                                        .then(
                                                literal("add")
                                                        .then(
                                                                argument("amount", IntegerArgumentType.integer())
                                                                        .executes(context -> {
                                                                            return runAdd(context.getSource(),
                                                                                    EntityArgumentType.getEntities(context, "targets"),
                                                                                    IntegerArgumentType.getInteger(context, "amount"));
                                                                        })
                                                        ))
                        ));
    }

    private static int runAdd(ServerCommandSource source, Collection<? extends Entity> targets, int amount) throws CommandSyntaxException {
        LostInTheCold.LOGGER.trace("Run add");
        for (Entity entity : targets) {
            int frozenTicks = entity.getFrozenTicks();
            entity.setFrozenTicks(frozenTicks + amount);
        }

        Text msg;
        if (targets.size() == 1) {
            Entity target = targets.iterator().next();
            msg = new TranslatableText("commands.lost-in-the-cold.freeze.add.success.single", amount, target.getDisplayName(), target.getFrozenTicks());
        } else {
            msg = new TranslatableText("commands.lost-in-the-cold.freeze.add.success.multiple", amount, targets.size());
        }
        source.sendFeedback(msg, true);

        return targets.size();
    }

    private static int runSet(ServerCommandSource source, Collection<? extends Entity> targets, int amount) throws CommandSyntaxException {
        LostInTheCold.LOGGER.trace("Run set");
        for (Entity entity : targets) {
            entity.setFrozenTicks(amount);
        }

        Text msg;
        if (targets.size() == 1) {
            Entity target = targets.iterator().next();
            msg = new TranslatableText("commands.lost-in-the-cold.freeze.set.success.single", target.getDisplayName(), amount);
        } else {
            msg = new TranslatableText("commands.lost-in-the-cold.freeze.set.success.multiple", targets.size(), amount);
        }
        source.sendFeedback(msg, true);

        return targets.size();
    }
}
