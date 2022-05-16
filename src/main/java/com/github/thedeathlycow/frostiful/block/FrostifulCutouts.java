package com.github.thedeathlycow.frostiful.block;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class FrostifulCutouts {

    public static void registerCutouts() {
        BlockRenderLayerMap.INSTANCE.putBlock(FrostifulBlocks.ICICLE, RenderLayer.getCutout());
    }

}
