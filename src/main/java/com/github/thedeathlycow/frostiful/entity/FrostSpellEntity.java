package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.entity.effect.FrostifulStatusEffects;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.LingeringPotionItem;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class FrostSpellEntity extends ExplosiveProjectileEntity {

    private final int power;

    protected FrostSpellEntity(EntityType<? extends FrostSpellEntity> entityType, World world) {
        super(entityType, world);
        this.power = 0;
    }

    public FrostSpellEntity(World world, LivingEntity owner, double velocityX, double velocityY, double velocityZ) {
        this(world, owner, velocityX, velocityY, velocityZ, 0);
    }

    public FrostSpellEntity(World world, LivingEntity owner, double velocityX, double velocityY, double velocityZ, int power) {
        super(FrostifulEntityTypes.FROST_SPELL, owner, velocityX, velocityY, velocityZ, world);
        this.power = power;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.createFrozenCloud();
            this.discard();
        }
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

    private void createFrozenCloud() {

        this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 1.0f, Explosion.DestructionType.NONE);

        AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
        areaEffectCloudEntity.setRadius(2.5F);
        areaEffectCloudEntity.setRadiusOnUse(-0.5F);
        areaEffectCloudEntity.setWaitTime(10);
        areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 2);
        areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / areaEffectCloudEntity.getDuration());

        areaEffectCloudEntity.addEffect(new StatusEffectInstance(FrostifulStatusEffects.FROZEN, 200));

        this.world.spawnEntity(areaEffectCloudEntity);
    }
}
