package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.entity.effect.FrostifulStatusEffects;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class SpellEntity extends ProjectileEntity {

    protected SpellEntity(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    public void onEntityHit(EntityHitResult hitResult) {
        super.onEntityHit(hitResult);
        if (!this.world.isClient) {
            Entity entityHit = hitResult.getEntity();
            entityHit.damage(DamageSource.FREEZE, 3.0f);
            if (entityHit instanceof LivingEntity livingHitEntity) {
                livingHitEntity.addStatusEffect(new StatusEffectInstance(FrostifulStatusEffects.FROZEN, 200));
            }
        }
    }
}
