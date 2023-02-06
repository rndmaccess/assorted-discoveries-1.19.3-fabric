package rndm_access.assorteddiscoveries.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rndm_access.assorteddiscoveries.common.core.ADBlockTags;

@Mixin(CropBlock.class)
public class ADCropBlockMixin {
    @Inject(method = "canPlantOnTop", at = @At("HEAD"), cancellable = true)
    private void assorteddiscoveries_plantOnPlanterBoxes(BlockState floor, BlockView world, BlockPos pos,
                                                         CallbackInfoReturnable<Boolean> info) {
        if(floor.isIn(ADBlockTags.OVERWORLD_PLANTER_BOXES)) {
            info.setReturnValue(true);
        }
    }
}
