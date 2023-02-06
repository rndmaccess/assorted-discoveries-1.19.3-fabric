package rndm_access.assorteddiscoveries.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rndm_access.assorteddiscoveries.common.core.CBlockTags;

@Mixin(SpreadableBlock.class)
public class ADSpreadableBlockMixin {
    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    private static void assorteddiscoveries_canSurvive(BlockState state, WorldView world, BlockPos pos,
                                                       CallbackInfoReturnable<Boolean> info) {
        BlockState blockState = world.getBlockState(pos.up());

        if(blockState.isIn(BlockTags.SNOW) || blockState.isIn(CBlockTags.SNOW_SLABS) || blockState.isIn(CBlockTags.SNOW_STAIRS)) {
            info.setReturnValue(true);
        }
    }
}
