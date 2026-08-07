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

package com.github.thedeathlycow.frostiful.client.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.render.entity.*;
import com.github.thedeathlycow.frostiful.client.render.feature.IceSkateFeatureRenderer;
import com.github.thedeathlycow.frostiful.registry.FEntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;


public class FEntityRenderers {

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful entity renderers");
        EntityRenderers.register(FEntityTypes.GLACIAL_ARROW, GlacialArrowEntityRenderer::new);
        EntityRenderers.register(FEntityTypes.FROST_SPELL, FrostSpellEntityRenderer::new);
        EntityRenderers.register(FEntityTypes.FROSTOLOGER, FrostologerEntityRenderer::new);
        EntityRenderers.register(FEntityTypes.CHILLAGER, ChillagerEntityRenderer::new);
        EntityRenderers.register(FEntityTypes.BITER, BiterEntityRenderer::new);
        EntityRenderers.register(FEntityTypes.PACKED_SNOWBALL, ThrownItemRenderer::new);
        EntityRenderers.register(FEntityTypes.THROWN_ICICLE, ThrownIcicleEntityRenderer::new);
        EntityRenderers.register(FEntityTypes.FREEZING_WIND, NoopRenderer::new);

        LivingEntityRenderLayerRegistrationCallback.EVENT.register(
                (entityType, entityRenderer, registrationHelper, context) -> {
                    if (entityRenderer instanceof HumanoidMobRenderer<?, ?, ?> bipedEntityRenderer) {
                        registrationHelper.register(
                                new IceSkateFeatureRenderer<>(
                                        bipedEntityRenderer,
                                        context.getModelSet()
                                )
                        );
                    } else if (entityRenderer instanceof AvatarRenderer<?> playerEntityRenderer) {
                        registrationHelper.register(
                                new IceSkateFeatureRenderer<>(
                                        playerEntityRenderer,
                                        context.getModelSet()
                                )
                        );
                    } else if (entityRenderer instanceof ArmorStandRenderer armorStandEntityRenderer) {
                        registrationHelper.register(
                                new IceSkateFeatureRenderer<>(
                                        armorStandEntityRenderer,
                                        context.getModelSet()
                                )
                        );
                    } else if (entityRenderer instanceof GiantMobRenderer giantEntityRenderer) {
                        registrationHelper.register(
                                new IceSkateFeatureRenderer<>(
                                        giantEntityRenderer,
                                        context.getModelSet()
                                )
                        );
                    }
                }
        );
    }

    private FEntityRenderers() {

    }
}
