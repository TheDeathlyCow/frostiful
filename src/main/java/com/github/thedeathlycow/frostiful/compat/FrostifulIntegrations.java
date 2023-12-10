package com.github.thedeathlycow.frostiful.compat;

import io.github.lucaargolo.seasons.FabricSeasons;
import io.github.lucaargolo.seasons.utils.Season;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.World;

public class FrostifulIntegrations {

    private FrostifulIntegrations() {}

    public static final String COLORFUL_HEARTS_ID = "colorfulhearts";

    public static final String OVERFLOWING_BARS_ID = "overflowingbars";

    public static final String TRINKETS_ID = "trinkets";

    public static final String FABRIC_SEASONS_ID = "seasons";

    public static boolean isHeartsRenderOverridden() {
        return isModLoaded(COLORFUL_HEARTS_ID) || isModLoaded(OVERFLOWING_BARS_ID);
    }

    public static boolean isModLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }

    public static boolean isWinter(World world) {
        if (isModLoaded(FABRIC_SEASONS_ID)) {
            return Season.WINTER == FabricSeasons.getCurrentSeason(world);
        } else {
            return false;
        }
    }
}
