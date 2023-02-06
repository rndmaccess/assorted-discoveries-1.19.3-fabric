package rndm_access.assorteddiscoveries.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rndm_access.assorteddiscoveries.common.block.ADGrassSlabBlock;
import rndm_access.assorteddiscoveries.common.core.ADBlockTags;
import rndm_access.assorteddiscoveries.common.core.CBlockTags;

@Mixin(PlantBlock.class)
public class ADPlantBlockMixin {
    @Inject(method = "canPlantOnTop", at = @At("HEAD"), cancellable = true)
    private void assorteddiscoveries_canPlantOnTop(BlockState floor, BlockView world, BlockPos pos,
                                                   CallbackInfoReturnable<Boolean> info) {
        if(floor.isIn(CBlockTags.DIRT_SLABS) && floor.getBlock() instanceof SlabBlock
                && floor.get(ADGrassSlabBlock.TYPE) != SlabType.BOTTOM) {
            info.setReturnValue(true);
        }

        if(floor.isIn(ADBlockTags.OVERWORLD_PLANTER_BOXES)) {
            info.setReturnValue(true);
        }
    }
}