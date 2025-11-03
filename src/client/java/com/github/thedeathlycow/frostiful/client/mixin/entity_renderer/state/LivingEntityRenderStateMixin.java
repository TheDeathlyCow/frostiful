package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer.state;

import com.github.thedeathlycow.frostiful.client.render.state.FLivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntityRenderState.class)
public class LivingEntityRenderStateMixin implements FLivingEntityRenderState {
    @Unique
    private boolean frostiful$isRooted = false;

    @Override
    @Unique
    public boolean frostiful$isRooted() {
        return this.frostiful$isRooted;
    }

    @Override
    public void frostiful$isRooted(boolean value) {
        this.frostiful$isRooted = value;
    }
}