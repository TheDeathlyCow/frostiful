package com.github.thedeathlycow.frostiful.test.frostologer;

import com.github.thedeathlycow.frostiful.entity.frostologer.FrostologerEntity;
import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.test.FrostifulGameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@SuppressWarnings("unused")
@GameTestHolder(FrostifulGameTest.MODID)
@PrefixGameTestTemplate(false)
public class FrostologerDestroyHeatSourcesTests {
    //region torch tests
    @GameTest(template = "frostologer_heat_source_test_template")
    public void ground_torch_is_frozen(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.TORCH.defaultBlockState(), FBlocks.FROZEN_TORCH);
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void wall_torch_is_frozen(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.WALL_TORCH.defaultBlockState(), FBlocks.FROZEN_WALL_TORCH);
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void ground_redstone_torch_is_frozen(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.REDSTONE_TORCH.defaultBlockState(), FBlocks.FROZEN_TORCH);
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void wall_redstone_torch_is_frozen(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.REDSTONE_WALL_TORCH.defaultBlockState(), FBlocks.FROZEN_WALL_TORCH);
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void ground_soul_torch_is_frozen(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.SOUL_TORCH.defaultBlockState(), FBlocks.FROZEN_TORCH);
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void wall_soul_torch_is_frozen(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.SOUL_WALL_TORCH.defaultBlockState(), FBlocks.FROZEN_WALL_TORCH);
    }
    //endregion

    //region lava tests

    @GameTest(template = "frostologer_heat_source_test_template")
    public void still_lava_becomes_obsidian(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.LAVA.defaultBlockState(), Blocks.OBSIDIAN);
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void flowing_lava_becomes_air(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.LAVA.defaultBlockState()
                        .setValue(LiquidBlock.LEVEL, 10),
                Blocks.AIR
        );
    }

    //endregion

    //region full block tests

    @GameTest(template = "frostologer_heat_source_test_template")
    public void glowstone_becomes_ice(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.GLOWSTONE.defaultBlockState(), Blocks.ICE);
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void shroomlight_becomes_ice(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.SHROOMLIGHT.defaultBlockState(), Blocks.ICE);
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void redstone_lamp_becomes_ice(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.REDSTONE_LAMP.defaultBlockState()
                        .setValue(RedstoneLampBlock.LIT, true),
                Blocks.ICE
        );
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void sea_lantern_becomes_ice(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.SEA_LANTERN.defaultBlockState(), Blocks.ICE);
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void furnace_becomes_ice(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.FURNACE.defaultBlockState()
                        .setValue(FurnaceBlock.LIT, true),
                Blocks.ICE
        );
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void respawn_anchor_becomes_ice(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.RESPAWN_ANCHOR.defaultBlockState()
                        .setValue(RespawnAnchorBlock.CHARGE, 4),
                Blocks.ICE
        );
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void jack_o_lantern_becomes_ice(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.JACK_O_LANTERN.defaultBlockState(),
                Blocks.ICE
        );
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void redstone_ore_becomes_ice(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.REDSTONE_ORE.defaultBlockState()
                        .setValue(RedStoneOreBlock.LIT, true),
                Blocks.ICE
        );
    }

    //endregion

    //region protected blocks tests

    @GameTest(template = "frostologer_heat_source_test_template")
    public void nether_portal_is_unaffected_by_frostologer(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.NETHER_PORTAL.defaultBlockState(), Blocks.NETHER_PORTAL);
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void end_portal_is_unaffected_by_frostologer(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.END_PORTAL.defaultBlockState(), Blocks.END_PORTAL);
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void end_gateway_is_unaffected_by_frostologer(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.END_GATEWAY.defaultBlockState(), Blocks.END_GATEWAY);
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void beacon_is_unaffected_by_frostologer(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.BEACON.defaultBlockState(), Blocks.BEACON);
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void vault_is_unaffected_by_frostologer(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.VAULT.defaultBlockState(), Blocks.VAULT);
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void trial_spawner_is_unaffected_by_frostologer(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.TRIAL_SPAWNER.defaultBlockState(), Blocks.TRIAL_SPAWNER);
    }

    //endregion

    //region water logged tests

    @GameTest(template = "frostologer_heat_source_test_template")
    public void waterlogged_sea_pickle_becomes_air(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.SEA_PICKLE.defaultBlockState()
                        .setValue(SeaPickleBlock.WATERLOGGED, true)
                        .setValue(SeaPickleBlock.PICKLES, 4),
                Blocks.AIR
        );
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void waterlogged_ender_chest_becomes_air(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.ENDER_CHEST.defaultBlockState()
                        .setValue(EnderChestBlock.WATERLOGGED, true),
                Blocks.AIR
        );
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void waterlogged_lantern_becomes_air(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.LANTERN.defaultBlockState()
                        .setValue(EnderChestBlock.WATERLOGGED, true),
                Blocks.AIR
        );
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void waterlogged_amethyst_cluster_becomes_air(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.AMETHYST_CLUSTER.defaultBlockState()
                        .setValue(AmethystClusterBlock.WATERLOGGED, true),
                Blocks.AIR
        );
    }

    // yes this is technically a possible block state
    @GameTest(template = "frostologer_heat_source_test_template")
    public void waterlogged_lit_candle_becomes_air(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.CANDLE.defaultBlockState()
                        .setValue(CandleBlock.WATERLOGGED, true)
                        .setValue(CandleBlock.LIT, true),
                Blocks.AIR
        );
    }

    // yes this is technically a possible block state
    @GameTest(template = "frostologer_heat_source_test_template")
    public void waterlogged_lit_campfire_becomes_air(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.CAMPFIRE.defaultBlockState()
                        .setValue(CampfireBlock.WATERLOGGED, true)
                        .setValue(CampfireBlock.LIT, true),
                Blocks.AIR
        );
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void waterlogged_hot_sun_lichen_becomes_air(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                FBlocks.HOT_SUN_LICHEN.defaultBlockState()
                        .setValue(AmethystClusterBlock.WATERLOGGED, true),
                Blocks.AIR
        );
    }

    //endregion

    //region small blocks

    @GameTest(template = "frostologer_heat_source_test_template")
    public void end_rod_becomes_air(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.END_ROD.defaultBlockState(),
                Blocks.AIR
        );
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void ender_chest_becomes_air(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.ENDER_CHEST.defaultBlockState(),
                Blocks.AIR
        );
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void lantern_becomes_air(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.LANTERN.defaultBlockState(),
                Blocks.AIR
        );
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void amethyst_cluster_becomes_air(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.AMETHYST_CLUSTER.defaultBlockState(),
                Blocks.AIR
        );
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void lit_candle_becomes_air(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.CANDLE.defaultBlockState()
                        .setValue(CandleBlock.LIT, true),
                Blocks.AIR
        );
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void lit_campfire_becomes_air(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.CAMPFIRE.defaultBlockState()
                        .setValue(CampfireBlock.LIT, true),
                Blocks.AIR
        );
    }

    @GameTest(template = "frostologer_heat_source_test_template")
    public void hot_sun_lichen_becomes_air(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                FBlocks.HOT_SUN_LICHEN.defaultBlockState(),
                Blocks.AIR
        );
    }

    //endregion

    private static void runDestroyHeatSourceTest(GameTestHelper context, BlockState toPlace, Block blockAtEnd) {
        ServerLevel serverWorld = context.getLevel();
        BlockPos pos = new BlockPos(1, 2, 1);

        serverWorld.setBlockAndUpdate(pos, toPlace);

        FrostologerEntity frostologer = context.spawn(FEntityTypes.FROSTOLOGER, pos.offset(1, 0, 1));
        frostologer.setInvulnerable(true);
        frostologer.setNoAi(true);
        frostologer.destroyHeatSource(serverWorld, toPlace, context.absolutePos(pos));

        context.succeedWhenBlockPresent(blockAtEnd, pos);
    }

}
