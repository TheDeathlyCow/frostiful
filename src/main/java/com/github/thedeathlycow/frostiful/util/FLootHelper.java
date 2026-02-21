package com.github.thedeathlycow.frostiful.util;

import java.util.List;
import java.util.Objects;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;
import java.util.Objects;

public class FLootHelper {
    public static <E extends LivingEntity> void dropPlayfightLoot(E entity, ResourceKey<LootTable> lootTableId) {
        Level level = entity.level();
        if (level instanceof ServerLevel serverLevel && Boolean.TRUE.equals(serverLevel.getGameRules().get(GameRules.MOB_DROPS))) {
            LootTable lootTable = Objects.requireNonNull(serverLevel.getServer())
                    .reloadableRegistries()
                    .getLootTable(lootTableId);

            List<ItemStack> generatedItems = lootTable.getRandomItems(new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.THIS_ENTITY, entity)
                    .withParameter(LootContextParams.ORIGIN, entity.position())
                    .create(LootContextParamSets.SELECTOR));

            for (ItemStack stack : generatedItems) {
                entity.spawnAtLocation(serverLevel, stack);
            }
        }
    }


    public static <E extends LivingEntity> void dropBrushingLoot(E entity, ItemStack tool, ResourceKey<LootTable> lootTableId) {
        Level world = entity.level();
        if (world instanceof ServerLevel serverWorld && Boolean.TRUE.equals(serverWorld.getGameRules().get(GameRules.MOB_DROPS))) {
            LootTable lootTable = Objects.requireNonNull(world.getServer())
                    .reloadableRegistries()
                    .getLootTable(lootTableId);

            List<ItemStack> generatedItems = lootTable.getRandomItems(new LootParams.Builder(serverWorld)
                    .withParameter(LootContextParams.THIS_ENTITY, entity)
                    .withParameter(LootContextParams.ORIGIN, entity.position())
                    .withParameter(LootContextParams.TOOL, tool)
                    .create(LootContextParamSets.SHEARING));

            for (ItemStack stack : generatedItems) {
                entity.spawnAtLocation(serverWorld, stack);
            }
        }
    }

    private FLootHelper() {

    }

}
