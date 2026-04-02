package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.section.ItemSettings;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.thermoo.api.core.v2.source.TemperatureSources;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class PackedSnowballEntity extends ThrowableItemProjectile {

    public PackedSnowballEntity(EntityType<? extends PackedSnowballEntity> entityType, Level world) {
        super(entityType, world);
    }

    public PackedSnowballEntity(Level world, LivingEntity owner, ItemStack stack) {
        super(FEntityTypes.PACKED_SNOWBALL, owner, world, stack);
    }

    public PackedSnowballEntity(Level world, double x, double y, double z, ItemStack stack) {
        super(FEntityTypes.PACKED_SNOWBALL, x, y, z, world, stack);
    }

    @Override
    protected Item getDefaultItem() {
        return FItems.PACKED_SNOWBALL;
    }

    @Override
    public void handleEntityEvent(byte status) {
        if (status == EntityEvent.DEATH) {
            ParticleOptions particleEffect = this.getParticleEffect();
            for (int i = 0; i < 8; i++) {
                this.level().addParticle(
                        particleEffect,
                        this.getX(), this.getY(), this.getZ(),
                        0.0, 0.0, 0.0
                );
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Entity target = entityHitResult.getEntity();

        ItemSettings config = FrostifulConfigYACL.itemSettings();

        float damage = target.is(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)
                ? config.packedSnowballVulnerableTypesDamage()
                : config.packedSnowballDamage();

        target.hurt(this.damageSources().thrown(this, this.getOwner()), damage);

        if (target instanceof LivingEntity livingTarget) {
            livingTarget.thermoo$addTemperature(
                    FrostifulConfigYACL.temperatureSourceSettings().packedSnowballTemperatureChange(),
                    target.level().thermoo$temperatureSources().create(
                            TemperatureSources.ACTIVE,
                            this
                    )
            );
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        Level world = level();
        if (!world.isClientSide()) {
            world.broadcastEntityEvent(this, EntityEvent.DEATH);
            this.discard();
        }
    }

    private ParticleOptions getParticleEffect() {
        ItemStack itemStack = this.getItem();
        return itemStack.isEmpty()
                ? ParticleTypes.ITEM_SNOWBALL
                : new ItemParticleOption(ParticleTypes.ITEM, itemStack.getItem());
    }

}
