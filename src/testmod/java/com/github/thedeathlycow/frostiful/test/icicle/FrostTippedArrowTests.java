package com.github.thedeathlycow.frostiful.test.icicle;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Blocks;

@SuppressWarnings("unused")
public final class FrostTippedArrowTests {

    @GameTest(template = "frostiful-test:icicle_tests.glacial_arrow.dispenser")
    public void glacial_arrow_can_be_fired_from_dispenser(GameTestHelper context) {
        // pushing the button to fire arrow from dispenser
        context.pressButton(0, 3, 0);
        // expect arrow to hit a target block and push andesite up
        context.succeedWhenBlockPresent(Blocks.POLISHED_ANDESITE, 0, 5, 3);
    }

}
