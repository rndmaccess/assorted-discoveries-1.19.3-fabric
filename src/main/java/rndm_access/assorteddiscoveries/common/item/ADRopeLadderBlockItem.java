package rndm_access.assorteddiscoveries.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class ADRopeLadderBlockItem extends BlockItem {
    public ADRopeLadderBlockItem(Block block, Item.Settings settings) {
        super(block, settings);
    }

    @Override
    public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
        BlockPos.Mutable mutablePos = context.getBlockPos().offset(context.getSide().getOpposite()).mutableCopy();
        BlockState state = context.getWorld().getBlockState(mutablePos);
        Direction down = Direction.DOWN;

        while (state.isOf(this.getBlock())) {
            mutablePos.move(down);
            state = context.getWorld().getBlockState(mutablePos);

            if (state.canReplace(context)) {
                return ItemPlacementContext.offset(context, mutablePos, down);
            }
        }
        return context;
    }
}
