package com.github.thedeathlycow.frostiful.config.group;

import com.github.thedeathlycow.frostiful.Frostiful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import net.minecraft.util.math.MathHelper;

@Config(name = Frostiful.MODID + ".client_config")
public class ClientConfigGroup implements ConfigData {

    float frostOverlayStart = 0.5f;
    boolean doColdHeartOverlay = true;
    float shakingStartPercent = 0.75f;
    boolean renderDripParticles = true;
    boolean disableFrostOverlayWhenWearingFrostologyCloak = true;

    public float getFrostOverlayStart() {
        return frostOverlayStart;
    }

    public boolean doColdHeartOverlay() {
        return doColdHeartOverlay;
    }

    public float getShakingStartPercent() {
        return MathHelper.clamp(shakingStartPercent, 0.0f, 1.0f);
    }

    public boolean renderDripParticles() {
        return renderDripParticles;
    }

    public boolean isDisableFrostOverlayWhenWearingFrostologyCloak() {
        return disableFrostOverlayWhenWearingFrostologyCloak;
    }
}
