package com.github.thedeathlycow.lostinthecold.server.command;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.types.Func;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.ScoreboardObjectiveArgumentType;
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
                                argument("targets", EntityArgumentType.entities())
                                        .then(
                                                literal("remove")
                                                        .then(
                                                                argument("amount", IntegerArgumentType.integer(0))
                                                                        .executes(context -> {
                                                                            return runAdjust(context.getSource(),
                                                                                    EntityArgumentType.getEntities(context, "targets"),
                                                                                    -IntegerArgumentType.getInteger(context, "amount"));
                                                                        })
                                                        )
                                        )
                                        .then(
                                                literal("add")
                                                        .then(
                                                                argument("amount", IntegerArgumentType.integer(0))
                                                                        .executes(context -> {
                                                                            return runAdjust(context.getSource(),
                                                                                    EntityArgumentType.getEntities(context, "targets"),
                                                                                    IntegerArgumentType.getInteger(context, "amount"));
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
                                                        )
                                        )
                        ));
    }

    private static int runAdjust(ServerCommandSource source, Collection<? extends Entity> targets, int amount) throws CommandSyntaxException {
        for (Entity entity : targets) {
            int frozenTicks = entity.getFrozenTicks();
            int freezing = Math.max(frozenTicks + amount, entity.getMinFreezeDamageTicks());
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
            int freezing = Math.max(amount, entity.getMinFreezeDamageTicks());
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
