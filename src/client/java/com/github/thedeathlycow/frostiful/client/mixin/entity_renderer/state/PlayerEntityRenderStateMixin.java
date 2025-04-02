package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer.state;

import com.github.thedeathlycow.frostiful.client.render.state.FPlayerRendererState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntityRenderState.class)
public class PlayerEntityRenderStateMixin implements FPlayerRendererState {
    @Unique
    private Identifier frostiful$capeTexture = null;

    @Nullable
    @Override
    public Identifier frostiful$capeTexture() {
        return this.frostiful$capeTexture;
    }

    @Override
    public void frostiful$capeTexture(Identifier id) {
        this.frostiful$capeTexture = id;
    }
}