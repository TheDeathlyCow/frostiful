package com.github.thedeathlycow.frostiful.client.registry;

import com.github.thedeathlycow.frostiful.registry.FBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

@Environment(EnvType.CLIENT)
public class FCutouts {

    public static void initialize() {
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.ICICLE, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.COLD_SUN_LICHEN, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.COOL_SUN_LICHEN, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.WARM_SUN_LICHEN, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.HOT_SUN_LICHEN, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.FROZEN_TORCH, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.FROZEN_WALL_TORCH, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.ICE_PANE, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.ICY_TRIAL_SPAWNER, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.ICY_VAULT, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FBlocks.BRITTLE_ICE, RenderType.translucent());
    }

    private FCutouts() {

    }
}
