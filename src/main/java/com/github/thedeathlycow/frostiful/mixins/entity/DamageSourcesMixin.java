package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.entity.damage.FDamageSources;
import com.github.thedeathlycow.frostiful.entity.damage.FDamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
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
    protected abstract DamageSource create(RegistryKey<DamageType> key, @Nullable Entity attacker);

    @Shadow
    protected abstract DamageSource create(RegistryKey<DamageType> key);

    @Unique
    private DamageSource frostiful$icicle;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void init(DynamicRegistryManager registryManager, CallbackInfo ci) {
        this.frostiful$icicle = this.create(FDamageTypes.ICICLE);
    }

    @Override
    @Unique
    public DamageSource frostiful$fallingIcicle(Entity attacker) {
        return this.create(FDamageTypes.FALLING_ICICLE, attacker);
    }

    @Override
    @Unique
    public DamageSource frostiful$icicle() {
        return this.frostiful$icicle;
    }
}
