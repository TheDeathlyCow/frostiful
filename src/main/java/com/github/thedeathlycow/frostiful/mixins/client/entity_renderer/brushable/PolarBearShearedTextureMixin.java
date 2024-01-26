package com.github.thedeathlycow.frostiful.mixins.client.entity_renderer.brushable;

import com.github.thedeathlycow.frostiful.client.BrushableTextures;
import com.github.thedeathlycow.frostiful.entity.FBrushable;
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


    @Inject(
            method = "getTexture(Lnet/minecraft/entity/passive/PolarBearEntity;)Lnet/minecraft/util/Identifier;",
            at = @At("HEAD"),
            cancellable = true
    )
    public void getShearedTexture(PolarBearEntity polarBear, CallbackInfoReturnable<Identifier> cir) {
        if (polarBear instanceof FBrushable brushable && brushable.frostiful$wasBrushed()) {
            cir.setReturnValue(BrushableTextures.POLAR_BEAR);
        }
    }


}
