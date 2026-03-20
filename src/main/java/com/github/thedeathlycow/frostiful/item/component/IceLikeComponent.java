package com.github.thedeathlycow.frostiful.item.component;

import com.github.thedeathlycow.frostiful.compat.TrinketsIntegration;
import com.github.thedeathlycow.frostiful.registry.FDataComponentTypes;
import com.github.thedeathlycow.frostiful.util.TextStyles;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public record IceLikeComponent(
        TagKey<DamageType> blockedDamageTypes
) {
    public static final IceLikeComponent DEFAULT = new IceLikeComponent(DamageTypeTags.IS_FREEZING);

    public static final Codec<IceLikeComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    TagKey.hashedCodec(Registries.DAMAGE_TYPE)
                            .optionalFieldOf("block_damage_types", DamageTypeTags.IS_FREEZING)
                            .forGetter(IceLikeComponent::blockedDamageTypes)
            ).apply(instance, IceLikeComponent::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, IceLikeComponent> PACKET_CODEC = StreamCodec.composite(
            TagKey.streamCodec(Registries.DAMAGE_TYPE),
            IceLikeComponent::blockedDamageTypes,
            IceLikeComponent::new
    );

    public static boolean isWearing(LivingEntity entity) {
        return !getAllEquipped(entity).isEmpty();
    }

    public static List<IceLikeComponent> getAllEquipped(LivingEntity entity) {
        return TrinketsIntegration.getAllEquipped(entity).stream()
                .map(stack -> stack.get(FDataComponentTypes.ICE_LIKE))
                .filter(Objects::nonNull)
                .toList();
    }

    public boolean blockDamage(DamageSource source) {
        return source.is(this.blockedDamageTypes);
    }
}