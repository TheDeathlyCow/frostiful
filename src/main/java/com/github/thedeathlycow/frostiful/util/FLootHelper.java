package com.github.thedeathlycow.frostiful.util;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

public class FLootHelper {

    public static <E extends LivingEntity> void dropLootFromEntity(E entity, Identifier lootTableId) {
        World world = entity.getWorld();
        LootTable lootTable = Objects.requireNonNull(world.getServer())
                .getLootManager().getLootTable(lootTableId);
        List<ItemStack> generatedItems = lootTable.generateLoot(new LootContextParameterSet.Builder((ServerWorld) world)
                .add(LootContextParameters.THIS_ENTITY, entity)
                .add(LootContextParameters.ORIGIN, entity.getPos())
                .build(LootContextTypes.SELECTOR));
        Vec3d pos = entity.getPos();
        for (ItemStack stack : generatedItems) {
            world.spawnEntity(new ItemEntity(world, pos.x, pos.y, pos.z, stack));
        }
    }

}
