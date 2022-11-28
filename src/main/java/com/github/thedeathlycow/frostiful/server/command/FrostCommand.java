package com.github.thedeathlycow.frostiful.server.command;

import com.github.thedeathlycow.frostiful.entity.FreezableEntity;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class FrostCommand {

    private static final SimpleCommandExceptionType NOT_LIVING_ENTITY = new SimpleCommandExceptionType(Text.translatable("frostiful.commands.frost.exception.not_living_entity"));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {

        var applyFrostResistanceArg =
                argument("apply frost resistance", BoolArgumentType.bool())
                        .executes(context -> {
                            return runAdjust(
                                    context.getSource(),
                                    EntityArgumentType.getEntity(context, "target"),
                                    IntegerArgumentType.getInteger(context, "amount"),
                                    BoolArgumentType.getBool(context, "apply frost resistance"),
                                    false
                            );
                        });

        var getSubCommand = literal("get")
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
                );

        var removeSubCommand = literal("remove")
                .then(
                        argument("target", EntityArgumentType.entity())
                                .then(
                                        argument("amount", IntegerArgumentType.integer(0))
                                                .executes(context -> {
                                                    return runAdjust(
                                                            context.getSource(),
                                                            EntityArgumentType.getEntity(context, "target"),
                                                            -IntegerArgumentType.getInteger(context, "amount"),
                                                            false,
                                                            true
                                                    );
                                                })
                                )
                );

        var addSubCommand = literal("add")
                .then(
                        argument("target", EntityArgumentType.entity())
                                .then(
                                        argument("amount", IntegerArgumentType.integer(0))
                                                .executes(context -> {
                                                    return runAdjust(
                                                            context.getSource(),
                                                            EntityArgumentType.getEntity(context, "target"),
                                                            IntegerArgumentType.getInteger(context, "amount"),
                                                            false,
                                                            false
                                                    );
                                                })
                                                .then(
                                                        applyFrostResistanceArg
                                                )
                                )
                );

        var setSubCommand = literal("set")
                .then(
                        argument("target", EntityArgumentType.entity())
                                .then(
                                        argument("amount", IntegerArgumentType.integer(0))
                                                .executes(context -> {
                                                    return runSet(context.getSource(),
                                                            EntityArgumentType.getEntity(context, "target"),
                                                            IntegerArgumentType.getInteger(context, "amount"));
                                                })
                                )
                );

        dispatcher.register(
                (literal("frost").requires((src) -> src.hasPermissionLevel(2)))
                        .then(getSubCommand)
                        .then(removeSubCommand)
                        .then(addSubCommand)
                        .then(setSubCommand)
        );
    }

    private static int runGetMax(ServerCommandSource source, Entity target) throws CommandSyntaxException {
        if (target instanceof LivingEntity livingEntity) {
            int amount = livingEntity.getMinFreezeDamageTicks();;
            Text msg = Text.translatable("commands.frostiful.frost.get.max.success", target.getDisplayName(), amount);
            source.sendFeedback(msg, true);
            return amount;
        } else {
            throw NOT_LIVING_ENTITY.create();
        }
    }

    private static int runGet(ServerCommandSource source, Entity target) throws CommandSyntaxException {
        if (target instanceof LivingEntity livingEntity) {
            int amount = livingEntity.getFrozenTicks();
            Text msg = Text.translatable("commands.frostiful.frost.get.current.success", target.getDisplayName(), amount);
            source.sendFeedback(msg, true);
            return amount;
        } else {
            throw NOT_LIVING_ENTITY.create();
        }
    }

    private static int runAdjust(ServerCommandSource source, Entity target, int amount, boolean applyFrostResistance, boolean isRemoving) throws CommandSyntaxException {
        if (target instanceof LivingEntity livingEntity) {
            FrostHelper.addLivingFrost(livingEntity, amount, applyFrostResistance);
        } else {
            throw NOT_LIVING_ENTITY.create();
        }

        String successMsgKey = isRemoving ? "commands.frostiful.frost.remove.success" : "commands.frostiful.frost.add.success";
        Text msg = Text.translatable(successMsgKey, MathHelper.abs(amount), target.getDisplayName(), target.getFrozenTicks());
        source.sendFeedback(msg, true);
        return 1;
    }

    private static int runSet(ServerCommandSource source, Entity target, int amount) throws CommandSyntaxException {
        if (target instanceof LivingEntity livingEntity) {
            FrostHelper.setFrost(livingEntity, amount);
        } else {
            throw NOT_LIVING_ENTITY.create();
        }

        Text msg = Text.translatable("commands.frostiful.frost.set.success", target.getDisplayName(), amount);
        source.sendFeedback(msg, true);

        return 1;
    }
}
