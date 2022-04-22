package com.github.thedeathlycow.lostinthecold.entity.damage;

import net.minecraft.entity.damage.DamageSource;

public class LostInTheColdDamageSource extends DamageSource {

    public static final DamageSource FALLING_ICICLE = new LostInTheColdDamageSource("fallingIcicle").setFallingBlock();
    public static final DamageSource ICICLE = new LostInTheColdDamageSource("icicle").setFromFalling();

    protected LostInTheColdDamageSource(String name) {
        super(name);
    }
}
