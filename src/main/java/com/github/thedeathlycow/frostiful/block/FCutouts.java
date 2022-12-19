package com.github.thedeathlycow.frostiful.block;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class FCutouts {

    public static void registerCutouts() {
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.ICICLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.COLD_SUN_LICHEN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.COOL_SUN_LICHEN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.WARM_SUN_LICHEN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.HOT_SUN_LICHEN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.FROZEN_TORCH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.FROZEN_WALL_TORCH, RenderLayer.getCutout());
    }

}
