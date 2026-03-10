package com.github.thedeathlycow.frostiful.compat;

import dev.yumi.mc.core.api.YumiMods;

public class FrostifulIntegrations {

    private FrostifulIntegrations() {}

    public static final String COLORFUL_HEARTS_ID = "colorfulhearts";

    public static final String OVERFLOWING_BARS_ID = "overflowingbars";

    public static final String ACCESSORIES_ID = "accessories";

    public static final String FABRIC_SEASONS_ID = "seasons";

    public static final String SCORCHFUL_ID = "scorchful";

    public static boolean isHeartsRenderOverridden() {
        return isModLoaded(COLORFUL_HEARTS_ID) || isModLoaded(OVERFLOWING_BARS_ID);
    }

    public static boolean isAccessoriesLoaded() {
        return isModLoaded(ACCESSORIES_ID);
    }

    public static boolean isModLoaded(String id) {
        return YumiMods.get().isModLoaded(id);
    }
}
