package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class FItemGroups {

    public static final ItemGroup FROSTIFUL = FabricItemGroupBuilder.create(Frostiful.id("frostiful"))
            .icon(() -> new ItemStack(FItems.FROST_WAND))
            .appendItems(stacks -> {
                stacks.add(new ItemStack(FItems.FUR_HELMET));
                stacks.add(new ItemStack(FItems.FUR_CHESTPLATE));
                stacks.add(new ItemStack(FItems.FUR_LEGGINGS));
                stacks.add(new ItemStack(FItems.FUR_BOOTS));
                stacks.add(new ItemStack(FItems.FUR_PADDED_CHAINMAIL_HELMET));
                stacks.add(new ItemStack(FItems.FUR_PADDED_CHAINMAIL_CHESTPLATE));
                stacks.add(new ItemStack(FItems.FUR_PADDED_CHAINMAIL_LEGGINGS));
                stacks.add(new ItemStack(FItems.FUR_PADDED_CHAINMAIL_BOOTS));
                stacks.add(new ItemStack(FItems.POLAR_BEAR_FUR_TUFT));
                stacks.add(new ItemStack(FItems.WOLF_FUR_TUFT));

                stacks.add(new ItemStack(FItems.COLD_SUN_LICHEN));
                stacks.add(new ItemStack(FItems.COOL_SUN_LICHEN));
                stacks.add(new ItemStack(FItems.WARM_SUN_LICHEN));
                stacks.add(new ItemStack(FItems.HOT_SUN_LICHEN));

                stacks.add(new ItemStack(FItems.FROST_WAND));
                stacks.add(new ItemStack(FItems.FROSTOLOGER_SPAWN_EGG));
                stacks.add(new ItemStack(FItems.ICICLE));
                stacks.add(new ItemStack(FItems.FROST_TIPPED_ARROW));

                stacks.add(new ItemStack(FItems.PACKED_SNOW_BLOCK));
                stacks.add(new ItemStack(FItems.PACKED_SNOW_BRICKS));
                stacks.add(new ItemStack(FItems.PACKED_SNOW_BRICK_STAIRS));
                stacks.add(new ItemStack(FItems.PACKED_SNOW_BRICK_SLAB));
                stacks.add(new ItemStack(FItems.PACKED_SNOW_BRICK_WALL));
            }).build();

}
