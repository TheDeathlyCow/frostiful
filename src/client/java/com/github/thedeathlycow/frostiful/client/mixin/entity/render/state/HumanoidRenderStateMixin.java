package com.github.thedeathlycow.frostiful.client.mixin.entity.render.state;

import com.github.thedeathlycow.frostiful.client.render.state.FHumanoidRenderState;
import com.github.thedeathlycow.frostiful.item.component.CapeComponent;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(HumanoidRenderState.class)
public class HumanoidRenderStateMixin implements FHumanoidRenderState {
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