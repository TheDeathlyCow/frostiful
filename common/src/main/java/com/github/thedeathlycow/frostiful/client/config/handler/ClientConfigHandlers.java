package com.github.thedeathlycow.frostiful.client.config.handler;

import com.github.thedeathlycow.frostiful.client.config.section.AccessibilitySettings;
import com.github.thedeathlycow.frostiful.client.config.section.DisplaySettings;
import com.github.thedeathlycow.frostiful.config.handler.ConfigHandlers;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;

public final class ClientConfigHandlers {
    public static final ConfigClassHandler<AccessibilitySettings> ACCESSIBILITY = ConfigHandlers.createHandler(
            AccessibilitySettings.class,
            "client/accessibility",
            ClientConfigPaths.ACCESSIBILITY
    );

    public static final ConfigClassHandler<DisplaySettings> DISPLAY = ConfigHandlers.createHandler(
            DisplaySettings.class,
            "client/display",
            ClientConfigPaths.DISPLAY
    );

    private ClientConfigHandlers() {

    }
}