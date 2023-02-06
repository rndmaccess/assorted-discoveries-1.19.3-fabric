package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShapes;
import rndm_access.assorteddiscoveries.common.core.ADBlocks;

public class ADJungleSnareHeadBlock extends AbstractPlantStemBlock implements ADJungleSnare {
    public ADJungleSnareHeadBlock(Settings settings) {
        super(settings, Direction.DOWN, VoxelShapes.fullCube(), false, 0.1);
    }

    @Override
    protected int getGrowthLength(Random random) {
        return 0;
    }

    @Override
    protected boolean chooseStemState(BlockState state) {
        return false;
    }

    @Override
    protected Block getPlant() {
        return ADBlocks.JUNGLE_SNARE_PLANT;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FLOWERING, AGE);
    }
}
