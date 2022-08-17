package com.github.thedeathlycow.frostiful.entity.damage;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import org.jetbrains.annotations.Nullable;

public class FrostifulDamageSource extends DamageSource { // extend DamageSource to access its constructor

    public static final String FROZEN_ATTACK_NAME = "frostiful.frozenAttack";
    public static final DamageSource FALLING_ICICLE = new FrostifulDamageSource(Frostiful.MODID + ".fallingIcicle").setFallingBlock();
    public static final DamageSource ICICLE = new FrostifulDamageSource(Frostiful.MODID + ".icicle").setFromFalling();

    public static DamageSource frozenAttack(@Nullable Entity attacker) {
        return new EntityDamageSource(FROZEN_ATTACK_NAME, attacker);
    }

    public static void registerDamageSources() {
        // damage sources already registered - just ensure this class is loaded
    }

    protected FrostifulDamageSource(String name) {
        super(name);
    }
}
