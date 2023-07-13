package com.github.thedeathlycow.frostiful.client;

import com.github.thedeathlycow.frostiful.Frostiful;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class FTexturedRenderLayers {

    public static final Identifier ARMOR_TRIMS_ATLAS_TEXTURE = Frostiful.id("textures/atlas/custom_armor_trims.png");
    public static final RenderLayer ARMOR_TRIMS_RENDER_LAYER = RenderLayer.getArmorCutoutNoCull(ARMOR_TRIMS_ATLAS_TEXTURE);
    public static final Map<Identifier, Identifier> LAYERS_TO_LOADERS = new HashMap<>();

    private FTexturedRenderLayers() {

    }

    static {
        LAYERS_TO_LOADERS.put(ARMOR_TRIMS_ATLAS_TEXTURE, Frostiful.id("custom_armor_trims"));
    }

}
