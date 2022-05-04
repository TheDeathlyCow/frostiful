package com.github.thedeathlycow.frostiful.mixins.world.gen.feature;

import com.github.thedeathlycow.frostiful.config.GlobalConfig;
import com.github.thedeathlycow.frostiful.init.Frostiful;
import net.minecraft.block.*;
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

        BlockState down = instance.getBlockState(blockPos.down());
        byte maxStackSize = Frostiful.getConfig().get(GlobalConfig.FREEZE_TOP_LAYER_MAX_ACCUMULATION);

        if (maxStackSize == 0) {
            blockState = Blocks.AIR.getDefaultState();
        } else if (down.isSideSolid(instance, blockPos, Direction.UP, SideShapeType.FULL)) {
            int layers = instance.getRandom().nextInt(maxStackSize) + 1;
            blockState = blockState.with(SnowBlock.LAYERS, layers);
        }

        return instance.setBlockState(blockPos, blockState, i);
    }

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
                    ordinal = 1
            )
    )
    private boolean makeGrassNotSnowyWhenZeroLayers(StructureWorldAccess instance, BlockPos blockPos, BlockState blockState, int i) {
        byte maxStackSize = Frostiful.getConfig().get(GlobalConfig.FREEZE_TOP_LAYER_MAX_ACCUMULATION);

        if (maxStackSize == 0 && blockState.getBlock() instanceof SnowyBlock) {
            return instance.setBlockState(blockPos, blockState.with(SnowyBlock.SNOWY, false), i);
        } else {
            return instance.setBlockState(blockPos, blockState, i);
        }
    }

}
