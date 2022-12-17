package com.github.thedeathlycow.frostiful.config.group;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = Frostiful.MODID + ".combat_config")
public class CombatConfigGroup implements ConfigData {

    int heatDrainPerLevel = 210;

    double heatDrainEfficiency = 0.5;

    float iceBreakerDamagePerLevel = 1.0f;

    float iceBreakerBaseDamage = 3.0f;

    double maxFrostSpellDistance = 25;

    int frostWandCooldown = 120;

    int frostWandRootTime = 100;

    int frostologerHeatDrainPerTick = 50;

    public int getHeatDrainPerLevel() {
        return heatDrainPerLevel;
    }

    public double getHeatDrainEfficiency() {
        return heatDrainEfficiency;
    }

    public float getIceBreakerDamagePerLevel() {
        return iceBreakerDamagePerLevel;
    }

    public float getIceBreakerBaseDamage() {
        return iceBreakerBaseDamage;
    }

    public double getMaxFrostSpellDistance() {
        return maxFrostSpellDistance;
    }

    public int getFrostWandCooldown() {
        return frostWandCooldown;
    }

    public int getFrostWandRootTime() {
        return frostWandRootTime;
    }

    public int getFrostologerHeatDrainPerTick() {
        // multiply by 2 as goals only twice at half the rate of normal
        return 2 * frostologerHeatDrainPerTick;
    }
}
