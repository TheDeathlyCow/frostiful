package com.github.thedeathlycow.frostiful.config.group;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.minecraft.util.math.MathHelper;

@Config(name = Frostiful.MODID + ".combat_config")
public class CombatConfigGroup implements ConfigData {


    boolean doChillagerPatrols = true;
    int heatDrainPerLevel = 210;

    double heatDrainEfficiency = 0.5;

    float iceBreakerDamagePerLevel = 1.0f;

    float iceBreakerBaseDamage = 3.0f;

    double maxFrostSpellDistance = 25;

    int frostWandCooldown = 120;

    int frostWandRootTime = 100;

    int frostologerHeatDrainPerTick = 30;

    int packedSnowballFreezeAmount = 500;

    float packedSnowballDamage = 2.0f;

    float packedSnowballVulnerableTypesDamage = 5.0f;

    float polarBearShearingDamage = 1.0f;

    int frostologerPassiveFreezingPerTick = 2;

    float frostologerMaxPassiveFreezing = 0.5f;

    int biterFrostBiteMaxAmplifier = 2;

    public boolean doChillagerPatrols() {
        return doChillagerPatrols;
    }

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

    public int getPackedSnowballFreezeAmount() {
        return packedSnowballFreezeAmount;
    }

    public float getPackedSnowballDamage() {
        return packedSnowballDamage;
    }

    public float getPackedSnowballVulnerableTypesDamage() {
        return packedSnowballVulnerableTypesDamage;
    }

    public float getPolarBearShearingDamage() {
        return polarBearShearingDamage;
    }

    public int getFrostologerPassiveFreezingPerTick() {
        return frostologerPassiveFreezingPerTick;
    }

    public float getFrostologerMaxPassiveFreezing() {
        return frostologerMaxPassiveFreezing;
    }

    public int getBiterFrostBiteMaxAmplifier() {
        return Math.max(0, this.biterFrostBiteMaxAmplifier);
    }
}
