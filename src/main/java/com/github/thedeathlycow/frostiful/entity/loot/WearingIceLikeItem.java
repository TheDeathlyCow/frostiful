package com.github.thedeathlycow.frostiful.entity.loot;

import com.github.thedeathlycow.frostiful.item.component.IceLikeComponent;
import com.github.thedeathlycow.frostiful.registry.FLootConditionTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record WearingIceLikeItem() implements LootItemCondition {
    public static final MapCodec<WearingIceLikeItem> CODEC = MapCodec.unit(new WearingIceLikeItem());

    @Override
    public LootItemConditionType getType() {
        return FLootConditionTypes.CHEST_EQUPPED_WITH_TRINKET;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.getOptionalParameter(LootContextParams.THIS_ENTITY);
        if (entity instanceof LivingEntity livingEntity) {
            return IceLikeComponent.isWearing(livingEntity);
        }

        return false;
    }
}
