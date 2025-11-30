package com.github.thedeathlycow.frostiful.entity.loot;

import com.github.thedeathlycow.frostiful.item.cloak.AbstractFrostologyCloakItem;
import com.github.thedeathlycow.frostiful.registry.FLootConditionTypes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record ChestEquippedWithTrinketLootCondition(
        ItemPredicate items
) implements LootItemCondition {

    public static final MapCodec<ChestEquippedWithTrinketLootCondition> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    ItemPredicate.CODEC
                            .fieldOf("items")
                            .forGetter(ChestEquippedWithTrinketLootCondition::items)
            ).apply(instance, ChestEquippedWithTrinketLootCondition::new)
    );

    @Override
    public LootItemConditionType getType() {
        return FLootConditionTypes.CHEST_EQUPPED_WITH_TRINKET;
    }

    @Override
    public boolean test(LootContext lootContext) {
        Entity entity = lootContext.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (entity instanceof Player player) {
            return AbstractFrostologyCloakItem.isWearing(player, this.items);
        }

        return false;
    }
}
