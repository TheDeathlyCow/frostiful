/*
 * Frostiful: A Vanilla+ Freezing Temperature Mod. Also try Scorchful!
 * Copyright (C) 2026	TheDeathlyCow
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.  If not, see
 * <https://www.gnu.org/licenses/>.
 */

package com.github.thedeathlycow.frostiful.client.mixin.entity.render;

import com.github.thedeathlycow.frostiful.client.BrushableTextures;
import com.github.thedeathlycow.frostiful.client.config.FrostifulClientConfig;
import com.github.thedeathlycow.frostiful.client.render.state.FPolarBearEntityRenderState;
import com.github.thedeathlycow.frostiful.registry.FDataAttachments;
import com.github.thedeathlycow.frostiful.survival.system.BrushSystem;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.renderer.entity.PolarBearRenderer;
import net.minecraft.client.renderer.entity.state.PolarBearRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.polarbear.PolarBear;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PolarBearRenderer.class)
public class PolarBearRendererMixin {
    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/animal/polarbear/PolarBear;Lnet/minecraft/client/renderer/entity/state/PolarBearRenderState;F)V",
            at = @At("TAIL")
    )
    private void updateRenderState(PolarBear polarBear, PolarBearRenderState state, float tickDelta, CallbackInfo ci) {
        long lastBrushTime = polarBear.getAttachedOrCreate(FDataAttachments.LAST_BRUSH_TIME);
        ((FPolarBearEntityRenderState) state).frostiful$wasSheared(BrushSystem.wasBrushed(polarBear, lastBrushTime));
    }

    @WrapMethod(
            method = "getTextureLocation(Lnet/minecraft/client/renderer/entity/state/PolarBearRenderState;)Lnet/minecraft/resources/Identifier;"
    )
    private Identifier setPolarBearHurtTexture(PolarBearRenderState state, Operation<Identifier> original) {
        if (!FrostifulClientConfig.displaySettings().disableHurtPolarBearSkin() && ((FPolarBearEntityRenderState) state).frostiful$wasSheared()) {
            return BrushableTextures.POLAR_BEAR;
        }

        return original.call(state);
    }
}