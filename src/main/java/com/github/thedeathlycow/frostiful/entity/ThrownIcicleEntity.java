package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.section.ItemSettings;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import com.github.thedeathlycow.thermoo.api.core.v2.source.TemperatureSources;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class ThrownIcicleEntity extends AbstractArrow {

    public ThrownIcicleEntity(EntityType<? extends ThrownIcicleEntity> entityType, Level world) {
        super(entityType, world);
    }

    public ThrownIcicleEntity(Level world, double x, double y, double z, ItemStack stack) {
        super(FEntityTypes.THROWN_ICICLE, x, y, z, world, stack, stack);
    }

    public ThrownIcicleEntity(Level world, LivingEntity owner, ItemStack stack) {
        super(FEntityTypes.THROWN_ICICLE, owner, world, stack, null);
    }

    @Override
    public void tick() {
        super.tick();
        Level world = level();
        if (world.isClientSide() && !this.isInGround()) {
            world.addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {

        if (entityHitResult.getEntity().getType() == FEntityTypes.FROSTOLOGER) {
            return;
        }

        ItemSettings config = FrostifulConfigYACL.itemSettings();

        float damage = entityHitResult.getEntity().is(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)
                ? config.thrownIcicleVulnerableTypesDamage()
                : config.thrownIcicleDamage();
        this.setBaseDamage(damage);

        super.onHitEntity(entityHitResult);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        int freezeAmount = FrostifulConfigYACL.temperatureSourceSettings().thrownIcicleFreezeAmount();

        target.thermoo$addTemperature(
                freezeAmount,
                target.level().thermoo$temperatureSources().create(
                        TemperatureSources.ACTIVE,
                        this
                )
        );
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return FSoundEvents.ENTITY_THROWN_ICICLE_HIT;
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(FItems.ICICLE);
    }
}
