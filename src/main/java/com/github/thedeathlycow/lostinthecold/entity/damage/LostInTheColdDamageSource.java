package com.github.thedeathlycow.lostinthecold.entity.damage;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import net.minecraft.entity.damage.DamageSource;

public class LostInTheColdDamageSource extends DamageSource {

    public static final DamageSource FALLING_ICICLE = new LostInTheColdDamageSource(LostInTheCold.MODID + ".fallingIcicle").setFallingBlock();
    public static final DamageSource ICICLE = new LostInTheColdDamageSource(LostInTheCold.MODID + ".icicle").setFromFalling();

    protected LostInTheColdDamageSource(String name) {
        super(name);
    }
}
