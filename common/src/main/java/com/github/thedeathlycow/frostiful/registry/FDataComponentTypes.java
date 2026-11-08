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

package com.github.thedeathlycow.frostiful.registry;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.compat.TrinketsIntegration;
import com.github.thedeathlycow.frostiful.item.attribute.FrostResistanceComponent;
import com.github.thedeathlycow.frostiful.item.component.CapeComponent;
import com.github.thedeathlycow.frostiful.item.component.SimpleTooltipComponent;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.ItemStack;

import java.util.function.UnaryOperator;

public final class FDataComponentTypes {
    public static final DataComponentType<FrostResistanceComponent> FROST_RESISTANCE = register(
            "frost_resistance",
            builder -> builder
                    .persistent(FrostResistanceComponent.CODEC)
                    .networkSynchronized(FrostResistanceComponent.PACKET_CODEC)
                    .cacheEncoding()
    );

    public static final DataComponentType<CapeComponent> CAPE = register(
            "cape",
            builder -> builder
                    .persistent(CapeComponent.CODEC)
                    .networkSynchronized(CapeComponent.PACKET_CODEC)
                    .cacheEncoding()
    );

    public static final DataComponentType<HolderSet<DamageType>> BLOCKS_DAMAGE = register(
            "blocks_damage",
            builder -> builder
                    .persistent(RegistryCodecs.homogeneousList(Registries.DAMAGE_TYPE))
                    .networkSynchronized(ByteBufCodecs.holderSet(Registries.DAMAGE_TYPE))
                    .cacheEncoding()
    );

    public static final DataComponentType<SimpleTooltipComponent> SIMPLE_TOOLTIP = register(
            "simple_tooltip",
            builder -> builder
                    .persistent(SimpleTooltipComponent.CODEC)
                    .networkSynchronized(SimpleTooltipComponent.STREAM_CODEC)
                    .cacheEncoding()
    );

    public static void initialize() {
        Frostiful.LOGGER.debug("Initialized Frostiful item components");

        ItemComponentTooltipProviderRegistry.addLast(FDataComponentTypes.SIMPLE_TOOLTIP);

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, _) -> {
            for (ItemStack stack : TrinketsIntegration.getAllEquipped(entity)) {
                HolderSet<DamageType> blockedDamageTypes = stack.get(BLOCKS_DAMAGE);

                if (blockedDamageTypes != null && blockedDamageTypes.contains(source.typeHolder())) {
                    return false;
                }
            }

            return true;
        });
    }

    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                Frostiful.id(id),
                builderOperator.apply(DataComponentType.builder()).build()
        );
    }

    private FDataComponentTypes() {

    }
}