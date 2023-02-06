package rndm_access.assorteddiscoveries.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.SnowyBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rndm_access.assorteddiscoveries.common.util.ADBlockStateUtil;

@Mixin(SnowyBlock.class)
public class ADSnowyBlockMixin {
    @Inject(method = "getStateForNeighborUpdate", at = @At("HEAD"), cancellable = true)
    private void assorteddiscoveries_getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos,
                                                CallbackInfoReturnable<BlockState> info) {
        if(direction == Direction.UP && ADBlockStateUtil.isSnowSlabOrStairs(world, neighborPos, neighborState)) {
            info.setReturnValue(state.with(SnowyBlock.SNOWY, true));
        }
    }

    @Inject(method = "getPlacementState", at = @At("HEAD"), cancellable = true)
    private void assorteddiscoveries_getPlacementState(ItemPlacementContext context, CallbackInfoReturnable<BlockState> info) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos().up();
        BlockState blockState = context.getWorld().getBlockState(blockPos);
        BlockState placedState = Block.getBlockFromItem(context.getStack().getItem()).getDefaultState();

        if(ADBlockStateUtil.isSnowSlabOrStairs(world, blockPos, blockState)) {
            info.setReturnValue(placedState.with(SnowyBlock.SNOWY, true));
        }
    }
}
