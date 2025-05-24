package com.github.thedeathlycow.frostiful.test.powder_snow_walkable;

import com.github.thedeathlycow.frostiful.registry.FItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

@SuppressWarnings("unused")
public class EntityPowderSnowTests {

    @GameTest(structure = "frostiful-test:powder_snow_walkable_test")
    public void zombie_wearing_fur_boots_does_not_fall(TestContext context) {
        context.setTime(18000);
        BlockPos spawnPos = new BlockPos(1, 2, 1);

        ZombieEntity zombie = context.spawnEntity(EntityType.ZOMBIE, spawnPos);
        StackReference stackReference = zombie.getStackReference(EquipmentSlot.FEET.getOffsetEntitySlotId(100));
        stackReference.set(new ItemStack(FItems.FUR_BOOTS));

        context.waitAndRun(
                20,
                () -> {
                    context.dontExpectEntityAtEnd(EntityType.ZOMBIE, new BlockPos(1, 1, 1));
                }
        );
    }

    @GameTest(structure = "frostiful-test:powder_snow_walkable_test")
    public void zombie_wearing_chainmail_fur_boots_does_not_fall(TestContext context) {
        context.setTime(18000);
        BlockPos spawnPos = new BlockPos(1, 2, 1);

        ZombieEntity zombie = context.spawnEntity(EntityType.ZOMBIE, spawnPos);
        StackReference stackReference = zombie.getStackReference(EquipmentSlot.FEET.getOffsetEntitySlotId(100));
        stackReference.set(new ItemStack(FItems.FUR_PADDED_CHAINMAIL_BOOTS));
        context.waitAndRun(
                20,
                () -> {
                    context.dontExpectEntityAtEnd(EntityType.ZOMBIE, new BlockPos(1, 1, 1));
                }
        );
    }

    @GameTest(structure = "frostiful-test:powder_snow_walkable_test")
    public void zombie_wearing_leather_boots_does_not_fall(TestContext context) {
        context.setTime(18000);
        BlockPos spawnPos = new BlockPos(1, 2, 1);

        ZombieEntity zombie = context.spawnEntity(EntityType.ZOMBIE, spawnPos);
        StackReference stackReference = zombie.getStackReference(EquipmentSlot.FEET.getOffsetEntitySlotId(100));
        stackReference.set(new ItemStack(Items.LEATHER_BOOTS));

        context.waitAndRun(
                20,
                () -> {
                    context.dontExpectEntityAtEnd(EntityType.ZOMBIE, new BlockPos(1, 1, 1));
                }
        );
    }

    @GameTest(structure = "frostiful-test:powder_snow_walkable_test")
    public void zombie_wearing_no_boots_falls(TestContext context) {
        context.setTime(18000);
        BlockPos spawnPos = new BlockPos(1, 2, 1);

        ZombieEntity zombie = context.spawnEntity(EntityType.ZOMBIE, spawnPos);

        context.waitAndRun(
                20,
                () -> {
                    context.expectEntityAtEnd(EntityType.ZOMBIE, new BlockPos(1, 1, 1));
                }
        );
    }

    @GameTest(structure = "frostiful-test:powder_snow_walkable_test")
    public void rabbit_does_not_fall(TestContext context) {
        BlockPos spawnPos = new BlockPos(1, 2, 1);

        RabbitEntity rabbit = context.spawnEntity(EntityType.RABBIT, spawnPos);

        context.waitAndRun(
                20,
                () -> {
                    context.dontExpectEntityAtEnd(EntityType.RABBIT, new BlockPos(1, 1, 1));
                }
        );
    }
}
