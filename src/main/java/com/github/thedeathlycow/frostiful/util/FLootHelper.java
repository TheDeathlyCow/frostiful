package com.github.thedeathlycow.frostiful.util;

import java.util.List;
import java.util.Objects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class FLootHelper {

    public static <E extends LivingEntity> void dropLootFromEntity(E entity, ResourceKey<LootTable> lootTableId) {
        Level world = entity.level();
        if (world instanceof ServerLevel serverWorld && world.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            LootTable lootTable = Objects.requireNonNull(world.getServer())
                    .reloadableRegistries().getLootTable(lootTableId);
            List<ItemStack> generatedItems = lootTable.getRandomItems(new LootParams.Builder(serverWorld)
                    .withParameter(LootContextParams.THIS_ENTITY, entity)
                    .withParameter(LootContextParams.ORIGIN, entity.position())
                    .create(LootContextParamSets.SELECTOR));

            for (ItemStack stack : generatedItems) {
                entity.spawnAtLocation(stack);
            }
        }
    }

    private FLootHelper() {

    }

}
