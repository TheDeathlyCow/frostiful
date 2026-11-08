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

package com.github.thedeathlycow.frostiful.mixins.block;

import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.PowderSnowBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public abstract class PowderSnowWalkableMixin {

    @Unique
    private static final EquipmentSlot[] ALLOWED_SLOTS = new EquipmentSlot[]{EquipmentSlot.FEET, EquipmentSlot.BODY};

    @ModifyReturnValue(
            method = "canEntityWalkOnPowderSnow",
            at = @At(
                    value = "RETURN",
                    ordinal = 1
            )
    )
    private static boolean ignoreHardCodedLeatherBootsCheck(boolean original) {
        return false;
    }

    @Inject(
            method = "canEntityWalkOnPowderSnow",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void checkPowderSnowWalkableItemTag(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof LivingEntity livingEntity) {
            for (EquipmentSlot slot : ALLOWED_SLOTS) {
                ItemStack stack = livingEntity.getItemBySlot(slot);
                if (!stack.isEmpty() && stack.is(FItemTags.POWDER_SNOW_WALKABLE)) {
                    cir.setReturnValue(true);
                    return;
                }
            }
        }
    }
}
