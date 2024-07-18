package com.github.thedeathlycow.frostiful.test.icicle;

import net.minecraft.block.Blocks;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;

@SuppressWarnings("unused")
public final class FrostTippedArrowTests {

    @GameTest(templateName = "frostiful-test:icicle_tests.frost_tipped_arrow.dispenser")
    public void frost_tipped_arrow_can_be_fired_from_dispenser(TestContext context) {
        // pushing the button to fire arrow from dispenser
        context.pushButton(0, 3, 0);
        // expect arrow to hit a target block and push andesite up
        context.expectBlockAtEnd(Blocks.POLISHED_ANDESITE, 0, 5, 3);
    }

}
