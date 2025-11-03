package com.github.thedeathlycow.frostiful.test.tests;

import com.github.thedeathlycow.frostiful.entity.ChillagerEntity;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.Items;

@SuppressWarnings("unused")
public class ChillagerDropTests {
    @GameTest()
    public void regularChillagerDoesNotDropOminousBottle(GameTestHelper context) {
        ServerLevel world = context.getLevel();
        DamageSources damageSources = world.damageSources();

        ChillagerEntity chillager = context.spawn(FEntityTypes.CHILLAGER, BlockPos.ZERO);

        chillager.damage(world, damageSources.genericKill(), Float.MAX_VALUE);

        context.assertItemEntityNotPresent(Items.OMINOUS_BOTTLE, BlockPos.ZERO, 3f);

        context.succeed();
    }

    @GameTest()
    public void chillagerCaptainDropsOminousBottle(GameTestHelper context) {
        ServerLevel world = context.getLevel();
        DamageSources damageSources = world.damageSources();

        ChillagerEntity chillager = context.spawn(FEntityTypes.CHILLAGER, BlockPos.ZERO);
        chillager.setPatrolLeader(true);
        chillager.equipStack(
                EquipmentSlot.HEAD,
                Raid.getOminousBannerInstance(chillager.getRegistryManager().getOrThrow(Registries.BANNER_PATTERN))
        );

        context.assertTrue(chillager.isCaptain(), Component.literal("Chillager is not a captain!"));

        chillager.damage(world, damageSources.genericKill(), Float.MAX_VALUE);

        context.assertItemEntityPresent(Items.OMINOUS_BOTTLE, BlockPos.ZERO, 3f);

        context.succeed();
    }
}