package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.Block;
import net.minecraft.util.shape.VoxelShape;

public class ADShortHatZombieVillagerPlushBlock extends ADAbstractDirectionalPlushBlock {
    private static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(3.0D, 0.0D, 3.0D, 13.5D,
            13.5D, 13.5D);

    public ADShortHatZombieVillagerPlushBlock(Settings settings) {
        super(settings, NORTH_SHAPE);
    }
}
