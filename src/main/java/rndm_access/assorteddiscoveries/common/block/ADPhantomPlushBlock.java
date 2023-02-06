package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.Block;
import net.minecraft.util.shape.VoxelShape;

public class ADPhantomPlushBlock extends ADAbstractDirectionalPlushBlock {
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(2.5, 0.0, 0.0, 13.5,
            5.0, 14.0);

    public ADPhantomPlushBlock(Settings settings) {
        super(settings, NORTH_SHAPE);
    }
}
