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

package com.github.thedeathlycow.frostiful.item.component;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import com.github.thedeathlycow.frostiful.compat.TrinketsIntegration;
import com.github.thedeathlycow.frostiful.registry.FDataComponentTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.ClientAsset;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public record CapeComponent(
        ClientAsset.ResourceTexture capeAsset,
        boolean overrideAccountCape
) {
    public static final CapeComponent FROSTOLOGY_CLOAK = new CapeComponent(
            new ClientAsset.ResourceTexture(Frostiful.id("entity/frostology_cloak")),
            true
    );

    public static final Codec<CapeComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    ClientAsset.ResourceTexture.CODEC
                            .fieldOf("cape_asset")
                            .forGetter(CapeComponent::capeAsset),
                    Codec.BOOL
                            .optionalFieldOf("override_account_cape", true)
                            .forGetter(CapeComponent::overrideAccountCape)
            ).apply(instance, CapeComponent::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, CapeComponent> PACKET_CODEC = StreamCodec.composite(
            ClientAsset.ResourceTexture.STREAM_CODEC,
            CapeComponent::capeAsset,
            ByteBufCodecs.BOOL,
            CapeComponent::overrideAccountCape,
            CapeComponent::new
    );

    @Nullable
    public static CapeComponent getEquippedCape(LivingEntity entity) {
        if (FrostifulIntegrations.isTrinketsLoaded()) {
            CapeComponent cape = TrinketsIntegration.getFirstChestEquipped(entity, FDataComponentTypes.CAPE);

            if (cape != null) {
                return cape;
            }
        }

        return entity.getItemBySlot(EquipmentSlot.CHEST).get(FDataComponentTypes.CAPE);
    }
}
