package com.github.thedeathlycow.frostiful.block;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FrostifulBlocks {

    public static final Block ICICLE = new Icicle(FabricBlockSettings.of(Material.ICE, MapColor.CYAN).nonOpaque().sounds(BlockSoundGroup.GLASS).ticksRandomly().strength(0.5F).dynamicBounds());

    public static void registerBlocks() {
        register("icicle", ICICLE);
    }

    private static void register(String id, Block item) {
        Registry.register(Registry.BLOCK, new Identifier(Frostiful.MODID, id), item);
    }
}
