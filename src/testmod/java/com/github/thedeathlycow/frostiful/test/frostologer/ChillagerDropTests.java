package com.github.thedeathlycow.frostiful.test.frostologer;

import com.github.thedeathlycow.frostiful.entity.ChillagerEntity;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.test.FrostifulGameTest;
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
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@SuppressWarnings("unused")
@GameTestHolder(FrostifulGameTest.MODID)
@PrefixGameTestTemplate(false)
public class ChillagerDropTests {
    @GameTest(template = FrostifulGameTest.EMPTY_STRUCTURE)
    public void regular_chillager_does_not_drop_ominous_bottle(GameTestHelper context) {
        Level world = context.getLevel();
        DamageSources damageSources = world.damageSources();

        ChillagerEntity chillager = context.spawn(FEntityTypes.CHILLAGER, BlockPos.ZERO);

        chillager.hurt(damageSources.genericKill(), Float.MAX_VALUE);

        context.assertItemEntityNotPresent(Items.OMINOUS_BOTTLE, BlockPos.ZERO, 3f);

        context.succeed();
    }

    @GameTest(template = FrostifulGameTest.EMPTY_STRUCTURE)
    public void chillager_captain_drops_ominous_bottle(GameTestHelper context) {
        Level world = context.getLevel();
        DamageSources damageSources = world.damageSources();

        ChillagerEntity chillager = context.spawn(FEntityTypes.CHILLAGER, BlockPos.ZERO);
        chillager.setPatrolLeader(true);
        chillager.setItemSlot(
                EquipmentSlot.HEAD,
                Raid.getLeaderBannerInstance(chillager.registryAccess().lookupOrThrow(Registries.BANNER_PATTERN))
        );

        context.assertTrue(chillager.isCaptain(), "Chillager is not a captain!");

        chillager.hurt(damageSources.genericKill(), Float.MAX_VALUE);

        context.assertItemEntityPresent(Items.OMINOUS_BOTTLE, BlockPos.ZERO, 3f);

        context.succeed();
    }
}