package com.github.thedeathlycow.frostiful.mixins.entity;

import com.github.thedeathlycow.frostiful.entity.damage.FDamageSources;
import com.github.thedeathlycow.frostiful.entity.damage.FDamageTypes;
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
