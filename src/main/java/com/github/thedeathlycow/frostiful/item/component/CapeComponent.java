package com.github.thedeathlycow.frostiful.item.component;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import com.github.thedeathlycow.frostiful.compat.TrinketsIntegration;
import com.github.thedeathlycow.frostiful.registry.FDataComponentTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public record CapeComponent(
        Identifier capeTexture,
        boolean overrideAccountCape
) {
    public static final CapeComponent FROSTOLOGY_CLOAK = new CapeComponent(
            Frostiful.id("textures/entity/frostology_cloak.png"),
            true
    );

    public static final Codec<CapeComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC
                            .fieldOf("cape_texture")
                            .forGetter(CapeComponent::capeTexture),
                    Codec.BOOL
                            .optionalFieldOf("override_account_cape", true)
                            .forGetter(CapeComponent::overrideAccountCape)
            ).apply(instance, CapeComponent::new)
    );

    public static final PacketCodec<RegistryByteBuf, CapeComponent> PACKET_CODEC = PacketCodec.tuple(
            Identifier.PACKET_CODEC,
            CapeComponent::capeTexture,
            PacketCodecs.BOOLEAN,
            CapeComponent::overrideAccountCape,
            CapeComponent::new
    );

    @Nullable
    public static CapeComponent getCapeOrChest(LivingEntity entity) {
        if (FrostifulIntegrations.isModLoaded(FrostifulIntegrations.TRINKETS_ID)) {
            CapeComponent cape = TrinketsIntegration.getComponentInCapeSlot(entity, FDataComponentTypes.CAPE);
            if (cape != null) {
                return cape;
            }
        }

        return entity.getEquippedStack(EquipmentSlot.CHEST).get(FDataComponentTypes.CAPE);
    }
}
