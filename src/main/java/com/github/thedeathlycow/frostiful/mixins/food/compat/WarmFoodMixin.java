package com.github.thedeathlycow.frostiful.mixins.food.compat;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.entity.effect.FStatusEffects;
import com.github.thedeathlycow.frostiful.tag.FItemTags;
import com.github.thedeathlycow.frostiful.util.TextStyles;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Item.class)
public abstract class WarmFoodMixin {

    @Shadow public abstract boolean isFood();

    @Inject(
            method = "finishUsing",
            at = @At("HEAD")
    )
    private void addWarmthToWarmFoods(
            ItemStack stack,
            World world,
            LivingEntity user,
            CallbackInfoReturnable<ItemStack> cir
    ) {
        if (this.isWarmingFood(stack)) {
            int duration = Frostiful.getConfig().freezingConfig.getWarmFoodWarmthTime();
            user.addStatusEffect(new StatusEffectInstance(FStatusEffects.WARMTH, duration));
        }
    }

    @Inject(
            method = "appendTooltip",
            at = @At("TAIL")
    )
    private void addToolTip(
            ItemStack stack,
            @Nullable World world,
            List<Text> tooltip,
            TooltipContext context,
            CallbackInfo ci
    ) {
        if (this.isWarmingFood(stack)) {
            tooltip.add(Text.translatable("item.frostiful.warming.tooltip")
                    .setStyle(TextStyles.WARMING_TOOLTIP));
        }
    }

    private boolean isWarmingFood(ItemStack stack) {
        return this.isFood() && stack.isIn(FItemTags.WARM_FOODS);
    }




}
