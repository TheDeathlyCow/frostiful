package com.github.thedeathlycow.frostiful.test.icicle;

import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;

@SuppressWarnings("unused")
public final class IcicleBreakTests implements FabricGameTest {

    @GameTest(templateName = "frostiful-test:icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_arrow(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.ARROW);
    }

    @GameTest(templateName = "frostiful-test:icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_spectral_arrow(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.SPECTRAL_ARROW);
    }

    @GameTest(templateName = "frostiful-test:icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_glacial_arrow(TestContext context) {
        strikeIcicleWithProjectile(context, FEntityTypes.GLACIAL_ARROW);
    }

    @GameTest(templateName = "frostiful-test:icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_snowball(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.SNOWBALL);
    }

    @GameTest(templateName = "frostiful-test:icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_trident(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.TRIDENT);
    }

    @GameTest(templateName = "frostiful-test:icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_firework(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.FIREWORK_ROCKET);
    }

    @GameTest(templateName = "frostiful-test:icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_fireball(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.FIREBALL);
    }

    @GameTest(templateName = "frostiful-test:icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_small_fireball(TestContext context) {
        strikeIcicleWithProjectile(context, EntityType.SMALL_FIREBALL);
    }

    @GameTest(templateName = "frostiful-test:icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_thrown_icicle(TestContext context) {
        strikeIcicleWithProjectile(context, FEntityTypes.THROWN_ICICLE);
    }

    private static void strikeIcicleWithProjectile(TestContext context, EntityType<?> type) {
        Entity entity = context.spawnEntity(type, 0, 7, 0);
        entity.addVelocity(0.0, 0.0, 5.0);
        context.expectBlockAtEnd(Blocks.AIR, 0, 7, 3);
    }
}
