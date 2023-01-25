package com.github.thedeathlycow.frostiful.mixins.client.entity_renderer;

import com.github.thedeathlycow.frostiful.entity.FShearable;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.PolarBearEntityRenderer;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PolarBearEntityRenderer.class)
@Environment(EnvType.CLIENT)
public class PolarBearShearedTextureMixin {

    private static final Identifier FROSTIFUL$SHEARED_TEXTURE = Frostiful.id("textures/entity/bear/polar_bear_sheared.png");

    @Inject(
            method = "getTexture(Lnet/minecraft/entity/passive/PolarBearEntity;)Lnet/minecraft/util/Identifier;",
            at = @At("HEAD"),
            cancellable = true
    )
    public void getShearedTexture(PolarBearEntity polarBear, CallbackInfoReturnable<Identifier> cir) {
        if (polarBear instanceof FShearable shearable && shearable.frostiful$wasSheared()) {
            cir.setReturnValue(FROSTIFUL$SHEARED_TEXTURE);
        }
    }


}
