package com.github.thedeathlycow.frostiful.client.config;

import com.github.thedeathlycow.frostiful.client.config.section.AccessibilitySettings;
import com.github.thedeathlycow.frostiful.client.config.section.DisplaySettings;

public final class FrostifulClientConfig {
    public static AccessibilitySettings accessibilitySettings() {
        return AccessibilitySettings.HANDLER.instance();
    }

    public static DisplaySettings displaySettings() {
        return DisplaySettings.HANDLER.instance();
    }

    public static void initialize() {
        AccessibilitySettings.HANDLER.load();
        AccessibilitySettings.HANDLER.save();

        DisplaySettings.HANDLER.load();
        DisplaySettings.HANDLER.save();
    }

    private FrostifulClientConfig() {

    }
}