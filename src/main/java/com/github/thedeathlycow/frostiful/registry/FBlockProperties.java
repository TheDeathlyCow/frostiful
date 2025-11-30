package com.github.thedeathlycow.frostiful.registry;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public final class FBlockProperties {
    public static final int MAX_CRACKING = 3;
    public static final IntegerProperty CRACKING = IntegerProperty.create("cracking", 0, MAX_CRACKING);
    public static final BooleanProperty FROZEN = BooleanProperty.create("frozen");

    private FBlockProperties() {

    }
}