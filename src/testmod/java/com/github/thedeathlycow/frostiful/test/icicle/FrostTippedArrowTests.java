package com.github.thedeathlycow.frostiful.test.icicle;

import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

public class FrostTippedArrowTests {

    @GameTest(templateName = "frostiful-test:icicle_tests.frost_tipped_arrow.dispenser")
    public void frostTippedArrowCanBeFiredFromDispenser(TestContext context) {
        context.pushButton(0, 3, 0);
        context.expectBlockAtEnd(Blocks.POLISHED_ANDESITE, 0, 5, 3);
    }

}
