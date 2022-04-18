package com.github.thedeathlycow.lostinthecold.init;

import com.github.thedeathlycow.lostinthecold.attributes.LostInTheColdEntityAttributes;
import com.github.thedeathlycow.lostinthecold.config.Config;
import com.github.thedeathlycow.lostinthecold.config.ConfigLoader;
import com.github.thedeathlycow.lostinthecold.items.LostInTheColdItems;
import com.github.thedeathlycow.lostinthecold.server.command.FreezeCommand;
import com.github.thedeathlycow.lostinthecold.world.LostInTheColdGameRules;
import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LostInTheCold implements ModInitializer {

    public static final String MODID = "lost-in-the-cold";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    private static final ConfigLoader configLoader = new ConfigLoader();
    private static final Config config = configLoader.createEmptyConfig(new Identifier(MODID, "config"));

    public static Config getConfig() {
        return config;
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Lost in the Cold...");

        CommandRegistrationCallback.EVENT.register(
                ((dispatcher, dedicated) -> {
                    FreezeCommand.register(dispatcher);
                })
        );

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new ConfigLoader());

        Reflection.initialize(
                LostInTheColdItems.class,
                LostInTheColdEntityAttributes.class,
                LostInTheColdGameRules.class
        );

        LOGGER.info("Initialized Lost in the Cold!");
    }

}
