package com.github.thedeathlycow.lostinthecold.init;

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
        onInitializeListeners.forEach(OnInitializeListener::onInitialize);
    }
}
