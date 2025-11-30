package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class FreezingWindEntity extends WindEntity {

    private int frost;

    public FreezingWindEntity(EntityType<? extends FreezingWindEntity> type, Level world) {
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

    protected ParticleOptions getDustParticle() {
        return ParticleTypes.SNOWFLAKE;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("Frost", Tag.TAG_INT)) {
            this.frost = nbt.getInt("Frost");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);

        nbt.putInt("Frost", this.frost);
    }
}
