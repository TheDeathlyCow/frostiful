package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer.state;

import com.github.thedeathlycow.frostiful.client.render.state.FPlayerRendererState;
import com.github.thedeathlycow.frostiful.item.component.CapeComponent;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntityRenderState.class)
public class PlayerEntityRenderStateMixin implements FPlayerRendererState {
    @Unique
    private CapeComponent frostiful$cape = null;

    @Nullable
    @Override
    public CapeComponent frostiful$cape() {
        return this.frostiful$cape;
    }

    public void frostiful$cape(CapeComponent cape) {
        this.frostiful$cape = cape;
    }
}