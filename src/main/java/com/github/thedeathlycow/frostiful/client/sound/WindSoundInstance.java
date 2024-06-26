package com.github.thedeathlycow.frostiful.client.sound;

import com.github.thedeathlycow.frostiful.entity.WindEntity;
import com.github.thedeathlycow.frostiful.sound.FSoundEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class WindSoundInstance extends MovingSoundInstance {

    private final WindEntity wind;

    public WindSoundInstance(WindEntity wind) {
        super(FSoundEvents.ENTITY_WIND_BLOW, SoundCategory.WEATHER, SoundInstance.createRandom());
        this.wind = wind;

        this.x = wind.getX();
        this.y = wind.getY();
        this.z = wind.getZ();
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 0.0f;
    }

    @Override
    public boolean canPlay() {
        return !this.wind.isSilent();
    }

    @Override
    public void tick() {
        if (wind.isRemoved()) {
            this.setDone();
            return;
        }

        this.x = this.wind.getX();
        this.y = this.wind.getY();
        this.z = this.wind.getZ();

        float speed = (float)this.wind.getVelocity().horizontalLength();
        if (speed >= 0.01f) {
            this.volume = MathHelper.lerp(1 / speed, 0.0f, 1.2f);
        } else {
            this.volume = 0.0f;
        }
    }

    @Override
    public boolean shouldAlwaysPlay() {
        return true;
    }
}
