package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.block.*;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;

public class FBlocks {

    public static final Block ICICLE = register(
            "icicle",
            settings -> new IcicleBlock(
                    settings
                            .mapColor(MapColor.COLOR_CYAN)
                            .noOcclusion()
                            .sound(SoundType.GLASS)
                            .randomTicks()
                            .strength(0.5F)
                            .pushReaction(PushReaction.DESTROY)
                            .dynamicShape()
                            .offsetType(BlockBehaviour.OffsetType.XZ)
                            .requiresCorrectToolForDrops()
            )
    );

    public static final Block COLD_SUN_LICHEN = register(
            "cold_sun_lichen",
            settings -> new SunLichenBlock(
                    SunLichenBlock.COLD_LEVEL,
                    settings
                            .replaceable()
                            .mapColor(MapColor.COLOR_RED)
                            .pushReaction(PushReaction.DESTROY)
                            .noCollission()
                            .strength(0.2f)
                            .sound(SoundType.GLOW_LICHEN)
                            .randomTicks()
                            .noOcclusion()
                            .lightLevel(state -> 0)
            )
    );
    public static final Block COOL_SUN_LICHEN = register(
            "cool_sun_lichen",
            settings -> new SunLichenBlock(
                    SunLichenBlock.COOL_LEVEL,
                    settings
                            .replaceable()
                            .mapColor(MapColor.COLOR_RED)
                            .pushReaction(PushReaction.DESTROY)
                            .noCollission()
                            .strength(0.2f)
                            .sound(SoundType.GLOW_LICHEN)
                            .randomTicks()
                            .noOcclusion()
                            .lightLevel(state -> 2)
            )
    );
    public static final Block WARM_SUN_LICHEN = register(
            "warm_sun_lichen",
            settings -> new SunLichenBlock(
                    SunLichenBlock.WARM_LEVEL,
                    settings
                            .replaceable()
                            .mapColor(MapColor.COLOR_RED)
                            .pushReaction(PushReaction.DESTROY)
                            .noCollission()
                            .strength(0.2f)
                            .sound(SoundType.GLOW_LICHEN)
                            .randomTicks()
                            .noOcclusion()
                            .lightLevel(state -> 4)
            )
    );
    public static final Block HOT_SUN_LICHEN = register(
            "hot_sun_lichen",
            settings -> new SunLichenBlock(
                    SunLichenBlock.HOT_LEVEL,
                    settings
                            .replaceable()
                            .mapColor(MapColor.COLOR_RED)
                            .pushReaction(PushReaction.DESTROY)
                            .noCollission()
                            .strength(0.2f)
                            .sound(SoundType.GLOW_LICHEN)
                            .randomTicks()
                            .noOcclusion()
                            .lightLevel(state -> 6)
            )
    );

    public static final Block FROZEN_TORCH = register(
            "frozen_torch",
            settings -> new FrozenTorchBlock(
                    settings
                            .noCollission()
                            .instabreak()
                            .pushReaction(PushReaction.DESTROY)
                            .sound(SoundType.WOOD)
            )
    );

    public static final Block FROZEN_WALL_TORCH = register(
            "frozen_wall_torch",
            settings -> new FrozenWallTorchBlock(
                    settings
                            .dropsLike(FROZEN_TORCH)
            ),
            BlockBehaviour.Properties.ofFullCopy(FROZEN_TORCH)
    );

    public static final Block PACKED_SNOW = register(
            "packed_snow",
            settings -> new PackedSnowBlock(
                    settings
                            .mapColor(MapColor.SNOW)
                            .replaceable()
                            .forceSolidOff()
                            .strength(1.2f, 3.0f)
                            .requiresCorrectToolForDrops()
                            .sound(FBlockSoundGroups.PACKED_SNOW)
                            .randomTicks()
                            .isViewBlocking((state, world, pos) -> {
                                return state.getValue(PackedSnowBlock.LAYERS) >= PackedSnowBlock.MAX_LAYERS;
                            })
                            .pushReaction(PushReaction.DESTROY)
            )
    );

    public static final Block PACKED_SNOW_BLOCK = register(
            "packed_snow_block",
            settings -> new Block(
                    settings
                            .mapColor(MapColor.WOOL)
                            .requiresCorrectToolForDrops()
                            .strength(1.5f, 6.0f)
                            .sound(FBlockSoundGroups.PACKED_SNOW)
            )
    );

    public static final Block PACKED_SNOW_BRICKS = register(
            "packed_snow_bricks",
            settings -> new Block(
                    settings
                            .mapColor(MapColor.WOOL)
                            .requiresCorrectToolForDrops()
                            .strength(1.5f, 6.0f)
                            .sound(FBlockSoundGroups.PACKED_SNOW)
            )
    );

    public static final Block PACKED_SNOW_BRICK_STAIRS = register(
            "packed_snow_brick_stairs",
            settings -> new StairBlock(
                    PACKED_SNOW_BRICKS.defaultBlockState(),
                    settings
            ),
            BlockBehaviour.Properties.ofFullCopy(PACKED_SNOW_BRICKS)
    );

    public static final Block PACKED_SNOW_BRICK_SLAB = register(
            "packed_snow_brick_slab",
            settings -> new SlabBlock(settings),
            BlockBehaviour.Properties.ofFullCopy(PACKED_SNOW_BRICKS)
    );

    public static final Block PACKED_SNOW_BRICK_WALL = register(
            "packed_snow_brick_wall",
            settings -> new WallBlock(settings),
            BlockBehaviour.Properties.ofFullCopy(PACKED_SNOW_BRICKS)
    );

    public static final Block ICE_PANE = register(
            "ice_pane",
            settings -> new IcePaneBlock(
                    settings
                            .mapColor(MapColor.ICE)
                            .strength(0.5f)
                            .randomTicks()
                            .friction(0.98f)
                            .sound(SoundType.GLASS)
                            .noOcclusion()
                            .isRedstoneConductor((state, world, pos) -> false)
            )
    );

    public static final Block CUT_PACKED_ICE = register(
            "cut_packed_ice",
            settings -> new Block(
                    settings
                            .mapColor(MapColor.ICE)
                            .instrument(NoteBlockInstrument.CHIME)
                            .friction(0.98f)
                            .strength(0.75f)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.GLASS)
            )
    );

    public static final Block CUT_PACKED_ICE_STAIRS = register(
            "cut_packed_ice_stairs",
            settings -> new StairBlock(
                    CUT_PACKED_ICE.defaultBlockState(),
                    settings
            ),
            BlockBehaviour.Properties.ofFullCopy(CUT_PACKED_ICE)
    );

    public static final Block CUT_PACKED_ICE_SLAB = register(
            "cut_packed_ice_slab",
            settings -> new SlabBlock(settings),
            BlockBehaviour.Properties.ofFullCopy(CUT_PACKED_ICE)
    );

    public static final Block CUT_PACKED_ICE_WALL = register(
            "cut_packed_ice_wall",
            settings -> new WallBlock(settings),
            BlockBehaviour.Properties.ofFullCopy(CUT_PACKED_ICE)
    );

    public static final Block CUT_BLUE_ICE = register(
            "cut_blue_ice",
            settings -> new Block(
                    settings
                            .mapColor(MapColor.ICE)
                            .friction(0.989f)
                            .strength(2.8f)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.GLASS)
            )
    );

    public static final Block CUT_BLUE_ICE_STAIRS = register(
            "cut_blue_ice_stairs",
            settings -> new StairBlock(
                    CUT_BLUE_ICE.defaultBlockState(),
                    settings
            ),
            BlockBehaviour.Properties.ofFullCopy(CUT_BLUE_ICE)
    );

    public static final Block CUT_BLUE_ICE_SLAB = register(
            "cut_blue_ice_slab",
            settings -> new SlabBlock(settings),
            BlockBehaviour.Properties.ofFullCopy(CUT_BLUE_ICE)
    );

    public static final Block CUT_BLUE_ICE_WALL = register(
            "cut_blue_ice_wall",
            settings -> new WallBlock(settings),
            BlockBehaviour.Properties.ofFullCopy(CUT_BLUE_ICE)
    );

    public static final Block ICY_TRIAL_SPAWNER = register(
            "icy_trial_spawner",
            settings -> new TrialSpawnerBlock(
                    settings
                            .mapColor(MapColor.WARPED_STEM)
                            .instrument(NoteBlockInstrument.BASEDRUM)
                            .lightLevel(state -> state.getValue(TrialSpawnerBlock.STATE).lightLevel())
                            .strength(50.0f)
                            .sound(SoundType.TRIAL_SPAWNER)
                            .isViewBlocking((state, level, pos) -> false)
                            .noOcclusion()
            )
    );
    public static final Block ICY_VAULT = register(
            "icy_vault",
            settings -> new VaultBlock(
                    settings
                            .mapColor(MapColor.WARPED_STEM)
                            .instrument(NoteBlockInstrument.BASEDRUM)
                            .noOcclusion()
                            .sound(SoundType.VAULT)
                            .lightLevel(state -> state.getValue(VaultBlock.STATE).lightLevel() * 5 / 6)
                            .strength(50.0f)
                            .isViewBlocking((state, level, pos) -> false)
            )
    );

    public static final Block BRITTLE_ICE = register(
            "brittle_ice",
            settings -> new BrittleIceBlock(
                    settings
                            .mapColor(MapColor.ICE)
                            .friction(0.98f)
                            .randomTicks()
                            .strength(0.5f)
                            .sound(SoundType.GLASS)
                            .noOcclusion()
                            .isRedstoneConductor((state, level, pos) -> false)
            )
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful blocks");
        DispenserBlock.registerProjectileBehavior(FItems.GLACIAL_ARROW);
        DispenserBlock.registerProjectileBehavior(FItems.PACKED_SNOWBALL);
        UseBlockCallback.EVENT.register(new CampfireUseEventListener());
        ((FabricBlockEntityType)BlockEntityType.TRIAL_SPAWNER).addSupportedBlock(ICY_TRIAL_SPAWNER);
        ((FabricBlockEntityType)BlockEntityType.VAULT).addSupportedBlock(ICY_VAULT);
    }

    private static Block register(String id, Function<BlockBehaviour.Properties, Block> blockFactory) {
        return register(id, blockFactory, BlockBehaviour.Properties.of());
    }

    private static Block register(String id, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties settings) {
        Block block = blockFactory.apply(settings);
        return Registry.register(BuiltInRegistries.BLOCK, Frostiful.id(id), block);
    }

    private FBlocks() {

    }

}
