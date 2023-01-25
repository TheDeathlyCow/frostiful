package com.github.thedeathlycow.frostiful.compat;

import net.fabricmc.loader.api.FabricLoader;

public class FrostifulIntegrations {

    public static final String HEALTH_OVERLAY_ID = "healthoverlay";

    public static final String TRINKETS_ID = "trinkets";

    public static boolean isModLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }

}
