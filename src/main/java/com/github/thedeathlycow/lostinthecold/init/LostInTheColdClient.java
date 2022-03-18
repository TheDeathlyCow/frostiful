package com.github.thedeathlycow.lostinthecold.init;

import com.github.thedeathlycow.lostinthecold.items.ModItems;
import net.fabricmc.api.ClientModInitializer;

import java.util.HashSet;
import java.util.Set;

public class LostInTheColdClient implements ClientModInitializer {

    private static final Set<OnInitializeListener> onInitializeListeners = new HashSet<>();

    public static void addOnInitializeListener(OnInitializeListener listener) {
        onInitializeListeners.add(listener);
    }

    @Override
    public void onInitializeClient() {
        LostInTheCold.LOGGER.info("Loading Lost in the Cold client");
        onInitializeListeners.forEach(OnInitializeListener::onInitialize);
        ModItems.registerItems();
    }
}
