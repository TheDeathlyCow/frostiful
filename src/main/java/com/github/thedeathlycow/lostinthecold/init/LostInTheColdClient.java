package com.github.thedeathlycow.lostinthecold.init;

import com.github.thedeathlycow.lostinthecold.attributes.ModEntityAttributes;
import net.fabricmc.api.ClientModInitializer;

import java.util.HashSet;
import java.util.Set;

public class LostInTheColdClient implements ClientModInitializer {

    private static final Set<InitializeServerListener> onInitializeListeners = new HashSet<>();

    public static void addOnInitializeListener(InitializeServerListener listener) {
        onInitializeListeners.add(listener);
    }

    @Override
    public void onInitializeClient() {
        onInitializeListeners.forEach(InitializeServerListener::onInitialize);
    }
}
