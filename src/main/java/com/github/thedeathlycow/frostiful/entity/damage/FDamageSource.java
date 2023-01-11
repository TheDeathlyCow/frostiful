package com.github.thedeathlycow.frostiful.entity.damage;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import org.jetbrains.annotations.Nullable;

public class FDamageSource extends DamageSource { // extend DamageSource to access its constructor

    public static final DamageSource MELT = new FDamageSource(Frostiful.MODID + ".melt").setFire();
    public static final DamageSource FALLING_ICICLE = new FDamageSource(Frostiful.MODID + ".fallingIcicle").setFallingBlock();
    public static final DamageSource ICICLE = new FDamageSource(Frostiful.MODID + ".icicle").setFromFalling();

    public static void registerDamageSources() {
        // damage sources already registered - just ensure this class is loaded
    }

    protected FDamageSource(String name) {
        super(name);
    }
}
