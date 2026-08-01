package com.github.thedeathlycow.frostiful.mixins.datafix;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.GameRuleRegistryFix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameRuleRegistryFix.class)
public class GameRuleRegistryFixMixin {
    @Invoker("convertBoolean")
    private static Dynamic<?> frostiful$invokeConvertBoolean(Dynamic<?> dynamic) {
        throw new IllegalStateException("Method not invoked");
    }

    @ModifyReturnValue(
            method = "lambda$makeRule$2",
            at = @At("TAIL")
    )
    private static Dynamic<?> fixDoPassiveFreezingGameRule(Dynamic<?> original) {
        return original.renameAndFixField(
                "frostiful.doPassiveFreezing",
                "frostiful:do_passive_freezing",
                GameRuleRegistryFixMixin::frostiful$invokeConvertBoolean
        );
    }
}