package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.config.FrostifulConfig;
import com.github.thedeathlycow.frostiful.entity.FrostSpellEntity;
import com.github.thedeathlycow.frostiful.entity.SpellEntity;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FrostWandItem extends Item implements Vanishable {

    public FrostWandItem(Settings settings) {
        super(settings);
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
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

                FrostifulConfig config = Frostiful.getConfig();

                FrostSpellEntity spell = new FrostSpellEntity(
                        world,
                        user,
                        0.0, 0.0, 0.0,
                        config.combatConfig.getMaxFrostSpellDistance()
                );
                spell.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 2.5f, 1.0f);
                spell.setPosition(user.getEyePos());
                world.spawnEntity(spell);

                if (user instanceof PlayerEntity player) {
                    stack.damage(2, player, (p) -> {
                        p.sendToolBreakStatus(p.getActiveHand());
                    });
                    player.incrementStat(Stats.USED.getOrCreateStat(this));
                    player.getItemCooldownManager().set(this, config.combatConfig.getFrostWandCooldown());
                }
            }
        }
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }

    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if ((double) state.getHardness(world, pos) != 0.0) {
            stack.damage(2, miner, (e) -> {
                e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
            });
        }

        return true;
    }
}
