package com.github.thedeathlycow.frostiful.client.config;

import com.github.thedeathlycow.frostiful.client.config.section.DisplaySettings;

public final class FrostifulClientConfig {
    public static DisplaySettings displaySettings() {
        return DisplaySettings.HANDLER.instance();
    }

    public static void initialize() {
        DisplaySettings.HANDLER.load();
        DisplaySettings.HANDLER.save();
    }

    private FrostifulClientConfig() {

    }
}