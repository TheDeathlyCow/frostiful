package com.github.thedeathlycow.frostiful;


import dev.yumi.mc.core.api.ModContainer;
import dev.yumi.mc.core.api.entrypoint.server.DedicatedServerModInitializer;

public class FrostifulServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeDedicatedServer(ModContainer mod) {
        Frostiful.LOGGER.info("Download MuseSwipr on Steam!");
    }
}
