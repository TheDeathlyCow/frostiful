package com.github.thedeathlycow.lostinthecold.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;

import java.util.Collection;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class FreezeCommand {


    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                (literal("freeze").requires((src) -> src.hasPermissionLevel(2)))
                        .then(
                                literal("get")
                                        .then(
                                                argument("target", EntityArgumentType.entity())
                                                        .executes(context -> {
                                                            return runGet(context.getSource(),
                                                                    EntityArgumentType.getEntity(context, "target"));
                                                        })
                                                        .then(literal("max")
                                                                .executes(context -> {
                                                                    return runGetMax(context.getSource(),
                                                                            EntityArgumentType.getEntity(context, "target"));
                                                                }))
                                        )
                        )
                        .then(
                                literal("remove")
                                        .then(
                                                argument("targets", EntityArgumentType.entities())
                                                        .then(
                                                                argument("amount", IntegerArgumentType.integer(0))
                                                                        .executes(context -> {
                                                                            return runAdjust(context.getSource(),
                                                                                    EntityArgumentType.getEntities(context, "targets"),
                                                                                    -IntegerArgumentType.getInteger(context, "amount"));
                                                                        })
                                                        )
                                        )
                        )
                        .then(
                                literal("add")
                                        .then(
                                                argument("targets", EntityArgumentType.entities())
                                                        .then(
                                                                argument("amount", IntegerArgumentType.integer(0))
                                                                        .executes(context -> {
                                                                            return runAdjust(context.getSource(),
                                                                                    EntityArgumentType.getEntities(context, "targets"),
                                                                                    IntegerArgumentType.getInteger(context, "amount"));
                                                                        })
                                                        )
                                        )
                        )
                        .then(
                                literal("set")
                                        .then(
                                                argument("targets", EntityArgumentType.entities())
                                                        .then(
                                                                argument("amount", IntegerArgumentType.integer(0))
                                                                        .executes(context -> {
                                                                            return runSet(context.getSource(),
                                                                                    EntityArgumentType.getEntities(context, "targets"),
                                                                                    IntegerArgumentType.getInteger(context, "amount"));
                                                                        })
                                                        )
                                        )
                        )
        );
    }

    private static int runGetMax(ServerCommandSource source, Entity target) {
        int amount = target.getMinFreezeDamageTicks();
        Text msg = new TranslatableText("commands.lost-in-the-cold.get.max.success", target.getDisplayName(), amount);
        source.sendFeedback(msg, true);
        return amount;
    }

    private static int runGet(ServerCommandSource source, Entity target) {
        int amount = target.getFrozenTicks();
        Text msg = new TranslatableText("commands.lost-in-the-cold.get.current.success", target.getDisplayName(), amount);
        source.sendFeedback(msg, true);
        return amount;
    }

    private static int runAdjust(ServerCommandSource source, Collection<? extends Entity> targets, int amount) throws CommandSyntaxException {
        for (Entity entity : targets) {
            int frozenTicks = entity.getFrozenTicks();
            int freezing = Math.min(frozenTicks + amount, entity.getMinFreezeDamageTicks());
            freezing = Math.max(0, freezing);
            entity.setFrozenTicks(freezing);
        }

        String successMsgKey = amount < 0 ? "commands.lost-in-the-cold.freeze.remove.success." : "commands.lost-in-the-cold.freeze.add.success.";
        Text msg;
        if (targets.size() == 1) {
            Entity target = targets.iterator().next();
            msg = new TranslatableText(successMsgKey + "single", MathHelper.abs(amount), target.getDisplayName(), target.getFrozenTicks());
        } else {
            msg = new TranslatableText(successMsgKey + "multiple", MathHelper.abs(amount), targets.size());
        }
        source.sendFeedback(msg, true);

        return targets.size();
    }

    private static int runSet(ServerCommandSource source, Collection<? extends Entity> targets, int amount) throws CommandSyntaxException {
        for (Entity entity : targets) {
            int freezing = Math.min(amount, entity.getMinFreezeDamageTicks());
            freezing = Math.max(0, freezing);
            entity.setFrozenTicks(freezing);
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
