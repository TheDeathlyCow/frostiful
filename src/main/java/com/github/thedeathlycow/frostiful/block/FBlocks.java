package com.github.thedeathlycow.frostiful.block;

import com.github.thedeathlycow.frostiful.entity.FrostTippedArrowEntity;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import com.github.thedeathlycow.frostiful.item.FItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Position;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class FBlocks {

    public static final Block ICICLE = new IcicleBlock(FabricBlockSettings.of(Material.ICE, MapColor.CYAN).nonOpaque().sounds(BlockSoundGroup.GLASS).ticksRandomly().strength(0.5F).dynamicBounds().offsetType(AbstractBlock.OffsetType.XZ).requiresTool());
    public static final Block COLD_SUN_LICHEN = new SunLichenBlock(0, FabricBlockSettings.of(Material.REPLACEABLE_PLANT, MapColor.RED).noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN).ticksRandomly().nonOpaque().luminance((state) -> 0));
    public static final Block COOL_SUN_LICHEN = new SunLichenBlock(1, FabricBlockSettings.of(Material.REPLACEABLE_PLANT, MapColor.RED).noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN).ticksRandomly().nonOpaque().luminance((state) -> 2));
    public static final Block WARM_SUN_LICHEN = new SunLichenBlock(2, FabricBlockSettings.of(Material.REPLACEABLE_PLANT, MapColor.RED).noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN).ticksRandomly().nonOpaque().luminance((state) -> 4));
    public static final Block HOT_SUN_LICHEN = new SunLichenBlock(3, FabricBlockSettings.of(Material.REPLACEABLE_PLANT, MapColor.RED).noCollision().strength(0.2f).sounds(BlockSoundGroup.GLOW_LICHEN).ticksRandomly().nonOpaque().luminance((state) -> 6));

    public static final Block PACKED_SNOW = new PackedSnowBlock(FabricBlockSettings.of(Material.SNOW_LAYER)
            .strength(1.2f, 3.0f)
            .requiresTool()
            .sounds(BlockSoundGroup.SNOW)
            .blockVision((state, world, pos) -> {
                return state.get(PackedSnowBlock.LAYERS) >= PackedSnowBlock.MAX_LAYERS;
            })
    );

    public static final Block PACKED_SNOW_BLOCK = new Block(FabricBlockSettings.of(Material.SNOW_BLOCK)
            .requiresTool()
            .strength(1.5f, 6.0f)
            .sounds(BlockSoundGroup.SNOW)
    );

    public static final Block PACKED_SNOW_BRICKS = new Block(FabricBlockSettings.of(Material.SNOW_BLOCK)
            .requiresTool()
            .strength(1.5f, 6.0f)
            .sounds(BlockSoundGroup.SNOW)
    );

    public static final Block PACKED_SNOW_BRICK_STAIRS = new StairsBlock(
            PACKED_SNOW_BRICKS.getDefaultState(),
            FabricBlockSettings.copyOf(PACKED_SNOW_BRICKS)
    );

    public static final Block PACKED_SNOW_BRICK_SLAB = new SlabBlock(FabricBlockSettings.copyOf(PACKED_SNOW_BRICKS));

    public static final Block PACKED_SNOW_BRICK_WALL = new WallBlock(FabricBlockSettings.copyOf(PACKED_SNOW_BRICKS));

    public static void registerBlocks() {
        register("icicle", ICICLE);
        register("cold_sun_lichen", COLD_SUN_LICHEN);
        register("cool_sun_lichen", COOL_SUN_LICHEN);
        register("warm_sun_lichen", WARM_SUN_LICHEN);
        register("hot_sun_lichen", HOT_SUN_LICHEN);

        register("packed_snow_block", PACKED_SNOW_BLOCK);
        register("packed_snow_bricks", PACKED_SNOW_BRICKS);
        register("packed_snow_brick_stairs", PACKED_SNOW_BRICK_STAIRS);
        register("packed_snow_brick_slab", PACKED_SNOW_BRICK_SLAB);
        register("packed_snow_brick_wall", PACKED_SNOW_BRICK_WALL);

        DispenserBlock.registerBehavior(FItems.FROST_TIPPED_ARROW, new ProjectileDispenserBehavior() {
                    protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                        FrostTippedArrowEntity arrowEntity = new FrostTippedArrowEntity(world, position.getX(), position.getY(), position.getZ());
                        arrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
                        return arrowEntity;
                    }
                }
        );
    }

    private static void register(String id, Block block) {
        Registry.register(Registry.BLOCK, new Identifier(Frostiful.MODID, id), block);
    }
}
