package com.github.thedeathlycow.frostiful.entity.damage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;


/**
 * Extension of {@link net.minecraft.entity.damage.DamageSources}. Interface-injected into that class
 * so that it can be used along-side it.
 */
public interface FDamageSources {

    DamageSource frostiful$fallingIcicle(Entity attacker);

    DamageSource frostiful$icicle();

    DamageSource frostiful$iceSkate(Entity attacker);

    DamageSource frostiful$brokenIce(Entity attacker);

    static FDamageSources getDamageSources(World world) {
        return (FDamageSources) world.getDamageSources();
    }

}
