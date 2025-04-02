package com.github.thedeathlycow.frostiful.item.component;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.registry.FDataComponentTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.RegistryCodecs;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public record FrostologyCloakComponent(
        Identifier capeTexture,
        RegistryEntryList<DamageType> blockedDamageTypes,
        boolean isActive
) {
    public static final Identifier DEFAULT_TEXTURE = Frostiful.id("textures/entity/frostology_cloak.png");

    public static final Codec<FrostologyCloakComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Identifier.CODEC
                            .optionalFieldOf("cape_texture", DEFAULT_TEXTURE)
                            .forGetter(FrostologyCloakComponent::capeTexture),
                    RegistryCodecs.entryList(RegistryKeys.DAMAGE_TYPE)
                            .optionalFieldOf("block_damage_types", RegistryEntryList.empty())
                            .forGetter(FrostologyCloakComponent::blockedDamageTypes),
                    Codec.BOOL
                            .optionalFieldOf("is_active", false)
                            .forGetter(FrostologyCloakComponent::isActive)
            ).apply(instance, FrostologyCloakComponent::new)
    );

    public static final PacketCodec<RegistryByteBuf, FrostologyCloakComponent> PACKET_CODEC = PacketCodec.tuple(
            Identifier.PACKET_CODEC,
            FrostologyCloakComponent::capeTexture,
            PacketCodecs.registryEntryList(RegistryKeys.DAMAGE_TYPE),
            FrostologyCloakComponent::blockedDamageTypes,
            PacketCodecs.BOOLEAN,
            FrostologyCloakComponent::isActive,
            FrostologyCloakComponent::new
    );

    @Nullable
    public static FrostologyCloakComponent getChestOrCape(LivingEntity entity) {
        return entity.getEquippedStack(EquipmentSlot.CHEST).get(FDataComponentTypes.FROSTOLOGY_CLOAK);
    }

    public boolean allowDamage(DamageSource source) {
        return !this.blockedDamageTypes.contains(source.getTypeRegistryEntry());
    }
}
