package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.config.group.IcicleConfigGroup;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.item.FItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ThrownIcicleEntity extends ThrownItemEntity {

    public ThrownIcicleEntity(EntityType<? extends ThrownIcicleEntity> entityType, World world) {
        super(entityType, world);
    }

    public ThrownIcicleEntity(double d, double e, double f, World world) {
        super(FEntityTypes.THROWN_ICICLE, d, e, f, world);
    }

    public ThrownIcicleEntity(LivingEntity livingEntity, World world) {
        super(FEntityTypes.THROWN_ICICLE, livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return FItems.ICICLE;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity victim = entityHitResult.getEntity();

        IcicleConfigGroup config = Frostiful.getConfig().icicleConfig;

        float damage = victim.getType().isIn(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)
                ? config.getThrownIcicleExtraDamage()
                : config.getThrownIcicleDamage();

        victim.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }

    }
}
