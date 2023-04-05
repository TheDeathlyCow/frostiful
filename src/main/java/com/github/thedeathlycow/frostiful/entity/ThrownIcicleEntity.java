package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.config.group.IcicleConfigGroup;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.item.FItems;
import com.github.thedeathlycow.frostiful.sound.FSoundEvents;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class ThrownIcicleEntity extends PersistentProjectileEntity {

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
    public void tick() {
        super.tick();
        if (this.world.isClient && !this.inGround) {
            this.world.addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        IcicleConfigGroup config = Frostiful.getConfig().icicleConfig;

        float damage = entityHitResult.getEntity().getType().isIn(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)
                ? config.getThrownIcicleExtraDamage()
                : config.getThrownIcicleDamage();
        this.setDamage(damage);

        super.onEntityHit(entityHitResult);
    }

    @Override
    protected void onHit(LivingEntity target) {
        super.onHit(target);
        IcicleConfigGroup config = Frostiful.getConfig().icicleConfig;
        int freezeAmount = config.getThrownIcicleFreezeAmount();

        target.thermoo$addTemperature(-freezeAmount, HeatingModes.ACTIVE);
    }

    @Override
    protected SoundEvent getHitSound() {
        return FSoundEvents.ENTITY_THROWN_ICICLE_HIT;
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(FItems.ICICLE);
    }
}
