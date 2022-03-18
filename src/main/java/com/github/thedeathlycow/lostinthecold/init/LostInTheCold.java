package com.github.thedeathlycow.lostinthecold.init;

import net.fabricmc.api.DedicatedServerModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class LostInTheCold implements DedicatedServerModInitializer {

    public static final String MODID = "lost-in-the-cold";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    private static final Set<InitializeServerListener> onInitializeListeners = new HashSet<>();

    public static void addOnInitializeListener(InitializeServerListener listener) {
        onInitializeListeners.add(listener);
    }

    @Override
    public void onInitializeServer() {
        onInitializeListeners.forEach(InitializeServerListener::onInitialize);
    }
}
