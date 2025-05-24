package com.github.thedeathlycow.frostiful.test.icicle;

import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.test.TestContext;

@SuppressWarnings("unused")
public final class IcicleBreakTests {

    @GameTest(structure = "frostiful-test:icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_arrow(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.ARROW);
    }

    @GameTest(structure = "frostiful-test:icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_spectral_arrow(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.SPECTRAL_ARROW);
    }

    @GameTest(structure = "frostiful-test:icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_glacial_arrow(TestContext context) {
        strikeIcicleWithProjectile(context, FEntityTypes.GLACIAL_ARROW);
    }

    @GameTest(structure = "frostiful-test:icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_snowball(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.SNOWBALL);
    }

    @GameTest(structure = "frostiful-test:icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_trident(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.TRIDENT);
    }

    @GameTest(structure = "frostiful-test:icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_firework(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.FIREWORK_ROCKET);
    }

    @GameTest(structure = "frostiful-test:icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_fireball(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.FIREBALL);
    }

    @GameTest(structure = "frostiful-test:icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_small_fireball(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.SMALL_FIREBALL);
    }

    @GameTest(structure = "frostiful-test:icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_thrown_icicle(TestContext context) {
        strikeIcicleWithProjectile(context, FEntityTypes.THROWN_ICICLE);
    }

    private static void strikeIcicleWithProjectile(TestContext context, EntityType<?> type) {
        Entity entity = context.spawnEntity(type, 0, 6, 0);
        entity.addVelocity(0.0, 0.0, 5.0);
        context.expectBlockAtEnd(Blocks.AIR, 0, 5, 3);
    }
}
