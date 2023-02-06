package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.util.shape.VoxelShape;

public class ADHorsePlushBlock extends ADAbstractDirectionalPlushBlock {
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(4.5D, 0.0D, 0.5D, 11.5D,
            12.5, 15.5D);

    public ADHorsePlushBlock(AbstractBlock.Settings settings) {
        super(settings, NORTH_SHAPE);
    }
}
