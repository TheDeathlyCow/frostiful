package com.github.thedeathlycow.frostiful.util;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
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
                .getLootManager().getTable(lootTableId);
        LootContext.Builder builder = new LootContext.Builder((ServerWorld) world)
                .parameter(LootContextParameters.THIS_ENTITY, entity)
                .parameter(LootContextParameters.ORIGIN, entity.getPos())
                .random(entity.getRandom());
        List<ItemStack> generatedItems = lootTable.generateLoot(builder.build(LootContextTypes.SELECTOR));
        Vec3d pos = entity.getPos();
        for (ItemStack stack : generatedItems) {
            world.spawnEntity(new ItemEntity(world, pos.x, pos.y, pos.z, stack));
        }
    }

}
