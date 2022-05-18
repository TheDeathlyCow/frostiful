package com.github.thedeathlycow.frostiful.block;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FrostifulBlocks {

    public static final Block ICICLE = new IcicleBlock(FabricBlockSettings.of(Material.ICE, MapColor.CYAN).nonOpaque().sounds(BlockSoundGroup.GLASS).ticksRandomly().strength(0.5F).dynamicBounds().requiresTool());
    public static final Block SUN_LICHEN = new SunLichenBlock(FabricBlockSettings.of(Material.REPLACEABLE_PLANT, MapColor.RED).noCollision().strength(0.2F).sounds(BlockSoundGroup.GLOW_LICHEN).ticksRandomly().luminance(SunLichenBlock.getLuminanceSupplier()));

    public static void registerBlocks() {
        register("icicle", ICICLE);
        register("sun_lichen", SUN_LICHEN);
    }

    private static void register(String id, Block block) {
        Registry.register(Registry.BLOCK, new Identifier(Frostiful.MODID, id), block);
    }
}
