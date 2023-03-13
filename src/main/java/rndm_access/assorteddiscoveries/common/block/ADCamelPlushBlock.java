package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.Block;
import net.minecraft.util.shape.VoxelShape;

public class ADCamelPlushBlock extends ADAbstractDirectionalPlushBlock {
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(2.0D, 0.0D, 1.0D, 14.0D,
            12.0D, 15.0D);

    public ADCamelPlushBlock(Settings settings) {
        super(settings, NORTH_SHAPE);
    }
}
