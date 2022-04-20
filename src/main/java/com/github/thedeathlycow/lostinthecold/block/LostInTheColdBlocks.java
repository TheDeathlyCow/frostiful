package com.github.thedeathlycow.lostinthecold.block;

import com.github.thedeathlycow.lostinthecold.init.LostInTheCold;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class LostInTheColdBlocks {

    public static final Block ICICLE_BLOCK = register("icicle_block", new IcicleBlock(FabricBlockSettings.of(Material.ICE, MapColor.CYAN).ticksRandomly().strength(0.5F).sounds(BlockSoundGroup.GLASS).dynamicBounds()));

    private static Block register(String id, Block item) {
        return Registry.register(Registry.BLOCK, new Identifier(LostInTheCold.MODID, id), item);
    }
}
