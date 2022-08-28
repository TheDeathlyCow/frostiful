package com.github.thedeathlycow.frostiful.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class FrostSpellEntity extends SpellEntity {

    public FrostSpellEntity(World world, LivingEntity owner, double velocityX, double velocityY, double velocityZ) {
        super(world, owner, velocityX, velocityY, velocityZ);
    }

    public FrostSpellEntity(World world, LivingEntity owner, double velocityX, double velocityY, double velocityZ, double maxDistance) {
        super(world, owner, velocityX, velocityY, velocityZ, maxDistance);
    }

    protected FrostSpellEntity(EntityType<? extends SpellEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void applyEffectCloud() {
        if (this.isRemoved()) {
            return;
        }

        this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 0.01f, Explosion.DestructionType.NONE);

        this.discard();
    }

    @Override
    protected void applySingleTargetEffect(Entity target) {
        if (target instanceof RootedEntity rootedEntity) {
            rootedEntity.frostiful$root();
        }
    }
}
