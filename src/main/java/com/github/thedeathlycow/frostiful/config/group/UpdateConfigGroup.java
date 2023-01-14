package com.github.thedeathlycow.frostiful.config.group;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = Frostiful.MODID + ".update_config")
public class UpdateConfigGroup implements ConfigData {

    @ConfigEntry.Gui.Excluded
    public int currentConfigVersion = Frostiful.CONFIG_VERSION;

    @ConfigEntry.Gui.Tooltip(count = 3)
    boolean enableConfigUpdates = true;

    public boolean isConfigUpdatesEnabled() {
        return enableConfigUpdates;
    }
}
