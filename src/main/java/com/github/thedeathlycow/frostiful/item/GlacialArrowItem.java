package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.entity.GlacialArrowEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class GlacialArrowItem extends ArrowItem {

    public GlacialArrowItem(Item.Properties settings) {
        super(settings);
    }

    public AbstractArrow createArrow(Level world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack shotFrom) {
        return new GlacialArrowEntity(world, shooter, stack.copyWithCount(1), shotFrom);
    }

    @Override
    public Projectile asProjectile(Level world, Position pos, ItemStack stack, Direction direction) {
        GlacialArrowEntity frostArrow = new GlacialArrowEntity(
                world,
                pos.x(), pos.y(), pos.z(),
                stack.copyWithCount(1),
                null
        );
        frostArrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return frostArrow;
    }

}
