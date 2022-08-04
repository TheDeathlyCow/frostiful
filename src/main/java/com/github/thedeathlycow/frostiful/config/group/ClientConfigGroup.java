package com.github.thedeathlycow.frostiful.config.group;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = Frostiful.MODID + ".client")
public class ClientConfigGroup implements ConfigData {

    double frostOverlayStart = 0.5;
    boolean doColdHeartOverlay = true;
}
