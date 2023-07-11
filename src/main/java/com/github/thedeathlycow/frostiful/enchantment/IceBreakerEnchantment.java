package com.github.thedeathlycow.frostiful.enchantment;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.enchantment.target.FEnchantmentTargets;
import com.github.thedeathlycow.frostiful.entity.RootedEntity;
import com.github.thedeathlycow.frostiful.entity.damage.FDamageSources;
import com.github.thedeathlycow.frostiful.registry.FItems;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.jetbrains.annotations.Nullable;

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

    /**
     * Applies bonus damage to targets when they take any damage while rooted. Applies an extra bit of bonus damage
     * that scales with the {@code level} of the Ice Breaker enchantment.
     *
     * @param attacker The (potential) attacker of the target
     * @param target The target
     * @param level The level of the ice breaker enchantment (can be 0 if not applied)
     */
    public static void applyIceBreakDamage(@Nullable LivingEntity attacker, Entity target, int level) {
        float extraDamage = getIceBreakerDamage(level);
        if (extraDamage > 0f) {
            FDamageSources damageSources = FDamageSources.getDamageSources(target.getWorld());

            DamageSource damageSource = damageSources.frostiful$brokenIce(attacker);

            if (target.damage(damageSource, extraDamage) && attacker != null) {
                procIceBreakerForAttacker(attacker);
            }
        }
    }

    private static void procIceBreakerForAttacker(LivingEntity attacker) {
        attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 30, 2));

        boolean resetFrostWandCooldown = attacker.isPlayer()
                && attacker.getEquippedStack(EquipmentSlot.MAINHAND).isOf(FItems.FROST_WAND);

        if (resetFrostWandCooldown) {
            PlayerEntity player = (PlayerEntity) attacker;

            ItemCooldownManager coolDownManager = player.getItemCooldownManager();

            coolDownManager.set(FItems.FROST_WAND, 0);
        }

    }

    public static void spawnShatterParticlesAndSound(LivingEntity victim, ServerWorld serverWorld) {
        ParticleEffect shatteredIce = new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.BLUE_ICE.getDefaultState());

        serverWorld.spawnParticles(
                shatteredIce,
                victim.getX(), victim.getY(), victim.getZ(),
                500,
                0.5, 1.0, 0.5,
                1.0
        );

        victim.getWorld().playSound(
                null,
                victim.getBlockPos(),
                SoundEvents.BLOCK_GLASS_BREAK,
                SoundCategory.AMBIENT,
                1.0f, 0.75f
        );
    }

    private static float getIceBreakerDamage(int level) {
        FrostifulConfig config = Frostiful.getConfig();
        float damage = config.combatConfig.getIceBreakerBaseDamage();
        return damage + level * config.combatConfig.getIceBreakerDamagePerLevel();
    }

}
