package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.config.group.CombatConfigGroup;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.thermoo.api.temperature.HeatingModes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class PackedSnowballEntity extends ThrownItemEntity {

    public PackedSnowballEntity(EntityType<? extends PackedSnowballEntity> entityType, World world) {
        super(entityType, world);
    }

    public PackedSnowballEntity(World world, LivingEntity owner) {
        super(FEntityTypes.PACKED_SNOWBALL, owner, world);
    }

    public PackedSnowballEntity(World world, double x, double y, double z) {
        super(FEntityTypes.PACKED_SNOWBALL, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            ParticleEffect particleEffect = this.getParticleEffect();
            for(int i = 0; i < 8; i++) {
                this.world.addParticle(
                        particleEffect,
                        this.getX(), this.getY(), this.getZ(),
                        0.0, 0.0, 0.0
                );
            }
        }
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity target = entityHitResult.getEntity();

        CombatConfigGroup config = Frostiful.getConfig().combatConfig;

        float damage = target.getType().isIn(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)
                ? config.getPackedSnowballVulnerableTypesDamage()
                : config.getPackedSnowballDamage();

        target.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);

        if (target instanceof LivingEntity livingTarget) {
            livingTarget.thermoo$addTemperature(
                    -config.getPackedSnowballFreezeAmount(),
                    HeatingModes.ACTIVE
            );
        }
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.world.sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }

    private ParticleEffect getParticleEffect() {
        ItemStack itemStack = this.getItem();
        return itemStack.isEmpty()
                ? ParticleTypes.ITEM_SNOWBALL
                : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
    }

}
