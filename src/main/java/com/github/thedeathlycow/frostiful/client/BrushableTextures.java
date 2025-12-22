package com.github.thedeathlycow.frostiful.client;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class BrushableTextures {

    public static final ResourceLocation POLAR_BEAR = Frostiful.id("textures/entity/bear/polar_bear_brushed.png");

    private BrushableTextures() {
    }
}
