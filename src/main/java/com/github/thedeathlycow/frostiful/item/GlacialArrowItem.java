package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.entity.GlacialArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GlacialArrowItem extends ArrowItem {

    public GlacialArrowItem(Item.Settings settings) {
        super(settings);
    }

    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack shotFrom) {
        return new GlacialArrowEntity(world, shooter, stack.copyWithCount(1), shotFrom);
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        GlacialArrowEntity frostArrow = new GlacialArrowEntity(
                world,
                pos.getX(), pos.getY(), pos.getZ(),
                stack.copyWithCount(1),
                null
        );
        frostArrow.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        return frostArrow;
    }

}
