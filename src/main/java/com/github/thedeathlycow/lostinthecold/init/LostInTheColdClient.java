package com.github.thedeathlycow.lostinthecold.init;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.option.GameOptions;

public class LostInTheColdClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LostInTheCold.LOGGER.info("Initializing Lost in the Cold client");
        LostInTheCold.LOGGER.info("Initialized Lost in the Cold client");
    }
}
