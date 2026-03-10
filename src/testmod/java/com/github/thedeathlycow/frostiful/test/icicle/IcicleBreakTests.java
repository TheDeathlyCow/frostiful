package com.github.thedeathlycow.frostiful.test.icicle;

import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import com.github.thedeathlycow.frostiful.test.FrostifulGameTest;
import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@SuppressWarnings("unused")
@GameTestHolder(FrostifulGameTest.MODID)
@PrefixGameTestTemplate(false)
public final class IcicleBreakTests implements FabricGameTest {

    @GameTest(template = "icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_arrow(GameTestHelper context) {
        strikeIcicleWithProjectile(context, EntityType.ARROW);
    }

    @GameTest(template = "icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_spectral_arrow(GameTestHelper context) {
        strikeIcicleWithProjectile(context, EntityType.SPECTRAL_ARROW);
    }

    @GameTest(template = "icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_glacial_arrow(GameTestHelper context) {
        strikeIcicleWithProjectile(context, FEntityTypes.GLACIAL_ARROW);
    }

    @GameTest(template = "icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_snowball(GameTestHelper context) {
        strikeIcicleWithProjectile(context, EntityType.SNOWBALL);
    }

    @GameTest(template = "icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_trident(GameTestHelper context) {
        strikeIcicleWithProjectile(context, EntityType.TRIDENT);
    }

    @GameTest(template = "icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_firework(GameTestHelper context) {
        strikeIcicleWithProjectile(context, EntityType.FIREWORK_ROCKET);
    }

    @GameTest(template = "icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_fireball(GameTestHelper context) {
        strikeIcicleWithProjectile(context, EntityType.FIREBALL);
    }

    @GameTest(template = "icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_small_fireball(GameTestHelper context) {
        strikeIcicleWithProjectile(context, EntityType.SMALL_FIREBALL);
    }

    @GameTest(template = "icicle_tests.projectile_base")
    public void icicles_break_when_struck_by_thrown_icicle(GameTestHelper context) {
        strikeIcicleWithProjectile(context, FEntityTypes.THROWN_ICICLE);
    }

    private static void strikeIcicleWithProjectile(GameTestHelper context, EntityType<?> type) {
        Entity entity = context.spawn(type, 0, 7, 0);
        entity.push(0.0, 0.0, 5.0);
        context.succeedWhenBlockPresent(Blocks.AIR, 0, 7, 3);
    }
}
