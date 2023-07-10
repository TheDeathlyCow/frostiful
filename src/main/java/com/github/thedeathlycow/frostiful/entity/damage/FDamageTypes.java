package com.github.thedeathlycow.frostiful.entity.damage;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class FDamageTypes {

    public static final RegistryKey<DamageType> FALLING_ICICLE =  RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Frostiful.id("falling_icicle"));
    public static final RegistryKey<DamageType> ICICLE =  RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Frostiful.id("icicle"));
    public static final RegistryKey<DamageType> ICE_SKATE =  RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Frostiful.id("ice_skate"));

}
