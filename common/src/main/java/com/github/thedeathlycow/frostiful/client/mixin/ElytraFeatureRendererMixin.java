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

package com.github.thedeathlycow.frostiful.client.mixin;

import com.github.thedeathlycow.frostiful.client.render.state.FHumanoidRenderState;
import com.github.thedeathlycow.frostiful.item.component.CapeComponent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.layers.WingsLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.core.ClientAsset;
import net.minecraft.world.entity.player.PlayerSkin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WingsLayer.class)
public class ElytraFeatureRendererMixin {
    @WrapOperation(
            method = "getPlayerElytraTexture",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/PlayerSkin;cape()Lnet/minecraft/core/ClientAsset$Texture;"
            )
    )
    private static ClientAsset.Texture getFrostologyCloakTexture(
            PlayerSkin instance,
            Operation<ClientAsset.Texture> original,
            @Local(argsOnly = true) HumanoidRenderState state
    ) {
        ClientAsset.Texture accountCape = original.call(instance);
        CapeComponent cape = ((FHumanoidRenderState) state).frostiful$cape();

        if (cape != null && (accountCape == null || cape.overrideAccountCape())) {
            return cape.capeAsset();
        } else {
            return accountCape;
        }
    }
}