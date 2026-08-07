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

package com.github.thedeathlycow.frostiful.mixins.entity.ice_skating;

import com.github.thedeathlycow.frostiful.registry.FSoundEvents;
import com.github.thedeathlycow.frostiful.survival.system.IceSkateSystem;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow @Final protected RandomSource random;

    @Inject(
            method = "walkingStepSound",
            at = @At("HEAD"),
            cancellable = true
    )
    private void playSkateSlideSound(BlockPos pos, BlockState state, CallbackInfo ci) {
        final Entity instance = (Entity) (Object) this;

        if (ci.isCancelled()) {
            return;
        }

        if (instance instanceof LivingEntity livingEntity) {
            boolean playGlideSound = IceSkateSystem.isIceSkatingAndMoving(livingEntity);

            if (playGlideSound) {
                // don't also play the normal step sounds
                ci.cancel();

                float pitch = random.nextFloat() * 0.75f + 0.5f;
                instance.playSound(FSoundEvents.ENTITY_GENERIC_ICE_SKATE_GLIDE, 1.0f, pitch);

                boolean playSkateSound = instance.isSprinting()
                        && random.nextFloat() < 0.1f;

                if (playSkateSound) {
                    pitch = random.nextFloat() * 0.2f + 0.9f;
                    instance.playSound(FSoundEvents.ENTITY_GENERIC_ICE_SKATE_SKATE, 1.0f, pitch);
                }
            }
        }
    }
}
