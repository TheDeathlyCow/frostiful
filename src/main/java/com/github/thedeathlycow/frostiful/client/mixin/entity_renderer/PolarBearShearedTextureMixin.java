package com.github.thedeathlycow.frostiful.client.mixin.entity_renderer;

import com.github.thedeathlycow.frostiful.Frostiful;
import com.github.thedeathlycow.frostiful.client.BrushableTextures;
import com.github.thedeathlycow.frostiful.registry.FrostifulEntityAttachments;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.PolarBearRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.PolarBear;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PolarBearRenderer.class)
@Environment(EnvType.CLIENT)
public class PolarBearShearedTextureMixin {
    @WrapMethod(
            method = "getTextureLocation(Lnet/minecraft/world/entity/animal/PolarBear;)Lnet/minecraft/resources/ResourceLocation;"
    )
    public ResourceLocation getShearedTexture(PolarBear polarBear, Operation<ResourceLocation> original) {
        if (!Frostiful.getConfig().clientConfig.isDisableHurtPolarBearSkin() && polarBear.getData(FrostifulEntityAttachments.BRUSHABLE_COMPONENT).wasBrushed()) {
            return BrushableTextures.POLAR_BEAR;
        }
        return original.call(polarBear);
    }
}
