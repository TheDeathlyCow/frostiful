package com.github.thedeathlycow.frostiful.entity.damage;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.entity.damage.DamageSource;

public class FrostifulDamageSource extends DamageSource {

    public static final DamageSource FALLING_ICICLE = new FrostifulDamageSource(Frostiful.MODID + ".fallingIcicle").setFallingBlock();
    public static final DamageSource ICICLE = new FrostifulDamageSource(Frostiful.MODID + ".icicle").setFromFalling();

    protected FrostifulDamageSource(String name) {
        super(name);
    }

    public static void registerDamageSources() {
        Frostiful.LOGGER.info("Registering damage sources...");
        // damage sources already registered - just ensure this class is loaded
        Frostiful.LOGGER.info("Registered damage sources!");
    }
}
