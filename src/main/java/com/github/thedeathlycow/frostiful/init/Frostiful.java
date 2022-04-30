package com.github.thedeathlycow.frostiful.init;

import com.github.thedeathlycow.datapack.config.config.Config;
import com.github.thedeathlycow.frostiful.attributes.FrostifulEntityAttributes;
import com.github.thedeathlycow.frostiful.block.FrostifulBlocks;
import com.github.thedeathlycow.frostiful.config.ConfigKeys;
import com.github.thedeathlycow.frostiful.items.FrostifulItems;
import com.github.thedeathlycow.frostiful.server.command.FreezeCommand;
import com.github.thedeathlycow.frostiful.world.FrostifulGameRules;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Frostiful implements ModInitializer {

    public static final String MODID = "frostiful";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    private static final Config config = ConfigKeys.createConfig();

    public static Config getConfig() {
        return config;
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Frostiful...");

        CommandRegistrationCallback.EVENT.register(
                ((dispatcher, dedicated) -> {
                    FreezeCommand.register(dispatcher);
                })
        );

        FrostifulEntityAttributes.registerAttributes();
        FrostifulBlocks.registerBlocks();
        FrostifulItems.registerItems();
        FrostifulGameRules.registerGamerules();

        LOGGER.info("Initialized Frostiful!");
    }

}
