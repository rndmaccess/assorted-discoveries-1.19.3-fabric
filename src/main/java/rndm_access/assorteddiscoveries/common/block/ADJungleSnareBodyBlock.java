package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.AbstractPlantBlock;
import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShapes;
import rndm_access.assorteddiscoveries.common.core.ADBlocks;

public class ADJungleSnareBodyBlock extends AbstractPlantBlock implements ADJungleSnare {
    public ADJungleSnareBodyBlock(Settings settings) {
        super(settings, Direction.DOWN, VoxelShapes.fullCube(), false);
    }

    @Override
    protected AbstractPlantStemBlock getStem() {
        return (AbstractPlantStemBlock) ADBlocks.JUNGLE_SNARE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FLOWERING);
    }
}
