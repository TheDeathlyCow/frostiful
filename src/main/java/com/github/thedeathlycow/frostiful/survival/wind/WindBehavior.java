package com.github.thedeathlycow.frostiful.survival.wind;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

public enum WindBehavior implements StringRepresentable {
    NEVER("never"),
    DURING_RAIN("during_rain"),
    ALWAYS("always");

    public static final Codec<WindBehavior> CODEC = StringRepresentable.fromEnum(WindBehavior::values);

    private final String name;

    WindBehavior(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
