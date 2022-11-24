package com.github.thedeathlycow.frostiful.test.icicle;

import net.minecraft.block.Blocks;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;

@SuppressWarnings("unused")
public final class FrostTippedArrowTests {

    @GameTest(templateName = "frostiful-test:icicle_tests.frost_tipped_arrow.dispenser")
    public void frost_tipped_arrow_can_be_fired_from_dispenser(TestContext context) {
        context.pushButton(0, 3, 0);
        context.expectBlockAtEnd(Blocks.POLISHED_ANDESITE, 0, 5, 3);
    }

}
