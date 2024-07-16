package com.github.thedeathlycow.frostiful.item.enchantment;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.Vec3d;

public record SetItemCooldownEnchantmentEffect(
        Item item,
        int durationTicks
) implements EnchantmentEntityEffect {

    public static final MapCodec<SetItemCooldownEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    Registries.ITEM.getCodec()
                            .fieldOf("item")
                            .forGetter(SetItemCooldownEnchantmentEffect::item),
                    Codecs.NONNEGATIVE_INT
                            .fieldOf("duration_ticks")
                            .forGetter(SetItemCooldownEnchantmentEffect::durationTicks)
            ).apply(instance, SetItemCooldownEnchantmentEffect::new)
    );

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if (context.owner() instanceof PlayerEntity player) {
            player.getItemCooldownManager().set(this.item, this.durationTicks);
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return null;
    }
}
