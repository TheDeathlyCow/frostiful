package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.ThrownIcicleEntity;
import com.github.thedeathlycow.frostiful.sound.FSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class IcicleItem extends BlockItem {


    public IcicleItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        world.playSound(
                null,
                user.getX(), user.getY(), user.getZ(),
                FSoundEvents.ENTITY_THROWN_ICICLE_THROW, SoundCategory.NEUTRAL,
                0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f)
        );

        if (!world.isClient) {
            ThrownIcicleEntity icicleEntity = new ThrownIcicleEntity(user, world, itemStack);

            icicleEntity.setVelocity(
                    user,
                    user.getPitch(), user.getYaw(),
                    0.0f, 1.0f, 1.0f
            );

            world.spawnEntity(icicleEntity);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));

        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        user.getItemCooldownManager().set(this, Frostiful.getConfig().icicleConfig.getThrownIcicleCooldown());

        return TypedActionResult.success(itemStack, world.isClient());
    }
}
