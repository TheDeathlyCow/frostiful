package com.github.thedeathlycow.frostiful.datagen.generator.tag;

import com.github.thedeathlycow.frostiful.registry.FEnchantments;
import com.github.thedeathlycow.frostiful.registry.tag.FEnchantmentTags;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.concurrent.CompletableFuture;

public class FEnchantmentTagGenerator extends FabricTagsProvider<Enchantment> {
    public FEnchantmentTagGenerator(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ENCHANTMENT, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        builder(FEnchantmentTags.IS_FROSTY)
                .add(Enchantments.FROST_WALKER);

        builder(FEnchantmentTags.HEAT_DRAIN_EXCLUSIVE_SET)
                .add(FEnchantments.ENERVATION)
                .add(FEnchantments.FROZEN_TOUCH_CURSE);
    }
}