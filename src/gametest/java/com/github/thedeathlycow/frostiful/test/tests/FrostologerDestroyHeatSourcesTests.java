package com.github.thedeathlycow.frostiful.test.tests;

import com.github.thedeathlycow.frostiful.entity.frostologer.FrostologerEntity;
import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("unused")
public class FrostologerDestroyHeatSourcesTests {
    //region torch tests
    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void groundTorchIsFrozen(TestContext context) {
        runDestroyHeatSourceTest(context, Blocks.TORCH.getDefaultState(), FBlocks.FROZEN_TORCH);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void wallTorchIsFrozen(TestContext context) {
        runDestroyHeatSourceTest(context, Blocks.WALL_TORCH.getDefaultState(), FBlocks.FROZEN_WALL_TORCH);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void groundRedstoneTorchIsFrozen(TestContext context) {
        runDestroyHeatSourceTest(context, Blocks.REDSTONE_TORCH.getDefaultState(), FBlocks.FROZEN_TORCH);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void wallRedstoneTorchIsFrozen(TestContext context) {
        runDestroyHeatSourceTest(context, Blocks.REDSTONE_WALL_TORCH.getDefaultState(), FBlocks.FROZEN_WALL_TORCH);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void groundSoulTorchIsFrozen(TestContext context) {
        runDestroyHeatSourceTest(context, Blocks.SOUL_TORCH.getDefaultState(), FBlocks.FROZEN_TORCH);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void wallSoulTorchIsFrozen(TestContext context) {
        runDestroyHeatSourceTest(context, Blocks.SOUL_WALL_TORCH.getDefaultState(), FBlocks.FROZEN_WALL_TORCH);
    }
    //endregion

    //region lava tests

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void stillLavaBecomesObsidian(TestContext context) {
        runDestroyHeatSourceTest(context, Blocks.LAVA.getDefaultState(), Blocks.OBSIDIAN);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void flowingLavaBecomesAir(TestContext context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.LAVA.getDefaultState()
                        .with(FluidBlock.LEVEL, 10),
                Blocks.AIR
        );
    }

    //endregion

    //region full block tests

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void glowstoneBecomesIce(TestContext context) {
        runDestroyHeatSourceTest(context, Blocks.GLOWSTONE.getDefaultState(), Blocks.ICE);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void shroomlightBecomesIce(TestContext context) {
        runDestroyHeatSourceTest(context, Blocks.SHROOMLIGHT.getDefaultState(), Blocks.ICE);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void redstoneLampBecomesIce(TestContext context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.REDSTONE_LAMP.getDefaultState()
                        .with(RedstoneLampBlock.LIT, true),
                Blocks.ICE
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void seaLanternBecomesIce(TestContext context) {
        runDestroyHeatSourceTest(context, Blocks.SEA_LANTERN.getDefaultState(), Blocks.ICE);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void furnaceBecomesIce(TestContext context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.FURNACE.getDefaultState()
                        .with(FurnaceBlock.LIT, true),
                Blocks.ICE
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void respawnAnchorBecomesIce(TestContext context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.RESPAWN_ANCHOR.getDefaultState()
                        .with(RespawnAnchorBlock.CHARGES, 4),
                Blocks.ICE
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void jackOLanternBecomesIce(TestContext context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.JACK_O_LANTERN.getDefaultState(),
                Blocks.ICE
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void redstoneOreBecomesIce(TestContext context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.REDSTONE_ORE.getDefaultState()
                        .with(RedstoneOreBlock.LIT, true),
                Blocks.ICE
        );
    }

    //endregion

    //region protected blocks tests

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void netherPortalIsUnaffectedByFrostologer(TestContext context) {
        runDestroyHeatSourceTest(context, Blocks.NETHER_PORTAL.getDefaultState(), Blocks.NETHER_PORTAL);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void endPortalIsUnaffectedByFrostologer(TestContext context) {
        runDestroyHeatSourceTest(context, Blocks.END_PORTAL.getDefaultState(), Blocks.END_PORTAL);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void endGatewayIsUnaffectedByFrostologer(TestContext context) {
        runDestroyHeatSourceTest(context, Blocks.END_GATEWAY.getDefaultState(), Blocks.END_GATEWAY);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void beaconIsUnaffectedByFrostologer(TestContext context) {
        runDestroyHeatSourceTest(context, Blocks.BEACON.getDefaultState(), Blocks.BEACON);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void vaultIsUnaffectedByFrostologer(TestContext context) {
        runDestroyHeatSourceTest(context, Blocks.VAULT.getDefaultState(), Blocks.VAULT);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void trialSpawnerIsUnaffectedByFrostologer(TestContext context) {
        runDestroyHeatSourceTest(context, Blocks.TRIAL_SPAWNER.getDefaultState(), Blocks.TRIAL_SPAWNER);
    }

    //endregion

    //region water logged tests

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void waterloggedSeaPickleBecomesAir(TestContext context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.SEA_PICKLE.getDefaultState()
                        .with(SeaPickleBlock.WATERLOGGED, true)
                        .with(SeaPickleBlock.PICKLES, 4),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void waterloggedEnderChestBecomesAir(TestContext context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.ENDER_CHEST.getDefaultState()
                        .with(EnderChestBlock.WATERLOGGED, true),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void waterloggedLanternBecomesAir(TestContext context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.LANTERN.getDefaultState()
                        .with(EnderChestBlock.WATERLOGGED, true),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void waterloggedAmethystClusterBecomesAir(TestContext context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.AMETHYST_CLUSTER.getDefaultState()
                        .with(AmethystClusterBlock.WATERLOGGED, true),
                Blocks.AIR
        );
    }

    // yes this is technically a possible block state
    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void waterloggedLitCandleBecomesAir(TestContext context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.CANDLE.getDefaultState()
                        .with(CandleBlock.WATERLOGGED, true)
                        .with(CandleBlock.LIT, true),
                Blocks.AIR
        );
    }

    // yes this is technically a possible block state
    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void waterlogged_lit_campfire_becomes_air(TestContext context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.CAMPFIRE.getDefaultState()
                        .with(CampfireBlock.WATERLOGGED, true)
                        .with(CampfireBlock.LIT, true),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void waterloggedHotSunLichenBecomesAir(TestContext context) {
        runDestroyHeatSourceTest(
                context,
                FBlocks.HOT_SUN_LICHEN.getDefaultState()
                        .with(AmethystClusterBlock.WATERLOGGED, true),
                Blocks.AIR
        );
    }

    //endregion

    //region small blocks

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void endRodBecomesAir(TestContext context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.END_ROD.getDefaultState(),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void enderChestBecomesAir(TestContext context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.ENDER_CHEST.getDefaultState(),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void lanternBecomesAir(TestContext context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.LANTERN.getDefaultState(),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void amethystClusterBecomesAir(TestContext context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.AMETHYST_CLUSTER.getDefaultState(),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void litCandleBecomesAir(TestContext context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.CANDLE.getDefaultState()
                        .with(CandleBlock.LIT, true),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void litCampfireBecomesAir(TestContext context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.CAMPFIRE.getDefaultState()
                        .with(CampfireBlock.LIT, true),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void hotSunLichenBecomesAir(TestContext context) {
        runDestroyHeatSourceTest(
                context,
                FBlocks.HOT_SUN_LICHEN.getDefaultState(),
                Blocks.AIR
        );
    }

    //endregion

    private static void runDestroyHeatSourceTest(TestContext context, BlockState toPlace, Block blockAtEnd) {
        ServerWorld serverWorld = context.getWorld();
        BlockPos pos = new BlockPos(1, 1, 1);

        serverWorld.setBlockState(pos, toPlace);

        FrostologerEntity frostologer = context.spawnEntity(FEntityTypes.FROSTOLOGER, pos.add(1, 0, 1));
        frostologer.setInvulnerable(true);
        frostologer.setAiDisabled(true);
        frostologer.destroyHeatSource(serverWorld, toPlace, context.getAbsolutePos(pos));

        context.expectBlockAtEnd(blockAtEnd, pos);
    }

}
