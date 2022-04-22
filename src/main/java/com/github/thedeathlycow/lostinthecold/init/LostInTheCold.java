package com.github.thedeathlycow.lostinthecold.init;

import com.github.thedeathlycow.datapack.config.config.Config;
import com.github.thedeathlycow.lostinthecold.attributes.LostInTheColdEntityAttributes;
import com.github.thedeathlycow.lostinthecold.block.LostInTheColdBlocks;
import com.github.thedeathlycow.lostinthecold.config.ConfigKeys;
import com.github.thedeathlycow.lostinthecold.items.LostInTheColdItems;
import com.github.thedeathlycow.lostinthecold.server.command.FreezeCommand;
import com.github.thedeathlycow.lostinthecold.tag.biome.LostInTheColdBiomeTemperatureTags;
import com.github.thedeathlycow.lostinthecold.world.LostInTheColdGameRules;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LostInTheCold implements ModInitializer {

    public static final String MODID = "lost-in-the-cold";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    private static final Config config = ConfigKeys.createConfig();

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

        LostInTheColdEntityAttributes.registerAttributes();
        LostInTheColdBlocks.registerBlocks();
        LostInTheColdItems.registerItems();
        LostInTheColdGameRules.registerGamerules();

        LOGGER.info("Initialized Lost in the Cold!");
    }

}
