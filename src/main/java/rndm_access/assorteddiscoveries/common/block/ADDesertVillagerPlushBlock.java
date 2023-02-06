package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.Block;
import net.minecraft.util.shape.VoxelShape;

public class ADDesertVillagerPlushBlock extends ADAbstractDirectionalPlushBlock {
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(3.5D, 0.0D, 3.5D, 13.0D,
            14.5D, 13.0D);

    public ADDesertVillagerPlushBlock(Settings settings) {
        super(settings, NORTH_SHAPE);
    }
}
