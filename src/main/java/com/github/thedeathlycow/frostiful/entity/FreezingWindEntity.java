package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.thermoo.api.core.v2.source.TemperatureSources;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

public class FreezingWindEntity extends WindEntity {
    private int temperatureChange;

    public FreezingWindEntity(EntityType<? extends FreezingWindEntity> type, Level world) {
        super(type, world);
        this.temperatureChange = FrostifulConfigYACL.temperatureSourceSettings().freezingWindTemperatureChange();
    }

    @Override
    public void onEntityCollision(LivingEntity entity) {
        super.onEntityCollision(entity);
        freezeEntity(entity, this.temperatureChange, this);
    }

    public static void freezeEntity(LivingEntity entity, int frost, @Nullable FreezingWindEntity source) {
        if (entity.getType() == EntityType.PLAYER) {
            entity.thermoo$addTemperature(
                    frost,
                    entity.level().thermoo$temperatureSources().create(TemperatureSources.ACTIVE, source)
            );
        }
    }

    protected ParticleOptions getDustParticle() {
        return ParticleTypes.SNOWFLAKE;
    }

    @Override
    protected void readAdditionalSaveData(ValueInput readView) {
        super.readAdditionalSaveData(readView);
        this.temperatureChange = readView.getIntOr("temperature_change", FrostifulConfigYACL.temperatureSourceSettings().freezingWindTemperatureChange());
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput writeView) {
        super.addAdditionalSaveData(writeView);

        writeView.putInt("temperature_change", this.temperatureChange);
    }
}
