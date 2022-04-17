package com.github.thedeathlycow.lostinthecold.mixins.world.gen.feature;

import net.minecraft.block.BlockState;
import net.minecraft.block.SideShapeType;
import net.minecraft.block.SnowBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.FreezeTopLayerFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(FreezeTopLayerFeature.class)
public class FreezeTopLayerMixin {

    @Redirect(
            method = "generate",
            slice = @Slice(
                    from = @At(
                            value = "INVOKE",
                            target = "Lnet/minecraft/world/biome/Biome;canSetSnow(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z"
                    )
            ),
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/StructureWorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z",
                    ordinal = 0
            )
    )
    private boolean randomizeSnowLayers(StructureWorldAccess instance, BlockPos blockPos, BlockState blockState, int i) {

        int layers = blockState.get(SnowBlock.LAYERS);

        if (instance.getBlockState(blockPos.down()).isSideSolid(instance, blockPos, Direction.UP, SideShapeType.FULL)) {
            layers = instance.getRandom().nextInt(2) + 1;
        }

        return instance.setBlockState(blockPos, blockState.with(SnowBlock.LAYERS, layers), i);
    }

}
