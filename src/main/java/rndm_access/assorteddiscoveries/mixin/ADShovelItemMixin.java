package rndm_access.assorteddiscoveries.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShovelItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rndm_access.assorteddiscoveries.common.core.ADBlocks;
import rndm_access.assorteddiscoveries.common.core.CBlockTags;

@Mixin(ShovelItem.class)
public class ADShovelItemMixin {
    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void assorteddiscoveries_makePathSlabOnUse(ItemUsageContext context, CallbackInfoReturnable<ActionResult> info) {
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        BlockState state = world.getBlockState(pos);
        BlockState aboveState = world.getBlockState(pos.up());
        boolean isDirtSlab = state.isIn(CBlockTags.DIRT_SLABS);

        // Add functionality to the shovel so slabs in the dirt slabs tag can be converted to paths.
        if((isDirtSlab && (context.getSide() != Direction.DOWN) && aboveState.isAir())
                || (isDirtSlab && (state.get(SlabBlock.TYPE) == SlabType.BOTTOM))
                || (isDirtSlab && aboveState.contains(SlabBlock.TYPE) && (aboveState.get(SlabBlock.TYPE) == SlabType.TOP))) {

            if(player != null) {
                world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
                context.getStack().damage(1, player, (p) -> p.sendToolBreakStatus(context.getHand()));
            }

            world.setBlockState(pos, ADBlocks.DIRT_PATH_SLAB.getDefaultState()
                    .with(SlabBlock.TYPE, state.get(SlabBlock.TYPE))
                    .with(SlabBlock.WATERLOGGED, state.get(SlabBlock.WATERLOGGED)));
            info.setReturnValue(ActionResult.success(world.isClient()));
        }
    }
}
