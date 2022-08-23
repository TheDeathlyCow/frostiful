package com.github.thedeathlycow.frostiful.config.group;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = Frostiful.MODID + ".client_config")
public class ClientConfigGroup implements ConfigData {

    float frostOverlayStart = 0.5f;
    boolean doColdHeartOverlay = true;

    public float getFrostOverlayStart() {
        return frostOverlayStart;
    }

    public boolean doColdHeartOverlay() {
        return doColdHeartOverlay;
    }
}
