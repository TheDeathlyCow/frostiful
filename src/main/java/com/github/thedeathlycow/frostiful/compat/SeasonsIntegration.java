package com.github.thedeathlycow.frostiful.compat;

import io.github.lucaargolo.seasons.FabricSeasons;
import io.github.lucaargolo.seasons.utils.Season;
import net.minecraft.world.World;

public class SeasonsIntegration {

    public static boolean isWinter(World world) {
        if (FrostifulIntegrations.isModLoaded(FrostifulIntegrations.FABRIC_SEASONS_ID)) {
            return Season.WINTER == FabricSeasons.getCurrentSeason(world);
        } else {
            return false;
        }
    }

    private SeasonsIntegration() {

    }

}
