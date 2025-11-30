package com.github.thedeathlycow.frostiful.item;

import com.github.thedeathlycow.frostiful.registry.FBannerPatterns;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.block.entity.BannerPatterns;

import static net.minecraft.commands.Commands.literal;

public final class FrostedBanner {

    private static final Component FROSTED_BANNER_NAME = Component.translatable("block.frostiful.frosted_banner")
            .withStyle(ChatFormatting.DARK_PURPLE);


    @SuppressWarnings("deprecation")
    public static ItemStack createItem(HolderGetter<BannerPattern> lookup) {
        ItemStack stack = new ItemStack(Items.WHITE_BANNER);

        BannerPatternLayers bannerPatterns = new BannerPatternLayers.Builder()
                .addIfRegistered(lookup, BannerPatterns.RHOMBUS_MIDDLE, DyeColor.PURPLE)
                .addIfRegistered(lookup, BannerPatterns.STRIPE_BOTTOM, DyeColor.LIGHT_GRAY)
                .addIfRegistered(lookup, BannerPatterns.STRIPE_CENTER, DyeColor.GRAY)
                .addIfRegistered(lookup, BannerPatterns.BORDER, DyeColor.LIGHT_GRAY)
                .addIfRegistered(lookup, BannerPatterns.STRIPE_MIDDLE, DyeColor.BLACK)
                .addIfRegistered(lookup, BannerPatterns.CIRCLE_MIDDLE, DyeColor.LIGHT_GRAY)
                .addIfRegistered(lookup, BannerPatterns.HALF_HORIZONTAL, DyeColor.LIGHT_GRAY)
                .addIfRegistered(lookup, BannerPatterns.BORDER, DyeColor.BLACK)
                .addIfRegistered(lookup, FBannerPatterns.FROSTOLOGY, DyeColor.CYAN)
                .build();
        stack.set(DataComponents.BANNER_PATTERNS, bannerPatterns);
        stack.set(DataComponents.HIDE_ADDITIONAL_TOOLTIP, Unit.INSTANCE);
        stack.set(DataComponents.ITEM_NAME, FROSTED_BANNER_NAME);

        return stack;
    }

    public static void registerCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                literal("givefrostedbanner")
                        .executes(
                                context -> {
                                    Entity as = context.getSource().getEntity();

                                    if (as instanceof ServerPlayer player) {
                                        HolderGetter<BannerPattern> lookup = context.getSource()
                                                .getServer()
                                                .registryAccess()
                                                .lookupOrThrow(Registries.BANNER_PATTERN);
                                        ItemStack stack = FrostedBanner.createItem(lookup);
                                        player.getInventory().add(stack);
                                    }

                                    return 0;
                                }
                        )
        );
    }


    private FrostedBanner() {

    }
}