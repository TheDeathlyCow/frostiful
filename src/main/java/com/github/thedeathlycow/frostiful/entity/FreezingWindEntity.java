package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.world.World;

public class FreezingWindEntity extends WindEntity {

    private int frost;

    public FreezingWindEntity(EntityType<? extends FreezingWindEntity> type, World world) {
        super(type, world);
        this.frost = Frostiful.getConfig().freezingConfig.getFreezingWindFrost();
    }

    @Override
    public void onEntityCollision(LivingEntity entity) {
        super.onEntityCollision(entity);
        freezeEntity(entity, this.frost);
    }

    public static void freezeEntity(LivingEntity entity, int frost) {
        if (entity.getType() == EntityType.PLAYER) {
            entity.thermoo$addTemperature(-frost, HeatingModes.ACTIVE);
        }
    }

    protected ParticleEffect getDustParticle() {
        return ParticleTypes.SNOWFLAKE;
    }

    @Override
    protected void readCustomData(ReadView readView) {
        super.readCustomData(readView);
        this.frost = readView.getInt("Frost", Frostiful.getConfig().freezingConfig.getFreezingWindFrost());
    }

    @Override
    protected void writeCustomData(WriteView writeView) {
        super.writeCustomData(writeView);

        writeView.putInt("Frost", this.frost);
    }
}
