package com.github.thedeathlycow.frostiful.entity;

import com.github.thedeathlycow.frostiful.attributes.FEntityAttributes;
import com.github.thedeathlycow.frostiful.item.FItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class ChillagerEntity extends PillagerEntity {
    public ChillagerEntity(EntityType<? extends ChillagerEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createChillagerAttributes() {
        return PillagerEntity.createPillagerAttributes()
                .add(FEntityAttributes.MAX_FROST, 45.0)
                .add(FEntityAttributes.FROST_RESISTANCE, 10.0);
    }

    @Override
    public ItemStack getArrowType(ItemStack stack) {
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
        return SoundEvents.ENTITY_PILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PILLAGER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PILLAGER_HURT;
    }

    @Override
    public SoundEvent getCelebratingSound() {
        return SoundEvents.ENTITY_PILLAGER_CELEBRATE;
    }

}
