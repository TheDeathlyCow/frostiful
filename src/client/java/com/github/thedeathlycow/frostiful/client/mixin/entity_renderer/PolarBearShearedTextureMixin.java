package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.BrushableTextures;
import com.github.thedeathlycow.frostiful.registry.FComponents;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.PolarBearEntityRenderer;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PolarBearEntityRenderer.class)
@Environment(EnvType.CLIENT)
public class PolarBearShearedTextureMixin {
    @WrapMethod(
            method = "getTexture(Lnet/minecraft/entity/passive/PolarBearEntity;)Lnet/minecraft/util/Identifier;"
    )
    public Identifier getShearedTexture(PolarBearEntity polarBear, Operation<Identifier> original) {
        if (!Frostiful.getConfig().clientConfig.isDisableHurtPolarBearSkin() && FComponents.BRUSHABLE_COMPONENT.get(polarBear).wasBrushed()) {
            return BrushableTextures.POLAR_BEAR;
        }
        return original.call(polarBear);
    }
}
