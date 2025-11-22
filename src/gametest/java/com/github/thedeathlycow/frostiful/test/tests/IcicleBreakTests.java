package com.github.thedeathlycow.frostiful.test.tests;

import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;

@SuppressWarnings("unused")
public final class IcicleBreakTests {

    @GameTest(structure = "frostiful-test:icicle_tests.projectile_base")
    public void iciclesBreakWhenStruckByArrow(GameTestHelper context) {
        strikeIcicleWithProjectile(context, EntityType.ARROW);
    }

    @GameTest(structure = "frostiful-test:icicle_tests.projectile_base")
    public void iciclesBreakWhenStruckBySpectralArrow(GameTestHelper context) {
        strikeIcicleWithProjectile(context, EntityType.SPECTRAL_ARROW);
    }

    @GameTest(structure = "frostiful-test:icicle_tests.projectile_base")
    public void iciclesBreakWhenStruckByGlacialArrow(GameTestHelper context) {
        strikeIcicleWithProjectile(context, FEntityTypes.GLACIAL_ARROW);
    }

    @GameTest(structure = "frostiful-test:icicle_tests.projectile_base")
    public void iciclesBreakWhenStruckBySnowball(GameTestHelper context) {
        strikeIcicleWithProjectile(context, EntityType.SNOWBALL);
    }

    @GameTest(structure = "frostiful-test:icicle_tests.projectile_base")
    public void iciclesBreakWhenStruckByTrident(GameTestHelper context) {
        strikeIcicleWithProjectile(context, EntityType.TRIDENT);
    }

    @GameTest(structure = "frostiful-test:icicle_tests.projectile_base")
    public void iciclesBreakWhenStruckByFirework(GameTestHelper context) {
        strikeIcicleWithProjectile(context, EntityType.FIREWORK_ROCKET);
    }

    @GameTest(structure = "frostiful-test:icicle_tests.projectile_base")
    public void iciclesBreakWhenStruckByFireball(GameTestHelper context) {
        strikeIcicleWithProjectile(context, EntityType.FIREBALL);
    }

    @GameTest(structure = "frostiful-test:icicle_tests.projectile_base")
    public void iciclesBreakWhenStruckBySmallFireball(GameTestHelper context) {
        strikeIcicleWithProjectile(context, EntityType.SMALL_FIREBALL);
    }

    @GameTest(structure = "frostiful-test:icicle_tests.projectile_base")
    public void iciclesBreakWhenStruckByThrownIcicle(GameTestHelper context) {
        strikeIcicleWithProjectile(context, FEntityTypes.THROWN_ICICLE);
    }

    private static void strikeIcicleWithProjectile(GameTestHelper context, EntityType<?> type) {
        Entity entity = context.spawn(type, 0, 6, 0);
        entity.push(0.0, 0.0, 5.0);
        context.succeedWhenBlockPresent(Blocks.AIR, 0, 5, 3);
    }
}
