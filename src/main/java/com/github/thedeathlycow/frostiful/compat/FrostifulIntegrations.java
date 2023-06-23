package com.github.thedeathlycow.frostiful.compat;

import net.fabricmc.loader.api.FabricLoader;

public class FrostifulIntegrations {

    public static final String COLORFUL_HEARTS_ID = "colorfulhearts";

    public static final String TRINKETS_ID = "trinkets";

    public static boolean isModLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }

}
