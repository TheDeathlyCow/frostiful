package com.github.thedeathlycow.frostiful;

import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;

public class FrostifulTest {

    public static void bootstrap() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
    }

    private FrostifulTest() {

    }
}
