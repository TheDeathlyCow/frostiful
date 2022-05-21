package com.github.thedeathlycow.frostiful.test.icicle;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;

public class IcicleBreakTests implements FabricGameTest {

    @GameTest(structureName = "frostiful-test:icicletests.breakiciclewitharrow")
    public void iciclesBreakWhenStruckByArrow(TestContext context) {
        strikeIcicleViaDispenser(context);
    }

    @GameTest(structureName = "frostiful-test:icicletests.breakiciclewithspectralarrow")
    public void iciclesBreakWhenStruckBySpectralArrow(TestContext context) {
        strikeIcicleViaDispenser(context);
    }

    @GameTest(structureName = "frostiful-test:icicletests.breakiciclewithfrosttippedarrow")
    public void iciclesBreakWhenStruckByFrostTippedArrow(TestContext context) {
        strikeIcicleViaDispenser(context);
    }

    @GameTest(structureName = "frostiful-test:icicletests.breakiciclewithsnowball")
    public void iciclesBreakWhenStruckBySnowball(TestContext context) {
        strikeIcicleViaDispenser(context);
    }

    @GameTest(structureName = "frostiful-test:icicletests.iciclesbreaksempty")
    public void iciclesBreakWhenStruckByTrident(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.TRIDENT);
    }

    @GameTest(structureName = "frostiful-test:icicletests.iciclesbreaksempty")
    public void iciclesBreakWhenStruckByFirework(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.FIREWORK_ROCKET);
    }

    @GameTest(structureName = "frostiful-test:icicletests.iciclesbreaksempty")
    public void iciclesBreakWhenStruckByFireball(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.FIREBALL);
    }

    private static void strikeIcicleViaDispenser(TestContext context) {
        context.pushButton(0, 7, 0);
        context.expectBlockAtEnd(Blocks.AIR, 0, 7, 3);
    }

    private static void strikeIcicleWithProjectile(TestContext context, EntityType<?> type) {
        Entity entity = context.spawnEntity(type, 0, 7, 0);
        entity.addVelocity(0.0, 0.0, 5.0);
        context.expectBlockAtEnd(Blocks.AIR, 0, 7, 3);
    }
}
