package com.github.thedeathlycow.frostiful.test.powder_snow_walkable;

import com.github.thedeathlycow.frostiful.registry.FItems;
import com.github.thedeathlycow.frostiful.test.FrostifulGameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@SuppressWarnings("unused")
@GameTestHolder(FrostifulGameTest.MODID)
@PrefixGameTestTemplate(false)
public class EntityPowderSnowTests {

    @GameTest(template = "powder_snow_walkable_test")
    public void zombie_wearing_fur_boots_does_not_fall(GameTestHelper context) {
        context.setDayTime(18000);
        BlockPos spawnPos = new BlockPos(1, 3, 1);

        Zombie zombie = context.spawn(EntityType.ZOMBIE, spawnPos);
        SlotAccess stackReference = zombie.getSlot(EquipmentSlot.FEET.getIndex(100));
        stackReference.set(new ItemStack(FItems.FUR_BOOTS));

        context.runAfterDelay(
                20,
                () -> {
                    context.succeedWhenEntityNotPresent(EntityType.ZOMBIE, new BlockPos(1, 2, 1));
                }
        );
    }

    @GameTest(template = "powder_snow_walkable_test")
    public void zombie_wearing_chainmail_fur_boots_does_not_fall(GameTestHelper context) {
        context.setDayTime(18000);
        BlockPos spawnPos = new BlockPos(1, 3, 1);

        Zombie zombie = context.spawn(EntityType.ZOMBIE, spawnPos);
        SlotAccess stackReference = zombie.getSlot(EquipmentSlot.FEET.getIndex(100));
        stackReference.set(new ItemStack(FItems.FUR_PADDED_CHAINMAIL_BOOTS));
        context.runAfterDelay(
                20,
                () -> {
                    context.succeedWhenEntityNotPresent(EntityType.ZOMBIE, new BlockPos(1, 2, 1));
                }
        );
    }

    @GameTest(template = "powder_snow_walkable_test")
    public void zombie_wearing_leather_boots_does_not_fall(GameTestHelper context) {
        context.setDayTime(18000);
        BlockPos spawnPos = new BlockPos(1, 3, 1);

        Zombie zombie = context.spawn(EntityType.ZOMBIE, spawnPos);
        SlotAccess stackReference = zombie.getSlot(EquipmentSlot.FEET.getIndex(100));
        stackReference.set(new ItemStack(Items.LEATHER_BOOTS));

        context.runAfterDelay(
                20,
                () -> {
                    context.succeedWhenEntityNotPresent(EntityType.ZOMBIE, new BlockPos(1, 2, 1));
                }
        );
    }

    @GameTest(template = "powder_snow_walkable_test")
    public void zombie_wearing_no_boots_falls(GameTestHelper context) {
        context.setDayTime(18000);
        BlockPos spawnPos = new BlockPos(1, 3, 1);

        Zombie zombie = context.spawn(EntityType.ZOMBIE, spawnPos);

        context.runAfterDelay(
                20,
                () -> {
                    context.succeedWhenEntityPresent(EntityType.ZOMBIE, new BlockPos(1, 2, 1));
                }
        );
    }

    @GameTest(template = "powder_snow_walkable_test")
    public void rabbit_does_not_fall(GameTestHelper context) {
        BlockPos spawnPos = new BlockPos(1, 3, 1);

        Rabbit rabbit = context.spawn(EntityType.RABBIT, spawnPos);

        context.runAfterDelay(
                20,
                () -> {
                    context.succeedWhenEntityNotPresent(EntityType.RABBIT, new BlockPos(1, 2, 1));
                }
        );
    }
}
