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

package com.github.thedeathlycow.frostiful.mixins.entity.mob;

import com.github.thedeathlycow.frostiful.config.FrostifulConfigYACL;
import com.github.thedeathlycow.frostiful.registry.FItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(Monster.class)
public abstract class StrayCarriesFrostArrowMixin extends PathfinderMob {

    protected StrayCarriesFrostArrowMixin(EntityType<? extends PathfinderMob> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(
            method = "getProjectile",
            at = @At("HEAD"),
            cancellable = true
    )
    private void straysHaveFrostArrows(ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (!FrostifulConfigYACL.entitySettings().straysCarryGlacialArrows()) {
            return;
        }

        if (this.getType() == EntityType.STRAY && stack.getItem() instanceof ProjectileWeaponItem rangedWeaponItem) {
            Predicate<ItemStack> isItemAmmoTest = rangedWeaponItem.getSupportedHeldProjectiles();
            ItemStack heldStack = ProjectileWeaponItem.getHeldProjectile(this, isItemAmmoTest);
            if (heldStack.isEmpty()) {
                cir.setReturnValue(new ItemStack(FItems.GLACIAL_ARROW));
            }
        }
    }

}
