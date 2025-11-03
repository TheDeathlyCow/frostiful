package com.github.thedeathlycow.frostiful.registry.tag;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public final class FDamageTypeTags {

    public static final TagKey<DamageType> IS_ICICLE = register("is_icicle");
    public static final TagKey<DamageType> DOES_NOT_BREAK_ROOT = register("does_not_break_root");

    private static TagKey<DamageType> register(String id) {
        return TagKey.create(Registries.DAMAGE_TYPE, Frostiful.location(id));
    }

    private FDamageTypeTags() {

    }
}
