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

public record IceLikeComponent(
        TagKey<DamageType> blockedDamageTypes
) {
    public static final IceLikeComponent DEFAULT = new IceLikeComponent(DamageTypeTags.IS_FREEZING);

    public static final Codec<IceLikeComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    TagKey.codec(RegistryKeys.DAMAGE_TYPE)
                            .optionalFieldOf("block_damage_types", DamageTypeTags.IS_FREEZING)
                            .forGetter(IceLikeComponent::blockedDamageTypes)
            ).apply(instance, IceLikeComponent::new)
    );

    public static final PacketCodec<RegistryByteBuf, IceLikeComponent> PACKET_CODEC = PacketCodec.tuple(
            TagKey.packetCodec(RegistryKeys.DAMAGE_TYPE),
            IceLikeComponent::blockedDamageTypes,
            IceLikeComponent::new
    );

    public static boolean isWearing(LivingEntity entity) {
        return !getAllEquipped(entity).isEmpty();
    }

    public static List<IceLikeComponent> getAllEquipped(LivingEntity entity) {
        List<IceLikeComponent> components = new ArrayList<>();

        for (ItemStack stack : entity.getEquippedItems()) {
            IceLikeComponent component = stack.get(FDataComponentTypes.ICE_LIKE);
            if (component != null) {
                components.add(component);
            }
        }

        if (FrostifulIntegrations.isModLoaded(FrostifulIntegrations.TRINKETS_ID)) {
            components.addAll(
                    TrinketsIntegration.getEquippedTrinket(entity, FDataComponentTypes.ICE_LIKE)
                            .stream()
                            .map(p -> p.getRight().get(FDataComponentTypes.ICE_LIKE))
                            .toList()
            );
        }

        return components;
    }

    public boolean blockDamage(DamageSource source) {
        return source.isIn(this.blockedDamageTypes);
    }
}