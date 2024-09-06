package com.github.thedeathlycow.frostiful;

import net.minecraft.Bootstrap;
import net.minecraft.SharedConstants;

public class FrostifulTest {

    public static void bootstrap() {
        SharedConstants.createGameVersion();
        Bootstrap.initialize();
    }

    private FrostifulTest() {

    }
}
