package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.Block;
import net.minecraft.util.shape.VoxelShape;

public class ADAllayPlushBlock extends ADAbstractDirectionalPlushBlock {
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.0D,
            10.0D, 13.0D);

    public ADAllayPlushBlock(Settings settings) {
        super(settings, NORTH_SHAPE);
    }
}
