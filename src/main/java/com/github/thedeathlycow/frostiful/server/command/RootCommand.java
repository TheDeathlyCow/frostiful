package com.github.thedeathlycow.frostiful.server.command;

import com.github.thedeathlycow.frostiful.entity.component.FrostWandRootComponent;
import com.github.thedeathlycow.frostiful.registry.FComponents;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import java.util.Collection;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.PermissionLevel;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

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
                FrostWandRootComponent component = FComponents.FROST_WAND_ROOT_COMPONENT.get(livingEntity);
                component.setRootedTicks(duration);
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
