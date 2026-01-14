package com.github.thedeathlycow.frostiful.test.tests;

import com.github.thedeathlycow.frostiful.entity.frostologer.FrostologerEntity;
import com.github.thedeathlycow.frostiful.registry.FBlocks;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("unused")
public class FrostologerDestroyHeatSourcesTests {
    //region torch tests
    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void groundTorchIsFrozen(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.TORCH.defaultBlockState(), FBlocks.FROZEN_TORCH);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void wallTorchIsFrozen(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.WALL_TORCH.defaultBlockState(), FBlocks.FROZEN_WALL_TORCH);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void groundRedstoneTorchIsFrozen(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.REDSTONE_TORCH.defaultBlockState(), FBlocks.FROZEN_TORCH);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void wallRedstoneTorchIsFrozen(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.REDSTONE_WALL_TORCH.defaultBlockState(), FBlocks.FROZEN_WALL_TORCH);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void groundSoulTorchIsFrozen(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.SOUL_TORCH.defaultBlockState(), FBlocks.FROZEN_TORCH);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void wallSoulTorchIsFrozen(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.SOUL_WALL_TORCH.defaultBlockState(), FBlocks.FROZEN_WALL_TORCH);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void groundCopperTorchIsFrozen(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.COPPER_TORCH.defaultBlockState(), FBlocks.FROZEN_TORCH);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void wallCopperTorchIsFrozen(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.COPPER_WALL_TORCH.defaultBlockState(), FBlocks.FROZEN_WALL_TORCH);
    }
    //endregion

    //region lava tests

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void stillLavaBecomesObsidian(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.LAVA.defaultBlockState(), Blocks.OBSIDIAN);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void flowingLavaBecomesAir(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.LAVA.defaultBlockState()
                        .setValue(LiquidBlock.LEVEL, 10),
                Blocks.AIR
        );
    }

    //endregion

    //region full block tests

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void glowstoneBecomesIce(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.GLOWSTONE.defaultBlockState(), Blocks.ICE);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void shroomlightBecomesIce(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.SHROOMLIGHT.defaultBlockState(), Blocks.ICE);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void redstoneLampBecomesIce(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.REDSTONE_LAMP.defaultBlockState()
                        .setValue(RedstoneLampBlock.LIT, true),
                Blocks.ICE
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void seaLanternBecomesIce(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.SEA_LANTERN.defaultBlockState(), Blocks.ICE);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void furnaceBecomesIce(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.FURNACE.defaultBlockState()
                        .setValue(FurnaceBlock.LIT, true),
                Blocks.ICE
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void respawnAnchorBecomesIce(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.RESPAWN_ANCHOR.defaultBlockState()
                        .setValue(RespawnAnchorBlock.CHARGE, 4),
                Blocks.ICE
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void jackOLanternBecomesIce(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.JACK_O_LANTERN.defaultBlockState(),
                Blocks.ICE
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void redstoneOreBecomesIce(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.REDSTONE_ORE.defaultBlockState()
                        .setValue(RedStoneOreBlock.LIT, true),
                Blocks.ICE
        );
    }

    //endregion

    //region protected blocks tests

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void netherPortalIsUnaffectedByFrostologer(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.NETHER_PORTAL.defaultBlockState(), Blocks.NETHER_PORTAL);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void endPortalIsUnaffectedByFrostologer(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.END_PORTAL.defaultBlockState(), Blocks.END_PORTAL);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void endGatewayIsUnaffectedByFrostologer(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.END_GATEWAY.defaultBlockState(), Blocks.END_GATEWAY);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void beaconIsUnaffectedByFrostologer(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.BEACON.defaultBlockState(), Blocks.BEACON);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void vaultIsUnaffectedByFrostologer(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.VAULT.defaultBlockState(), Blocks.VAULT);
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void trialSpawnerIsUnaffectedByFrostologer(GameTestHelper context) {
        runDestroyHeatSourceTest(context, Blocks.TRIAL_SPAWNER.defaultBlockState(), Blocks.TRIAL_SPAWNER);
    }

    //endregion

    //region water logged tests

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void waterloggedSeaPickleBecomesAir(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.SEA_PICKLE.defaultBlockState()
                        .setValue(SeaPickleBlock.WATERLOGGED, true)
                        .setValue(SeaPickleBlock.PICKLES, 4),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void waterloggedEnderChestBecomesAir(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.ENDER_CHEST.defaultBlockState()
                        .setValue(EnderChestBlock.WATERLOGGED, true),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void waterloggedLanternBecomesAir(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.LANTERN.defaultBlockState()
                        .setValue(EnderChestBlock.WATERLOGGED, true),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void waterloggedAmethystClusterBecomesAir(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.AMETHYST_CLUSTER.defaultBlockState()
                        .setValue(AmethystClusterBlock.WATERLOGGED, true),
                Blocks.AIR
        );
    }

    // yes this is technically a possible block state
    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void waterloggedLitCandleBecomesAir(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.CANDLE.defaultBlockState()
                        .setValue(CandleBlock.WATERLOGGED, true)
                        .setValue(CandleBlock.LIT, true),
                Blocks.AIR
        );
    }

    // yes this is technically a possible block state
    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void waterlogged_lit_campfire_becomes_air(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.CAMPFIRE.defaultBlockState()
                        .setValue(CampfireBlock.WATERLOGGED, true)
                        .setValue(CampfireBlock.LIT, true),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void waterloggedHotSunLichenBecomesAir(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                FBlocks.HOT_SUN_LICHEN.defaultBlockState()
                        .setValue(AmethystClusterBlock.WATERLOGGED, true),
                Blocks.AIR
        );
    }

    //endregion

    //region small blocks

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void endRodBecomesAir(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.END_ROD.defaultBlockState(),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void enderChestBecomesAir(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.ENDER_CHEST.defaultBlockState(),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void lanternBecomesAir(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.LANTERN.defaultBlockState(),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void amethystClusterBecomesAir(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.AMETHYST_CLUSTER.defaultBlockState(),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void litCandleBecomesAir(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.CANDLE.defaultBlockState()
                        .setValue(CandleBlock.LIT, true),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void litCampfireBecomesAir(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.CAMPFIRE.defaultBlockState()
                        .setValue(CampfireBlock.LIT, true),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void hotSunLichenBecomesAir(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                FBlocks.HOT_SUN_LICHEN.defaultBlockState(),
                Blocks.AIR
        );
    }

    @GameTest(structure = "frostiful-test:frostologer_heat_source_test_template")
    public void cryingObsidianBecomesObsidian(GameTestHelper context) {
        runDestroyHeatSourceTest(
                context,
                Blocks.CRYING_OBSIDIAN.defaultBlockState(),
                Blocks.OBSIDIAN
        );
    }

    //endregion

    private static void runDestroyHeatSourceTest(GameTestHelper context, BlockState toPlace, Block blockAtEnd) {
        ServerLevel serverWorld = context.getLevel();
        BlockPos pos = new BlockPos(1, 1, 1);

        serverWorld.setBlockAndUpdate(context.absolutePos(pos), toPlace);

        FrostologerEntity frostologer = context.spawn(FEntityTypes.FROSTOLOGER, pos.offset(1, 0, 1));
        frostologer.setInvulnerable(true);
        frostologer.setNoAi(true);
        frostologer.tryDestroyHeatSource(serverWorld, toPlace, context.absolutePos(pos));

        context.succeedWhenBlockPresent(blockAtEnd, pos);
    }

}
