/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.entity.ThrownIcicle;
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
    private static final int COOLDOWN_TICKS = 10;

    public IcicleItem(Block block, Item.Properties settings) {
        super(block, settings.useBlockDescriptionPrefix());
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        if (!FrostifulConfigYACL.itemSettings().enableIcicleThrowing()) {
            return InteractionResult.PASS;
        }

        ItemStack itemStack = user.getItemInHand(hand);

        world.playSound(
                null,
                user.getX(), user.getY(), user.getZ(),
                FSoundEvents.ENTITY_THROWN_ICICLE_THROW, SoundSource.NEUTRAL,
                0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f)
        );

        if (!world.isClientSide()) {
            ThrownIcicle icicleEntity = new ThrownIcicle(world, user, itemStack.copyWithCount(1));

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
        user.getCooldowns().addCooldown(itemStack, COOLDOWN_TICKS);

        return InteractionResult.SUCCESS;
    }

    @Override
    public Projectile asProjectile(Level world, Position pos, ItemStack stack, Direction direction) {
        ThrownIcicle icicleEntity = new ThrownIcicle(world, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1));
        icicleEntity.pickup = AbstractArrow.Pickup.ALLOWED;
        return icicleEntity;
    }
}
