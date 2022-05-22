package com.github.thedeathlycow.frostiful.test.icicle;

import com.github.thedeathlycow.frostiful.entity.FrostifulEntityTypes;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;

public class IcicleBreakTests implements FabricGameTest {

    @GameTest(structureName = "frostiful-test:icicle_tests.empty")
    public void iciclesBreakWhenStruckByArrow(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.ARROW);
    }

    @GameTest(structureName = "frostiful-test:icicle_tests.empty")
    public void iciclesBreakWhenStruckBySpectralArrow(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.SPECTRAL_ARROW);
    }

    @GameTest(structureName = "frostiful-test:icicle_tests.empty")
    public void iciclesBreakWhenStruckByFrostTippedArrow(TestContext context) {
        strikeIcicleWithProjectile(context, FrostifulEntityTypes.FROST_TIPPED_ARROW);
    }

    @GameTest(structureName = "frostiful-test:icicle_tests.empty")
    public void iciclesBreakWhenStruckBySnowball(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.SNOWBALL);
    }

    @GameTest(structureName = "frostiful-test:icicle_tests.empty")
    public void iciclesBreakWhenStruckByTrident(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.TRIDENT);
    }

    @GameTest(structureName = "frostiful-test:icicle_tests.empty")
    public void iciclesBreakWhenStruckByFirework(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.FIREWORK_ROCKET);
    }

    @GameTest(structureName = "frostiful-test:icicle_tests.empty")
    public void iciclesBreakWhenStruckByFireball(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.FIREBALL);
    }

    private static void strikeIcicleWithProjectile(TestContext context, EntityType<?> type) {
        Entity entity = context.spawnEntity(type, 0, 7, 0);
        entity.addVelocity(0.0, 0.0, 5.0);
        context.expectBlockAtEnd(Blocks.AIR, 0, 7, 3);
    }
}
