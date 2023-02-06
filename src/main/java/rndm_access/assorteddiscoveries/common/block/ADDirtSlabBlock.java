package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.SlabBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import rndm_access.assorteddiscoveries.common.core.ADBlocks;

public class ADDirtSlabBlock extends SlabBlock implements Fertilizable {
    public ADDirtSlabBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        world.setBlockState(pos, ADBlocks.GRASS_SLAB.getDefaultState().with(TYPE, state.get(TYPE))
                .with(ADGrassSlabBlock.SNOWY, ADGrassSlabBlock.isSnow(world, blockPos, blockState)));
    }
}
