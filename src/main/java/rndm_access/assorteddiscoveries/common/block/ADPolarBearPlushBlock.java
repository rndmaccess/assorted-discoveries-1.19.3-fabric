package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.Block;
import net.minecraft.util.shape.VoxelShape;

public class ADPolarBearPlushBlock extends ADAbstractDirectionalPlushBlock {
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(3.5D, 0.0D, 2.5D, 12.5D,
            11.5D, 14.5D);

    public ADPolarBearPlushBlock(Settings settings) {
        super(settings, NORTH_SHAPE);
    }
}
