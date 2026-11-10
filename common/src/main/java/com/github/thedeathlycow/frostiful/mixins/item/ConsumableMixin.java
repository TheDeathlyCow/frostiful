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

package com.github.thedeathlycow.frostiful.mixins.item;

import com.github.thedeathlycow.frostiful.item.component.ConsumeCallback;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Consumable.class)
public class ConsumableMixin {
    @Inject(
            method = "onConsume",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getAllOfType(Ljava/lang/Class;)Ljava/util/stream/Stream;"
            )
    )
    private void onConsumeItem(Level level, LivingEntity user, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        ConsumeCallback.EVENT.invoker().onConsume(level, user, stack, (Consumable) (Object) this);
    }
}
