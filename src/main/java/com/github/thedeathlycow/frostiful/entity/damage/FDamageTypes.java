package com.github.thedeathlycow.frostiful.entity.damage;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class FDamageTypes {

    public static final ResourceKey<DamageType> FALLING_ICICLE =  ResourceKey.create(Registries.DAMAGE_TYPE, Frostiful.id("falling_icicle"));
    public static final ResourceKey<DamageType> ICICLE =  ResourceKey.create(Registries.DAMAGE_TYPE, Frostiful.id("icicle"));
    public static final ResourceKey<DamageType> ICE_SKATE =  ResourceKey.create(Registries.DAMAGE_TYPE, Frostiful.id("ice_skate"));
    public static final ResourceKey<DamageType> BROKEN_ICE =  ResourceKey.create(Registries.DAMAGE_TYPE, Frostiful.id("broken_ice"));

    private FDamageTypes() {
    }
}
