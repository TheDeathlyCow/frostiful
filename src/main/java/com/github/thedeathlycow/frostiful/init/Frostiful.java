package com.github.thedeathlycow.frostiful.init;

import com.github.thedeathlycow.frostiful.attributes.FrostifulEntityAttributes;
import com.github.thedeathlycow.frostiful.block.FrostifulBlocks;
import com.github.thedeathlycow.frostiful.config.GlobalConfig;
import com.github.thedeathlycow.frostiful.config.ConfigReloader;
import com.github.thedeathlycow.frostiful.items.FrostifulItems;
import com.github.thedeathlycow.frostiful.server.command.FreezeCommand;
import com.github.thedeathlycow.frostiful.world.FrostifulGameRules;
import com.github.thedeathlycow.simple.config.Config;
import com.github.thedeathlycow.simple.config.reload.Reloadable;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Frostiful implements ModInitializer {

    public static final String MODID = "frostiful";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    private static final Config CONFIG = GlobalConfig.createConfig();

    public static Config getConfig() {
        return CONFIG;
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Frostiful...");

        ConfigReloader reloader = new ConfigReloader();
        reloader.addListener(new Reloadable(CONFIG));
        ResourceManagerHelper.get(ResourceType.SERVER_DATA)
                .registerReloadListener(reloader);

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
