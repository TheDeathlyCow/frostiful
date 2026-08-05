package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.config.section.ItemSettings;
import com.github.thedeathlycow.frostiful.entity.FrostSpell;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import com.github.thedeathlycow.frostiful.survival.system.FrostRootSystem;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class FrostWandItem extends Item {

    public FrostWandItem(Properties settings) {
        super(settings);
    }

    public static ItemAttributeModifiers createAttributeModifiers() {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID,
                                5.0,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(
                                BASE_ATTACK_SPEED_ID,
                                -2.9f,
                                AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    public static Tool createToolComponent() {
        return new Tool(List.of(), 1.0f, 2, false);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public float getAttackDamageBonus(Entity target, float baseAttackDamage, DamageSource damageSource) {
        Entity attacker = damageSource.getEntity();
        boolean resetCooldown = target instanceof LivingEntity livingEntity && FrostRootSystem.isRooted(livingEntity);
        if (attacker instanceof Player player && resetCooldown) {
            player.getCooldowns().addCooldown(this.getDefaultInstance(), 0);
        }

        return super.getAttackDamageBonus(target, baseAttackDamage, damageSource);
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        int useTime = this.getUseDuration(stack, user) - remainingUseTicks;
        if (useTime > 10 && !world.isClientSide()) {
            fireFrostSpell(stack, world, user);
            return true;
        }
        return false;
    }

    public static void fireFrostSpell(ItemStack frostWandStack, Level level, LivingEntity user) {
        ItemSettings config = FrostifulConfigYACL.itemSettings();

        FrostSpell spell = new FrostSpell(level, user, Vec3.ZERO, config.maxFrostSpellDistance());

        spell.shootFromRotation(user, user.getXRot(), user.getYHeadRot(), 0.0f, 2.5f, 1.0f);

        level.addFreshEntity(spell);

        spell.playSound(FSoundEvents.ITEM_FROST_WAND_CAST_SPELL, 1f, 1f);

        if (user instanceof Player player) {
            frostWandStack.hurtWithoutBreaking(1, player);
            player.awardStat(Stats.ITEM_USED.get(frostWandStack.getItem()));
            player.getCooldowns().addCooldown(frostWandStack, config.frostWandCooldown());
        }
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (itemStack.getDamageValue() >= itemStack.getMaxDamage() - 1) {
            return InteractionResult.FAIL;
        } else {
            if (!world.isClientSide()) {
                world.playSound(
                        null,
                        user.getX(), user.getY(), user.getZ(),
                        FSoundEvents.ITEM_FROST_WAND_PREPARE_CAST,
                        SoundSource.PLAYERS,
                        1.0f, 1.0f
                );
            }
            user.startUsingItem(hand);
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getDestroySpeed(world, pos) != 0.0f) {
            stack.hurtAndBreak(2, miner, miner.getUsedItemHand());
        }

        return true;
    }

//    @Override
//    public boolean canBeEnchantedWith(ItemStack stack, Enchantment enchantment, EnchantingContext context) {
//
//        if (Registries.ENCHANTMENT.getEntry(enchantment).isIn(FEnchantmentTags.FROST_WAND_ENCHANTING_TABLE)) {
//            return true;
//        }
//
//        return super.canBeEnchantedWith(stack, enchantment, context);
//    }
}
