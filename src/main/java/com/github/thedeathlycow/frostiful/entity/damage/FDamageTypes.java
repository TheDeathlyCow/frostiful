package com.github.thedeathlycow.frostiful.entity.damage;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class FDamageTypes {

    public static final RegistryKey<DamageType> MELT = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Frostiful.id("melt"));

}
