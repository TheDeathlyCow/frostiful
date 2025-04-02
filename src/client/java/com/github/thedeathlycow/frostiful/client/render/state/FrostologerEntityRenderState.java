package com.github.thedeathlycow.frostiful.client.render.state;

import com.github.thedeathlycow.frostiful.client.render.feature.FrostLayer;
import net.minecraft.client.render.entity.state.IllagerEntityRenderState;
import net.minecraft.util.Identifier;

public class FrostologerEntityRenderState extends IllagerEntityRenderState {
    public boolean usingFrostWand = false;
    public Identifier capeTexture = null;
    public int tint = 0;
    public FrostLayer frostLayer = FrostLayer.NONE;
    public boolean glowingEyes = false;
    public float capePitch = 0f;
    public float capeSwing = 0f;
    public float capeStrafe = 0f;
}