package com.github.thedeathlycow.lostinthecold.init;

import com.github.thedeathlycow.lostinthecold.block.LostInTheColdCutouts;
import net.fabricmc.api.ClientModInitializer;

public class LostInTheColdClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        LostInTheCold.LOGGER.info("Initializing Lost in the Cold client...");
        LostInTheColdCutouts.registerCutouts();
        LostInTheCold.LOGGER.info("Initialized Lost in the Cold client!");
    }
}
