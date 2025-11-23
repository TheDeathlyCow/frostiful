package com.github.thedeathlycow.frostiful.test.frostologer;

import com.github.thedeathlycow.frostiful.entity.ChillagerEntity;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

@SuppressWarnings("unused")
public class ChillagerDropTests {
    @GameTest(template = FabricGameTest.EMPTY_STRUCTURE)
    public void regular_chillager_does_not_drop_ominous_bottle(GameTestHelper context) {
        Level world = context.getLevel();
        DamageSources damageSources = world.damageSources();

        ChillagerEntity chillager = context.spawn(FEntityTypes.CHILLAGER, BlockPos.ZERO);

        chillager.damage(damageSources.genericKill(), Float.MAX_VALUE);

        context.assertItemEntityNotPresent(Items.OMINOUS_BOTTLE, BlockPos.ZERO, 3f);

        context.succeed();
    }

    @GameTest(template = FabricGameTest.EMPTY_STRUCTURE)
    public void chillager_captain_drops_ominous_bottle(GameTestHelper context) {
        Level world = context.getLevel();
        DamageSources damageSources = world.damageSources();

        ChillagerEntity chillager = context.spawn(FEntityTypes.CHILLAGER, BlockPos.ZERO);
        chillager.setPatrolLeader(true);
        chillager.equipStack(
                EquipmentSlot.HEAD,
                Raid.getLeaderBannerInstance(chillager.getRegistryManager().getWrapperOrThrow(Registries.BANNER_PATTERN))
        );

        context.assertTrue(chillager.isCaptain(), "Chillager is not a captain!");

        chillager.damage(damageSources.genericKill(), Float.MAX_VALUE);

        context.assertItemEntityPresent(Items.OMINOUS_BOTTLE, BlockPos.ZERO, 3f);

        context.succeed();
    }
}