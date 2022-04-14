package com.github.thedeathlycow.lostinthecold.init;

import net.fabricmc.api.DedicatedServerModInitializer;

public class LostInTheColdServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        LostInTheCold.LOGGER.info("Initializing Lost in the Cold server...");
        LostInTheCold.LOGGER.info("Initialized Lost in the Cold server!");
    }
}
