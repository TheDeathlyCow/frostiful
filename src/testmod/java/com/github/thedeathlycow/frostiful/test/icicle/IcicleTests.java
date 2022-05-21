package com.github.thedeathlycow.frostiful.test.icicle;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;
import com.github.thedeathlycow.frostiful.block.*;

public class IcicleTests implements FabricGameTest {

    @GameTest(structureName = EMPTY_STRUCTURE)
    public void breakIcicle(TestContext context) {
        context.setBlockState(0, 0, 0, FrostifulBlocks.ICICLE.getDefaultState());
    }

}
