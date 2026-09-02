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

import com.github.thedeathlycow.frostiful.client.render.state.FHumanoidRenderState;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.state.ArmorStandRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorStandRenderer.class)
public class ArmorStandRendererMixin {
    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/entity/decoration/ArmorStand;Lnet/minecraft/client/renderer/entity/state/ArmorStandRenderState;F)V",
            at = @At("TAIL")
    )
    private void updateRenderState(ArmorStand entity, ArmorStandRenderState state, float tickDelta, CallbackInfo ci) {
        boolean wearingSkates = entity.getItemBySlot(EquipmentSlot.FEET).is(FItemTags.ICE_SKATES);

        FHumanoidRenderState bipedState = ((FHumanoidRenderState) state);

        bipedState.frostiful$wearingIceSkates(wearingSkates);
    }
}