package com.github.thedeathlycow.frostiful.test.icicle;

import net.minecraft.block.Blocks;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.test.TestContext;

@SuppressWarnings("unused")
public final class FrostTippedArrowTests {

    @GameTest(structure = "frostiful-test:icicle_tests.glacial_arrow.dispenser")
    public void glacial_arrow_can_be_fired_from_dispenser(TestContext context) {
        // pushing the button to fire arrow from dispenser
        context.pushButton(0, 2, 0);
        // expect arrow to hit a target block and push andesite up
        context.expectBlockAtEnd(Blocks.POLISHED_ANDESITE, 0, 4, 3);
    }

}
