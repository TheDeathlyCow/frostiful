package com.github.thedeathlycow.lostinthecold.init;

import com.github.thedeathlycow.lostinthecold.items.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class LostInTheCold implements DedicatedServerModInitializer {

    public static final String MODID = "lost-in-the-cold";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    private static final List<OnInitializeListener> onInitializeListeners = new LinkedList<>();

    public static void addOnInitializeListener(OnInitializeListener listener) {
        onInitializeListeners.add(listener);
    }

    @Override
    public void onInitializeServer() {
        LOGGER.info("Loading Lost in the Cold dedicated server");
        onInitializeListeners.forEach(OnInitializeListener::onInitialize);
        ModItems.registerItems();
    }
}
