package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.entity.FrostTippedArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FrostTippedArrowItem extends ArrowItem {

    public FrostTippedArrowItem(Settings settings) {
        super(settings);
    }

    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter) {
        return new FrostTippedArrowEntity(world, shooter);
    }

}
