package com.github.thedeathlycow.frostiful.item.cloak;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.compat.FrostifulIntegrations;
import com.github.thedeathlycow.frostiful.registry.tag.FItemTags;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

import java.util.function.Predicate;

public abstract class AbstractFrostologyCloakItem extends Item implements Equipable {

    public static final ResourceLocation MODEL_TEXTURE_ID = Frostiful.id("textures/entity/frostology_cloak.png");

    public AbstractFrostologyCloakItem(Properties settings) {
        super(settings);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
    }

    public static boolean isWearing(Player player, Predicate<ItemStack> isCloak) {
        if (FrostifulIntegrations.isModLoaded(FrostifulIntegrations.TRINKETS_ID)) {
            boolean trinket = TrinketsApi.getTrinketComponent(player)
                    .map(trinketComponent -> trinketComponent.isEquipped(isCloak))
                    .orElse(false);

            if (trinket) {
                return true;
            }
        }

        ItemStack chestStack = player.getInventory()
                .getArmor(EquipmentSlot.CHEST.getIndex());
        return isCloak.test(chestStack);
    }

    public static EquipmentSlot getPreferredEquipmentSlot(LivingEntity entity, ItemStack stack) {
        return EquipmentSlot.CHEST;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack ingredient) {
        return ingredient.is(FItemTags.FUR);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        return this.swapWithEquipmentSlot(this, world, user, hand);
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.CHEST;
    }
}
