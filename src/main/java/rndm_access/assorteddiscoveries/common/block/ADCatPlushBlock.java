package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.Block;
import net.minecraft.util.shape.VoxelShape;

public class ADCatPlushBlock extends ADAbstractDirectionalPlushBlock {
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(4.5D, 0.0D, 1.0D, 11.5D,
            9.5D, 14.5D);

    public ADCatPlushBlock(Settings settings) {
        super(settings, NORTH_SHAPE);
    }
}
