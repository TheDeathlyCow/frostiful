package com.github.thedeathlycow.frostiful.item.enchantment;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public record SetItemCooldownEnchantmentEffect(
        Item item,
        int durationTicks
) implements EnchantmentEntityEffect {

    public static final MapCodec<SetItemCooldownEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    BuiltInRegistries.ITEM.byNameCodec()
                            .fieldOf("item")
                            .forGetter(SetItemCooldownEnchantmentEffect::item),
                    ExtraCodecs.NON_NEGATIVE_INT
                            .fieldOf("duration_ticks")
                            .forGetter(SetItemCooldownEnchantmentEffect::durationTicks)
            ).apply(instance, SetItemCooldownEnchantmentEffect::new)
    );

    @Override
    public void apply(ServerLevel world, int level, EnchantedItemInUse context, Entity user, Vec3 pos) {
        if (context.owner() instanceof Player player) {
            player.getCooldowns().addCooldown(this.item, this.durationTicks);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
