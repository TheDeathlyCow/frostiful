package com.github.thedeathlycow.frostiful.entity.loot;

import com.github.thedeathlycow.frostiful.item.component.IceLikeComponent;
import com.github.thedeathlycow.frostiful.registry.FLootConditionTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;

public record WearingIceLikeItem() implements LootCondition {
    public static final MapCodec<WearingIceLikeItem> CODEC = MapCodec.unit(new WearingIceLikeItem());

    @Override
    public LootConditionType getType() {
        return FLootConditionTypes.CHEST_EQUPPED_WITH_TRINKET;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.get(LootContextParameters.THIS_ENTITY);
        if (entity instanceof LivingEntity livingEntity) {
            return IceLikeComponent.isWearing(livingEntity);
        }

        return false;
    }
}
