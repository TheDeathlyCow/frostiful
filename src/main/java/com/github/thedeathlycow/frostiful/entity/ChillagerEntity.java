package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.sound.FSoundEvents;
import com.github.thedeathlycow.thermoo.api.ThermooAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class ChillagerEntity extends PillagerEntity {
    public ChillagerEntity(EntityType<? extends ChillagerEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createChillagerAttributes() {
        return PillagerEntity.createPillagerAttributes()
                .add(ThermooAttributes.MIN_TEMPERATURE, 5.0)
                .add(ThermooAttributes.FROST_RESISTANCE, 10.0);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {

        if (source.isIn(DamageTypeTags.IS_FIRE)) {
            FrostifulConfig config = Frostiful.getConfig();
            amount *= config.combatConfig.getChillagerFireDamageMultiplier();
        }

        return super.damage(source, amount);
    }

    @Override
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
        if (world.getDifficulty() != Difficulty.PEACEFUL) {
            Frostiful.LOGGER.info("Chillager {} was struck by lightning {}.", this, lightning);
            FrostologerEntity frostologer = FEntityTypes.FROSTOLOGER.create(world);
            if (frostologer == null) {
                return;
            }

            frostologer.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
            frostologer.initialize(
                    world,
                    world.getLocalDifficulty(frostologer.getBlockPos()),
                    SpawnReason.CONVERSION,
                    null,
                    null
            );
            frostologer.setAiDisabled(this.isAiDisabled());
            if (this.hasCustomName()) {
                frostologer.setCustomName(this.getCustomName());
                frostologer.setCustomNameVisible(this.isCustomNameVisible());
            }

            frostologer.setPersistent();
            world.spawnEntityAndPassengers(frostologer);
            // TODO: uncomment this line for 1.0.6
//            ServerLivingEntityEvents.MOB_CONVERSION.invoker().onConversion(
//                    this,
//                    frostologer,
//                    false
//            );
            this.discard();
        } else {
            super.onStruckByLightning(world, lightning);
        }
    }

    @Override
    public ItemStack getProjectileType(ItemStack stack) {
        if (stack.getItem() instanceof RangedWeaponItem rangedWeaponItem) {
            ItemStack itemStack = RangedWeaponItem.getHeldProjectile(
                    this,
                    rangedWeaponItem.getHeldProjectiles()
            );

            return itemStack.isEmpty() ? new ItemStack(FItems.FROST_TIPPED_ARROW) : itemStack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FSoundEvents.ENTITY_CHILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FSoundEvents.ENTITY_CHILLAGER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FSoundEvents.ENTITY_CHILLAGER_HURT;
    }

    @Override
    public SoundEvent getCelebratingSound() {
        return FSoundEvents.ENTITY_CHILLAGER_CELEBRATE;
    }

}
