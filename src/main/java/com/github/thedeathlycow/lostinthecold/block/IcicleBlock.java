package com.github.thedeathlycow.lostinthecold.block;

import com.github.thedeathlycow.lostinthecold.entity.damage.LostInTheColdDamageSource;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.entity.damage.DamageSource;

public class IcicleBlock extends PointedDripstoneBlock {
    public IcicleBlock(Settings settings) {
        super(settings);
    }

    @Override
    public DamageSource getDamageSource() {
        return LostInTheColdDamageSource.FALLING_ICICLE;
    }

}
