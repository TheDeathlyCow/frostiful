package com.github.thedeathlycow.frostiful.item.component;

import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import com.github.thedeathlycow.frostiful.compat.TrinketsIntegration;
import com.github.thedeathlycow.frostiful.registry.FDataComponentTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.TagKey;

import java.util.ArrayList;
import java.util.List;

public record FrostologyComponent(
        TagKey<DamageType> blockedDamageTypes
) {
    public static final FrostologyComponent DEFAULT = new FrostologyComponent(DamageTypeTags.IS_FREEZING);

    public static final Codec<FrostologyComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    TagKey.codec(RegistryKeys.DAMAGE_TYPE)
                            .optionalFieldOf("block_damage_types", DamageTypeTags.IS_FREEZING)
                            .forGetter(FrostologyComponent::blockedDamageTypes)
            ).apply(instance, FrostologyComponent::new)
    );

    public static final PacketCodec<RegistryByteBuf, FrostologyComponent> PACKET_CODEC = PacketCodec.tuple(
            TagKey.packetCodec(RegistryKeys.DAMAGE_TYPE),
            FrostologyComponent::blockedDamageTypes,
            FrostologyComponent::new
    );

    public static boolean isWearing(LivingEntity entity) {
        return !getAllEquipped(entity).isEmpty();
    }

    public static List<FrostologyComponent> getAllEquipped(LivingEntity entity) {
        List<FrostologyComponent> components = new ArrayList<>();

        for (ItemStack stack : entity.getEquippedItems()) {
            FrostologyComponent component = stack.get(FDataComponentTypes.FROSTOLOGY);
            if (component != null) {
                components.add(component);
            }
        }

        if (FrostifulIntegrations.isModLoaded(FrostifulIntegrations.TRINKETS_ID)) {
            components.addAll(
                    TrinketsIntegration.getEquippedTrinket(entity, FDataComponentTypes.FROSTOLOGY)
                            .stream()
                            .map(p -> p.getRight().get(FDataComponentTypes.FROSTOLOGY))
                            .toList()
            );
        }

        return components;
    }

    public boolean blockDamage(DamageSource source) {
        return source.isIn(this.blockedDamageTypes);
    }
}