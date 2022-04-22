package com.github.thedeathlycow.lostinthecold.block;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class LostInTheColdCutouts {

    public static void registerCutouts() {
        BlockRenderLayerMap.INSTANCE.putBlock(LostInTheColdBlocks.ICICLE, RenderLayer.getCutout());
    }

}
