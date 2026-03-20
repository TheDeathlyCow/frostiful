package com.github.thedeathlycow.frostiful.entity.loot;

import com.github.thedeathlycow.frostiful.compat.TrinketsIntegration;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

public record IsChillagerLord() implements LootItemCondition {
    public static final MapCodec<IsChillagerLord> CODEC = MapCodec.unit(new IsChillagerLord());

    @Override
    public MapCodec<IsChillagerLord> codec() {
        return CODEC;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY);
        if (entity instanceof LivingEntity livingEntity) {
            return TrinketsIntegration.hasAnyEquipped(livingEntity, stack -> stack.is(FItemTags.CHILLAGER_LORD_CLOAK));
        }

        return false;
    }
}
