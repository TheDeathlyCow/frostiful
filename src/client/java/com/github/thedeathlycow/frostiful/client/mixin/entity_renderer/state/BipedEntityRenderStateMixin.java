package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer.state;

import com.github.thedeathlycow.frostiful.client.render.state.FBipedRenderState;
import com.github.thedeathlycow.frostiful.item.component.CapeComponent;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BipedEntityRenderState.class)
public class BipedEntityRenderStateMixin implements FBipedRenderState {
    @Unique
    private boolean frostiful$wearingIceSkates = false;

    @Unique
    private CapeComponent frostiful$cape = null;

    @Override
    @Unique
    public boolean frostiful$wearingIceSkates() {
        return this.frostiful$wearingIceSkates;
    }

    @Override
    public void frostiful$wearingIceSkates(boolean value) {
        this.frostiful$wearingIceSkates = value;
    }

    @Override
    public CapeComponent frostiful$cape() {
        return this.frostiful$cape;
    }

    @Override
    public void frostiful$cape(CapeComponent cape) {
        this.frostiful$cape = cape;
    }
}