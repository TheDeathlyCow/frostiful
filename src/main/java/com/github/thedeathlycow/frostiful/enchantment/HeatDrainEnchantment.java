package com.github.thedeathlycow.frostiful.enchantment;

import com.github.thedeathlycow.frostiful.config.group.CombatConfigGroup;
import com.github.thedeathlycow.frostiful.particle.HeatDrainParticleEffect;
import com.github.thedeathlycow.frostiful.util.FrostifulMathHelper;
import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class HeatDrainEnchantment extends Enchantment {

    public HeatDrainEnchantment(Rarity weight, EquipmentSlot[] slotTypes) {
        super(weight, EnchantmentTarget.WEAPON, slotTypes);
    }

    @Override
    public boolean isTreasure() {
        return true;
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
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {

        if (!target.canFreeze()) {
            return;
        }

        int heatDrained = CombatConfigGroup.HEAT_DRAIN_PER_LEVEL.getValue() * level;

        if (target instanceof LivingEntity livingTarget) {
            heatDrained = FrostHelper.addLivingFrost(livingTarget, heatDrained);
        } else {
            heatDrained = FrostHelper.addFrost(target, heatDrained);
        }

        int frostRemoved = MathHelper.floor(heatDrained * CombatConfigGroup.HEAT_DRAIN_EFFICIENCY.getValue());
        FrostHelper.removeLivingFrost(user, frostRemoved);

        if (heatDrained > 0) {
            addHeatDrainParticles(user, target, level);
        }
    }

    public static void addHeatDrainParticles(LivingEntity user, Entity target, int level) {
        World world = user.getEntityWorld();
        if (world instanceof ServerWorld serverWorld) {
            Vec3d from = FrostifulMathHelper.getMidPoint(target.getEyePos(), target.getPos());
            final int numParticles = (level << 1) + 5;

            double fromX = from.getX();
            double fromY = from.getY();
            double fromZ = from.getZ();
            HeatDrainParticleEffect effect = new HeatDrainParticleEffect(user.getEyePos());
            serverWorld.spawnParticles(effect, fromX, fromY, fromZ, numParticles, 0.5, 0.5, 0.5, 0.3);
        }
    }
}