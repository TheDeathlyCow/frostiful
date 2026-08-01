package com.github.thedeathlycow.frostiful.client.render.state;

import com.github.thedeathlycow.frostiful.client.render.feature.FrostLayer;
import net.minecraft.client.renderer.entity.state.IllagerRenderState;
import net.minecraft.core.ClientAsset;

public class FrostologerEntityRenderState extends IllagerRenderState {
    public boolean usingFrostWand = false;
    public ClientAsset.ResourceTexture capeTexture = null;
    public int tint = 0;
    public FrostLayer frostLayer = FrostLayer.NONE;
    public boolean glowingEyes = false;
    public float capePitch = 0f;
    public float capeSwing = 0f;
    public float capeStrafe = 0f;
}