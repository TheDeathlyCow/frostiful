package com.github.thedeathlycow.frostiful.test.tests;

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
    private static final String NIGHT_ENVIRONMENT = "frostiful-test:night";

    @GameTest(structure = "frostiful-test:powder_snow_walkable_test", environment = NIGHT_ENVIRONMENT)
    public void zombieWearingFurBootsDoesNotFall(TestContext context) {
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

    @GameTest(structure = "frostiful-test:powder_snow_walkable_test", environment = NIGHT_ENVIRONMENT)
    public void zombieWearingChainmailFurBootsDoesNotFall(TestContext context) {
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

    @GameTest(structure = "frostiful-test:powder_snow_walkable_test", environment = NIGHT_ENVIRONMENT)
    public void zombieWearingLeatherBootsDoesNotFall(TestContext context) {
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

    @GameTest(structure = "frostiful-test:powder_snow_walkable_test", environment = NIGHT_ENVIRONMENT)
    public void zombieWearingNoBootsFalls(TestContext context) {
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
    public void rabbitDoesNotFall(TestContext context) {
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
