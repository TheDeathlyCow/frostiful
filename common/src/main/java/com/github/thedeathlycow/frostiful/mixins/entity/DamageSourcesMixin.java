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

package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.entity.damage.FDamageSources;
import com.github.thedeathlycow.frostiful.registry.FDamageTypes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageSources.class)
public abstract class DamageSourcesMixin implements FDamageSources {

    @Shadow
    protected abstract DamageSource source(ResourceKey<DamageType> key, @Nullable Entity attacker);

    @Shadow
    protected abstract DamageSource source(ResourceKey<DamageType> key);

    @Unique
    private DamageSource frostiful$icicle;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void init(RegistryAccess registryManager, CallbackInfo ci) {
        this.frostiful$icicle = this.source(FDamageTypes.ICICLE);
    }

    @Override
    @Unique
    public DamageSource frostiful$fallingIcicle(Entity attacker) {
        return this.source(FDamageTypes.FALLING_ICICLE, attacker);
    }

    @Override
    @Unique
    public DamageSource frostiful$icicle() {
        return this.frostiful$icicle;
    }

    @Override
    @Unique
    public DamageSource frostiful$iceSkate(Entity attacker) {
        return this.source(FDamageTypes.ICE_SKATE, attacker);
    }

    @Override
    @Unique
    public DamageSource frostiful$brokenIce(Entity attacker) {
        return this.source(FDamageTypes.BROKEN_ICE, attacker);
    }
}
