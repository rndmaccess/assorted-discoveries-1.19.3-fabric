package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.function.Supplier;

public class ADOnionBlock extends BeetrootsBlock {
    private static final VoxelShape[] ONION_SHAPE_BY_AGE;
    private final Supplier<Item> seedItem;

    public ADOnionBlock(AbstractBlock.Settings settings, Supplier<Item> seedItem) {
        super(settings);
        this.seedItem = seedItem;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ONION_SHAPE_BY_AGE[state.get(this.getAgeProperty())];
    }

    @Override
    protected ItemConvertible getSeedsItem() { return seedItem.get(); }

    static {
        ONION_SHAPE_BY_AGE = new VoxelShape[] {
                Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 8.0D, 12.0D),
                Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 11.0D, 13.0D),
                Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D),
                Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D) };
    }
}
