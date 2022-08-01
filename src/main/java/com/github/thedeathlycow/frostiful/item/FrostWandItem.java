package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.util.survival.FrostHelper;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FrostWandItem extends Item implements Vanishable {

    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public FrostWandItem(Settings settings) {
        super(settings);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Tool modifier", 8.0, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", -2.9000000953674316, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        int useTime = this.getMaxUseTime(stack) - remainingUseTicks;
        if (useTime > 10) {
            if (!world.isClient) {

                FireballEntity fireball = new FireballEntity(EntityType.FIREBALL, world);
                fireball.setOwner(user);
                fireball.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 2.5f, 1.0f);
                world.spawnEntity(fireball);

                if (user instanceof PlayerEntity player) {
                    stack.damage(1, player, (p) -> {
                        p.sendToolBreakStatus(user.getActiveHand());
                    });
                    player.incrementStat(Stats.USED.getOrCreateStat(this));
                }
            }
        }
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, (e) -> {
            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });

        FrostHelper.addLivingFrost(target, 1000);

        return true;
    }
}
