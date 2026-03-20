package com.github.thedeathlycow.frostiful.client.registry;

import com.github.thedeathlycow.frostiful.registry.FBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ChunkSectionLayerMap;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;

@Environment(EnvType.CLIENT)
public class FCutouts {

    public static void initialize() {
        ChunkSectionLayerMap.putBlock(FBlocks.ICICLE, ChunkSectionLayer.CUTOUT);
        ChunkSectionLayerMap.putBlock(FBlocks.COLD_SUN_LICHEN, ChunkSectionLayer.CUTOUT);
        ChunkSectionLayerMap.putBlock(FBlocks.COOL_SUN_LICHEN, ChunkSectionLayer.CUTOUT);
        ChunkSectionLayerMap.putBlock(FBlocks.WARM_SUN_LICHEN, ChunkSectionLayer.CUTOUT);
        ChunkSectionLayerMap.putBlock(FBlocks.HOT_SUN_LICHEN, ChunkSectionLayer.CUTOUT);
        ChunkSectionLayerMap.putBlock(FBlocks.FROZEN_TORCH, ChunkSectionLayer.CUTOUT);
        ChunkSectionLayerMap.putBlock(FBlocks.FROZEN_WALL_TORCH, ChunkSectionLayer.CUTOUT);
        ChunkSectionLayerMap.putBlock(FBlocks.ICE_PANE, ChunkSectionLayer.TRANSLUCENT);
        ChunkSectionLayerMap.putBlock(FBlocks.ICY_TRIAL_SPAWNER, ChunkSectionLayer.CUTOUT);
        ChunkSectionLayerMap.putBlock(FBlocks.ICY_VAULT, ChunkSectionLayer.CUTOUT);
        ChunkSectionLayerMap.putBlock(FBlocks.BRITTLE_ICE, ChunkSectionLayer.TRANSLUCENT);
    }

    private FCutouts() {

    }
}
