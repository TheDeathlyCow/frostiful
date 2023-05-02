package com.github.thedeathlycow.frostiful.enchantment;

import com.github.thedeathlycow.frostiful.enchantment.target.FEnchantmentTargets;
import com.github.thedeathlycow.frostiful.registry.FItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;

public class IceBreakerEnchantment extends Enchantment {

    public IceBreakerEnchantment(Rarity weight, EquipmentSlot[] slotTypes) {
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
        return 5;
    }

    public static void onUsedIceBreaker(LivingEntity attacker) {
        attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 30, 2));

        boolean resetFrostWandCooldown = attacker.isPlayer()
                && attacker.getEquippedStack(EquipmentSlot.MAINHAND).isOf(FItems.FROST_WAND);

        if (resetFrostWandCooldown) {
            PlayerEntity player = (PlayerEntity) attacker;

            ItemCooldownManager coolDownManager = player.getItemCooldownManager();

            coolDownManager.set(FItems.FROST_WAND, 0);
        }

    }

}
