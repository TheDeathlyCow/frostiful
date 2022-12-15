package com.github.thedeathlycow.frostiful.enchantment;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.enchantment.target.FEnchantmentTargets;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.particle.HeatDrainParticleEffect;
import com.github.thedeathlycow.frostiful.util.FMathHelper;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public sealed class EnervationEnchantment extends Enchantment permits FrozenTouchCurse {

    public EnervationEnchantment(Rarity weight, EquipmentSlot[] slotTypes) {
        super(weight, FEnchantmentTargets.FROSTIFUL_FROST_WAND, slotTypes);
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public int getMinPower(int level) {
        return level * 10;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof SwordItem
                || item instanceof AxeItem
                || super.isAcceptableItem(stack);
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {

        if (!target.canFreeze()) {
            return;
        }

        int heatDrained = 0;
        FrostifulConfig config = Frostiful.getConfig();

        if (target instanceof LivingEntity livingTarget) {
            heatDrained = FrostHelper.addLivingFrost(livingTarget, config.combatConfig.getHeatDrainPerLevel() * level);
        }

        int frostRemoved = MathHelper.floor(heatDrained * config.combatConfig.getHeatDrainEfficiency());
        FrostHelper.removeLivingFrost(user, frostRemoved);

        if (heatDrained > 0) {
            addHeatDrainParticles(user, target, level);
        }
    }

    public static void addHeatDrainParticles(Entity destination, Entity source, int level) {
        World world = destination.getEntityWorld();
        if (world instanceof ServerWorld serverWorld) {
            Vec3d from = FMathHelper.getMidPoint(source.getEyePos(), source.getPos());
            final int numParticles = (level << 1) + 5;

            double fromX = from.getX();
            double fromY = from.getY();
            double fromZ = from.getZ();
            HeatDrainParticleEffect effect = new HeatDrainParticleEffect(destination.getEyePos());
            serverWorld.spawnParticles(effect, fromX, fromY, fromZ, numParticles, 0.5, 0.5, 0.5, 0.3);
        }
    }
}