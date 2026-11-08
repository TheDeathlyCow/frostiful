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

import com.github.thedeathlycow.frostiful.entity.damage.FDamageSources;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import com.github.thedeathlycow.frostiful.survival.system.IceSkateSystem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    public abstract ItemStack getItemBySlot(EquipmentSlot slot);

    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(
            method = "aiStep",
            at = @At("TAIL")
    )
    private void updateIsIceSkating(CallbackInfo ci) {
        ProfilerFiller profiler = Profiler.get();
        profiler.push("frostiful.ice_skate_tick");

        LivingEntity self = (LivingEntity) (Object) this;
        IceSkateSystem.aiStep(self);

        profiler.pop();
    }

    @Inject(
            method = "push(Lnet/minecraft/world/entity/Entity;)V",
            at = @At("HEAD")
    )
    private void damageOnLandingUponEntity(Entity entity, CallbackInfo ci) {
        if (!this.getItemBySlot(EquipmentSlot.FEET).is(FItemTags.ICE_SKATES)) {
            return;
        }

        if (entity instanceof LivingEntity target && target.level() instanceof ServerLevel world) {
            double attackerHeight = this.position().y;
            double targetEyeHeight = target.getEyePosition().y;

            if (attackerHeight > targetEyeHeight) {
                FDamageSources damageSources = FDamageSources.getDamageSources(this.level());
                target.hurtServer(world, damageSources.frostiful$iceSkate(this), 1.0f);
            }
        }
    }
}
