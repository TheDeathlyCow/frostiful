package com.github.thedeathlycow.frostiful.client.mixin.entity.render.state;

import com.github.thedeathlycow.frostiful.client.render.state.FLivingEntityRenderState;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntityRenderState.class)
public class LivingEntityRenderStateMixin implements FLivingEntityRenderState {
    @Unique
    private boolean frostiful$isRooted = false;

    @Unique
    private BlockModelRenderState frostiful$blockModel = new BlockModelRenderState();

    @Override
    @Unique
    public boolean frostiful$isRooted() {
        return this.frostiful$isRooted;
    }

    @Override
    public void frostiful$isRooted(boolean value) {
        this.frostiful$isRooted = value;
    }

    @Override
    public BlockModelRenderState frostiful$blockModel() {
        return this.frostiful$blockModel;
    }
}