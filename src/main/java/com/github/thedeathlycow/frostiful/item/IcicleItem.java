package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.entity.ThrownIcicleEntity;
import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class IcicleItem extends BlockItem implements ProjectileItem {
    public IcicleItem(Block block, Item.Properties settings) {
        super(block, settings.useBlockDescriptionPrefix());
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);

        world.playSound(
                null,
                user.getX(), user.getY(), user.getZ(),
                FSoundEvents.ENTITY_THROWN_ICICLE_THROW, SoundSource.NEUTRAL,
                0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f)
        );

        if (!world.isClientSide()) {
            ThrownIcicleEntity icicleEntity = new ThrownIcicleEntity(world, user, itemStack.copyWithCount(1));

            icicleEntity.shootFromRotation(
                    user,
                    user.getXRot(), user.getYRot(),
                    0.0f, 1.0f, 1.0f
            );
            if (user.getAbilities().instabuild) {
                icicleEntity.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
            }

            world.addFreshEntity(icicleEntity);
        }

        user.awardStat(Stats.ITEM_USED.get(this));
        itemStack.consume(1, user);
        user.getCooldowns().addCooldown(itemStack, FrostifulConfigYACL.icicleConfig().getThrownIcicleCooldown());

        return InteractionResult.SUCCESS;
    }

    @Override
    public Projectile asProjectile(Level world, Position pos, ItemStack stack, Direction direction) {
        ThrownIcicleEntity icicleEntity = new ThrownIcicleEntity(world, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1));
        icicleEntity.pickup = AbstractArrow.Pickup.ALLOWED;
        return icicleEntity;
    }
}
