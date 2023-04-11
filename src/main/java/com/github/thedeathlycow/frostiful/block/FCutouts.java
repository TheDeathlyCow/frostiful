package com.github.thedeathlycow.frostiful.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.render.entity.SpiderEntityRenderer;

@Environment(EnvType.CLIENT)
public class FCutouts {

    public static void registerCutouts() {
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.ICICLE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.COLD_SUN_LICHEN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.COOL_SUN_LICHEN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.WARM_SUN_LICHEN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.HOT_SUN_LICHEN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.FROZEN_TORCH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.FROZEN_WALL_TORCH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.ICE_PANE, RenderLayer.getTranslucent());
    }

}
