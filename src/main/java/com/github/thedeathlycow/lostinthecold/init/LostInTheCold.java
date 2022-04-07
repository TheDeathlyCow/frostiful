package com.github.thedeathlycow.lostinthecold.init;

import com.github.thedeathlycow.lostinthecold.attributes.ModEntityAttributes;
import com.github.thedeathlycow.lostinthecold.config.HypothermiaConfig;
import com.github.thedeathlycow.lostinthecold.config.HypothermiaConfigLoader;
import com.github.thedeathlycow.lostinthecold.items.ModItems;
import com.google.common.reflect.Reflection;
import net.fabricmc.api.ModInitializer;
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
        return config;
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Lost in the Cold");

        Reflection.initialize(
                ModItems.class,
                ModEntityAttributes.class
        );

        LOGGER.info("Initialized Lost in the Cold");
    }

    private static final HypothermiaConfig config;

    static {
        config = HypothermiaConfigLoader.load();
    }
}
