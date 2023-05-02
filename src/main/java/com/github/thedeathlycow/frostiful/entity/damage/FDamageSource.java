package com.github.thedeathlycow.frostiful.entity.damage;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.entity.damage.DamageSource;

public class FDamageSource extends DamageSource { // extend DamageSource to access its constructor

    public static final DamageSource MELT = new FDamageSource(Frostiful.MODID + ".melt");
    public static final DamageSource FALLING_ICICLE = new FDamageSource(Frostiful.MODID + ".fallingIcicle").setFallingBlock();
    public static final DamageSource ICICLE = new FDamageSource(Frostiful.MODID + ".icicle").setFromFalling();

    /**
     * A copy of {@link net.minecraft.entity.damage.DamageSources#hotFloor()} with the same name (for translation purposes), but a technically different
     * instance so that heat from hot floor is not applied by sun lichen
     */
    public static final DamageSource SUN_LICHEN = new FDamageSource("hotFloor").setFire();
    public static void registerDamageSources() {
        // damage sources already registered - just ensure this class is loaded
    }

    protected FDamageSource(String name) {
        super(name);
    }
}
