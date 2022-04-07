package com.github.thedeathlycow.lostinthecold.init;

import com.github.thedeathlycow.lostinthecold.attributes.ModEntityAttributes;
import com.github.thedeathlycow.lostinthecold.config.HypothermiaConfig;
import com.github.thedeathlycow.lostinthecold.config.HypothermiaConfigLoader;
import com.github.thedeathlycow.lostinthecold.items.ModItems;
import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class LostInTheCold implements ModInitializer {

    public static final String MODID = "lost-in-the-cold";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static File getDataFolder() {
        File file = new File("mods/" + MODID);
        if (!file.exists() && !file.mkdirs()) {
            LOGGER.error("Could not create data folder!");
        }
        return file;
    }

    public static HypothermiaConfig getConfig() {
        return configLoader.getConfig();
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Lost in the Cold");
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(configLoader);

        Reflection.initialize(
                ModItems.class,
                ModEntityAttributes.class
        );

        LOGGER.info("Initialized Lost in the Cold");
    }

    private static final HypothermiaConfigLoader configLoader;

    static {
        configLoader = new HypothermiaConfigLoader(getDataFolder());
    }
}
