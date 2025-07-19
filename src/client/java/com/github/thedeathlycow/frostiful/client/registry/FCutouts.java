package com.github.thedeathlycow.frostiful.client.registry;

import com.github.thedeathlycow.frostiful.registry.FBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.render.BlockRenderLayer;

@Environment(EnvType.CLIENT)
public class FCutouts {

    public static void initialize() {
        BlockRenderLayerMap.putBlock(FBlocks.ICICLE, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(FBlocks.COLD_SUN_LICHEN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(FBlocks.COOL_SUN_LICHEN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(FBlocks.WARM_SUN_LICHEN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(FBlocks.HOT_SUN_LICHEN, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(FBlocks.FROZEN_TORCH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(FBlocks.FROZEN_WALL_TORCH, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(FBlocks.ICE_PANE, BlockRenderLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(FBlocks.ICY_TRIAL_SPAWNER, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(FBlocks.ICY_VAULT, BlockRenderLayer.CUTOUT);
        BlockRenderLayerMap.putBlock(FBlocks.BRITTLE_ICE, BlockRenderLayer.TRANSLUCENT);
    }

    private FCutouts() {

    }
}
