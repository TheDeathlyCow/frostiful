package com.github.thedeathlycow.frostiful.entity.damage;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;


/**
 * Extension of {@link net.minecraft.world.damagesource.DamageSources}. Interface-injected into that class
 * so that it can be used along-side it.
 */
public interface FDamageSources {

    DamageSource frostiful$fallingIcicle(Entity attacker);

    DamageSource frostiful$icicle();

    DamageSource frostiful$iceSkate(Entity attacker);

    DamageSource frostiful$brokenIce(Entity attacker);

    static FDamageSources getDamageSources(Level world) {
        return (FDamageSources) world.damageSources();
    }

}
