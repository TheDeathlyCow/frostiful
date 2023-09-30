package com.github.thedeathlycow.frostiful.config.group;

import com.github.thedeathlycow.frostiful.Frostiful;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = Frostiful.MODID + ".client_config")
public class ClientConfigGroup implements ConfigData {

    float frostOverlayStart = 0.5f;
    boolean doColdHeartOverlay = true;
    boolean renderDripParticles = true;
    boolean disableFrostOverlayWhenWearingFrostologyCloak = true;
    boolean shakeHandWhenShivering = true;
    float handShakeIntensity = 1.0f;

    public float getFrostOverlayStart() {
        return frostOverlayStart;
    }

    public boolean doColdHeartOverlay() {
        return doColdHeartOverlay;
    }

    public boolean renderDripParticles() {
        return renderDripParticles;
    }

    public boolean isDisableFrostOverlayWhenWearingFrostologyCloak() {
        return disableFrostOverlayWhenWearingFrostologyCloak;
    }

    public boolean isShakeCameraWhenShiveringEnabled() {
        return shakeHandWhenShivering;
    }

    public float getHandShakeIntensity() {
        return handShakeIntensity;
    }
}
