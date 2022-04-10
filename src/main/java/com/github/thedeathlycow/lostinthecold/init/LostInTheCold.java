package com.github.thedeathlycow.lostinthecold.init;

import com.github.thedeathlycow.lostinthecold.attributes.ModEntityAttributes;
import com.github.thedeathlycow.lostinthecold.config.Config;
import com.github.thedeathlycow.lostinthecold.config.ConfigKeys;
import com.github.thedeathlycow.lostinthecold.config.ConfigLoader;
import com.github.thedeathlycow.lostinthecold.items.ModItems;
import com.github.thedeathlycow.lostinthecold.server.command.FreezeCommand;
import com.github.thedeathlycow.lostinthecold.world.ModGameRules;
import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;

public class LostInTheCold implements ModInitializer {

    public static final String MODID = "lost-in-the-cold";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static Config getConfig() {
        return config;
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Lost in the Cold");

        CommandRegistrationCallback.EVENT.register(
                ((dispatcher, dedicated) -> {
                    FreezeCommand.register(dispatcher);
                })
        );

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new ConfigLoader());

        Reflection.initialize(
                ModItems.class,
                ModEntityAttributes.class,
                ModGameRules.class
        );

        LOGGER.info("Initialized Lost in the Cold");
    }

    private static final Config config = ConfigKeys.constructDefaultConfig();

}
