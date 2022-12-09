package com.github.thedeathlycow.frostiful.util.survival.effects;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.server.function.CommandFunctionManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

public class FunctionSurvivalEffectType extends SurvivalEffectType<FunctionSurvivalEffectType.Config> {


    @Nullable
    private MinecraftServer server;

    public FunctionSurvivalEffectType() {
        this.server = null;

        // set/remove server based on lifetime events
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            this.server = server;
        });
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            this.server = null;
        });
    }

    @Override
    public void apply(LivingEntity victim, Config config) {

        if (this.server == null) {
            return;
        }

        ServerCommandSource src = victim.getCommandSource();

        CommandFunctionManager manager = this.server.getCommandFunctionManager();

        for (CommandFunction function : config.functions) {
            manager.execute(function, src);
        }
    }

    @Override
    public boolean shouldApply(LivingEntity victim, Config config) {
        return victim.getFreezingScale() >= config.threshold;
    }

    @Override
    public Config configFromJson(JsonElement json) {

        if (this.server == null) {
            return Config.DEFAULT;
        }

        return Config.fromJson(this.server.getCommandFunctionManager(), json);
    }

    public record Config(
            float threshold,
            Collection<CommandFunction> functions
    ) {

        public static final Config DEFAULT = new Config(Float.POSITIVE_INFINITY, Collections.emptyList());

        public static Config fromJson(CommandFunctionManager manager, JsonElement jsonElement) {

            JsonObject json = jsonElement.getAsJsonObject();
            Collection<CommandFunction> functions = parseFunctions(manager, json.get("name").getAsString());

            float threshold = json.get("threshold").getAsFloat();

            return new Config(threshold, functions);
        }

        private static Collection<CommandFunction> parseFunctions(CommandFunctionManager manager, String name) {

            if (name.startsWith("#")) {
                Identifier id = new Identifier(name.substring(1));
                Collection<CommandFunction> functions = manager.getTag(id);

                if (functions == null) {
                    throw new JsonParseException("Unknown function tag: " + id);
                }

                return functions;
            } else {
                Identifier id = new Identifier(name);
                return Collections.singleton(
                        manager.getFunction(id)
                                .orElseThrow(
                                        () -> new JsonParseException("Unknown function: " + id)
                                )
                );
            }
        }

    }

}
