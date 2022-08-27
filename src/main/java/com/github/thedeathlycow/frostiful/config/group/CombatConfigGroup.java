package com.github.thedeathlycow.frostiful.config.group;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = Frostiful.MODID + ".combat_config")
public class CombatConfigGroup implements ConfigData {

    int heatDrainPerLevel = 630;
    double heatDrainEfficiency = 0.5;

    float iceBreakerDamagePerLevel = 1.5f;

    double maxFrostSpellDistance = 20;

    int frostWandCooldown = 100;

    int frostWandFrozenEffectTime = 10;

    public int getHeatDrainPerLevel() {
        return heatDrainPerLevel;
    }

    public double getHeatDrainEfficiency() {
        return heatDrainEfficiency;
    }

    public float getIceBreakerDamagePerLevel() {
        return iceBreakerDamagePerLevel;
    }

    public double getMaxFrostSpellDistance() {
        return maxFrostSpellDistance;
    }

    public int getFrostWandCooldown() {
        return frostWandCooldown;
    }

    public int getFrostWandFrozenEffectTime() {
        return frostWandFrozenEffectTime;
    }
}
