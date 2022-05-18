package com.github.thedeathlycow.frostiful.mixins.entity.mob;

import com.github.thedeathlycow.frostiful.item.FrostifulItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Predicate;

@Mixin(StrayEntity.class)
public abstract class StrayDefaultArrowItemMixin extends AbstractSkeletonEntity {

    public StrayDefaultArrowItemMixin(EntityType<? extends StrayEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ItemStack getArrowType(ItemStack stack) {
        if (stack.getItem() instanceof RangedWeaponItem) {
            Predicate<ItemStack> predicate = ((RangedWeaponItem) stack.getItem()).getHeldProjectiles();
            ItemStack itemStack = RangedWeaponItem.getHeldProjectile(this, predicate);
            return itemStack.isEmpty() ? new ItemStack(FrostifulItems.FROST_TIPPED_ARROW) : itemStack;
        } else {
            return ItemStack.EMPTY;
        }
    }

}
